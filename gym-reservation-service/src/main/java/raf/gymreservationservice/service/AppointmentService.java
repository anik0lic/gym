package raf.gymreservationservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.gymreservationservice.dto.AppointmentCreateDto;
import raf.gymreservationservice.dto.AppointmentDto;

public interface AppointmentService {
    Page<AppointmentDto> findAll(Pageable pageable);
    AppointmentDto add(AppointmentCreateDto appointmentCreateDto);
}
