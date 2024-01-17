package raf.gymreservationservice.service.impl;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import raf.gymreservationservice.configuration.userservice.dto.DiscountDto;
import raf.gymreservationservice.configuration.userservice.dto.IncrementReservationCountDto;
import raf.gymreservationservice.domain.Appointment;
import raf.gymreservationservice.domain.Reservation;
import raf.gymreservationservice.dto.CancellationDto;
import raf.gymreservationservice.dto.ReservationCreateDto;
import raf.gymreservationservice.dto.ReservationDto;
import raf.gymreservationservice.exceptions.NotFoundException;
import raf.gymreservationservice.listener.helper.MessageHelper;
import raf.gymreservationservice.mapper.ReservationMapper;
import raf.gymreservationservice.repository.AppointmentRepository;
import raf.gymreservationservice.repository.ReservationRepository;
import raf.gymreservationservice.service.ReservationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository reservationRepository;
    private ReservationMapper reservationMapper;
    private AppointmentRepository appointmentRepository;
    private RestTemplate userServiceRestTemplate;
    private JmsTemplate jmsTemplate;
    private String incrementReservationCountDestination;
    private String reservationCancellationQueue;
    private MessageHelper messageHelper;
    private Retry reservationServiceRetry;
    private Bulkhead reservationServiceBulkhead;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper,
                                  AppointmentRepository appointmentRepository, RestTemplate userServiceRestTemplate, JmsTemplate jmsTemplate,
                                  @Value("${destination.incrementReservationCount}") String incrementReservationCountDestination, @Value("${destination.reservationCancel}") String reservationCancellationQueue, MessageHelper messageHelper,
                                  Retry reservationServiceRetry, Bulkhead reservationServiceBulkhead) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.appointmentRepository = appointmentRepository;
        this.userServiceRestTemplate = userServiceRestTemplate;
        this.jmsTemplate = jmsTemplate;
        this.incrementReservationCountDestination = incrementReservationCountDestination;
        this.reservationCancellationQueue = reservationCancellationQueue;
        this.messageHelper = messageHelper;
        this.reservationServiceRetry = reservationServiceRetry;
        this.reservationServiceBulkhead = reservationServiceBulkhead;
    }

    @Override
    public Page<ReservationDto> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(reservationMapper::reservationToReservationDto);
    }

    @Override
    public void addReservation(ReservationCreateDto reservationCreateDto) {
        Appointment appointment = appointmentRepository.findById(reservationCreateDto.getAppointmentId()).get();
        if (appointment.getCapacity() == 0) {
            return;
        }
        appointment.setCapacity(appointment.getCapacity() - 1);
        appointmentRepository.save(appointment);

        DiscountDto discountDto = Bulkhead.decorateSupplier(reservationServiceBulkhead,
                () -> Retry.decorateSupplier(reservationServiceRetry, () -> getDiscount(reservationCreateDto.getUserId())).get()).get();

        BigDecimal price = appointment.getGymTraining().getPrice().divide(BigDecimal.valueOf(100))
                .multiply(BigDecimal.valueOf(100 - Objects.requireNonNull(discountDto.getDiscount())));

        Reservation reservation = new Reservation(appointment, reservationCreateDto.getUserId(), price);
        reservationRepository.save(reservation);
        jmsTemplate.convertAndSend(incrementReservationCountDestination, messageHelper.createTextMessage(new IncrementReservationCountDto(reservationCreateDto.getUserId(), appointment.getGymTraining().getGym().getName())));
    }

    private DiscountDto getDiscount(Long userId) {
        System.out.println("[" + System.currentTimeMillis() / 1000 + "] Getting discount for id: " + userId);
        try {
            Thread.sleep(5000);
            return userServiceRestTemplate.exchange("/client/" + userId + "/discount", HttpMethod.GET, null, DiscountDto.class).getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException(String.format("Discount for User with id: %d not found.", userId));
            throw new RuntimeException("Internal server error.");
        } catch (Exception e) {
            throw new RuntimeException("Internal server error.");
        }
    }

    @Override
    public void deleteById(Long id) {
        Reservation reservation = reservationRepository.findById(id).get();
        reservation.getAppointment().setCapacity(reservation.getAppointment().getCapacity() + 1);
        CancellationDto cancellationDto = new CancellationDto(List.of(reservation.getUserId()), reservation.getAppointment().getGymTraining().getGym().getName());
        jmsTemplate.convertAndSend(reservationCancellationQueue, messageHelper.createTextMessage(cancellationDto));
        reservationRepository.deleteById(id);
    }
}
