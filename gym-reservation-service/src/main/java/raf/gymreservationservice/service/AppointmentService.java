package raf.gymreservationservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.gymreservationservice.dto.AppointmentCreateDto;
import raf.gymreservationservice.dto.AppointmentDto;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<AppointmentDto> findAll();
    AppointmentDto findById(Long id);
    List<AppointmentDto> findByGymId(Long id);
    AppointmentDto add(AppointmentCreateDto appointmentCreateDto);
    void deleteById(Long id);
}
