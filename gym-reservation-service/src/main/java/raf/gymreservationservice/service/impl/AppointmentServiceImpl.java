package raf.gymreservationservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.gymreservationservice.domain.Appointment;
import raf.gymreservationservice.domain.GymTraining;
import raf.gymreservationservice.dto.AppointmentCreateDto;
import raf.gymreservationservice.dto.AppointmentDto;
import raf.gymreservationservice.mapper.AppointmentMapper;
import raf.gymreservationservice.repository.AppointmentRepository;
import raf.gymreservationservice.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private AppointmentRepository appointmentRepository;
    private AppointmentMapper appointmentMapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public Page<AppointmentDto> findAll(Pageable pageable) {
        return appointmentRepository.findAll(pageable)
                .map(appointmentMapper::appointmentToAppointmentDto);
    }

    @Override
    public AppointmentDto add(AppointmentCreateDto appointmentCreateDto) {
        Appointment appointment = appointmentMapper.appointmentCreateDtoToAppointment(appointmentCreateDto);
        appointmentRepository.save(appointment);
        return appointmentMapper.appointmentToAppointmentDto(appointment);
    }
}
