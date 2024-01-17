package raf.gymreservationservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.gymreservationservice.dto.GymTrainingDto;
import raf.gymreservationservice.dto.ReservationCreateDto;
import raf.gymreservationservice.dto.ReservationDto;

public interface ReservationService {
    Page<ReservationDto> findAll(Pageable pageable);
    ReservationDto addReservation(ReservationCreateDto reservationCreateDto);
    void deleteById(Long id);
}
