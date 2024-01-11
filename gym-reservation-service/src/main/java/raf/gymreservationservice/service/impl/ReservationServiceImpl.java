package raf.gymreservationservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import raf.gymreservationservice.configuration.userservice.dto.DiscountDto;
import raf.gymreservationservice.domain.Appointment;
import raf.gymreservationservice.domain.Reservation;
import raf.gymreservationservice.dto.ReservationCreateDto;
import raf.gymreservationservice.dto.ReservationDto;
import raf.gymreservationservice.mapper.ReservationMapper;
import raf.gymreservationservice.repository.AppointmentRepository;
import raf.gymreservationservice.repository.ReservationRepository;
import raf.gymreservationservice.service.ReservationService;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository reservationRepository;
    private ReservationMapper reservationMapper;
    private AppointmentRepository appointmentRepository;
    private RestTemplate userServiceRestTemplate;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper,
                                  AppointmentRepository appointmentRepository, RestTemplate userServiceRestTemplate) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.appointmentRepository = appointmentRepository;
        this.userServiceRestTemplate = userServiceRestTemplate;
    }

    @Override
    public Page<ReservationDto> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(reservationMapper::reservationToReservationDto);
    }

    @Override
    public void addReservation(ReservationCreateDto reservationCreateDto) {
        Appointment appointment = appointmentRepository.findById(reservationCreateDto.getAppointmentId()).get();

        ResponseEntity<DiscountDto> discountDtoResponseEntity = userServiceRestTemplate.exchange("/client/" +
                reservationCreateDto.getUserId() + "/discount", HttpMethod.GET, null, DiscountDto.class);

        BigDecimal price = appointment.getGymTraining().getPrice().divide(BigDecimal.valueOf(100))
                .multiply(BigDecimal.valueOf(100 - Objects.requireNonNull(discountDtoResponseEntity.getBody()).getDiscount()));

        Reservation reservation = new Reservation(appointment, reservationCreateDto.getUserId(), price);
        reservationRepository.save(reservation);
    }
}
