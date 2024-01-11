package raf.gymreservationservice.mapper;

import org.springframework.stereotype.Component;
import raf.gymreservationservice.domain.Reservation;
import raf.gymreservationservice.dto.ReservationDto;
import raf.gymreservationservice.repository.AppointmentRepository;

@Component
public class ReservationMapper {
    private AppointmentMapper appointmentMapper;

    public ReservationMapper(AppointmentMapper appointmentMapper) {
        this.appointmentMapper = appointmentMapper;
    }

    public ReservationDto reservationToReservationDto(Reservation reservation){
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setUserId(reservation.getUserId());
        reservationDto.setPrice(reservation.getPrice());
        reservationDto.setAppointment(appointmentMapper.appointmentToAppointmentDto(reservation.getAppointment()));
        return reservationDto;
    }
}
