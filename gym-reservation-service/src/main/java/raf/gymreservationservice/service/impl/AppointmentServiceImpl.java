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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private AppointmentRepository appointmentRepository;
    private AppointmentMapper appointmentMapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public Optional<AppointmentDto> findAll() {
        LocalDate now = LocalDate.now();
        LocalDate fiveDaysLater = now.plusDays(5);

        return appointmentRepository.findAppointmentsForNextFiveDays(now, fiveDaysLater)
                .map(appointmentMapper::appointmentToAppointmentDto);
    }
    @Override
    public AppointmentDto findById(Long id) {
        Appointment appointment = appointmentRepository.findById(id).get();
        return appointmentMapper.appointmentToAppointmentDto(appointment);
    }

    @Override
    public Optional<AppointmentDto> findByGymId(Long id) {
        return appointmentRepository.findAppointmentsByGymTrainingGymId(id)
                .map(appointmentMapper::appointmentToAppointmentDto);
    }

    @Override
    public AppointmentDto add(AppointmentCreateDto appointmentCreateDto) {
        Appointment appointment = appointmentMapper.appointmentCreateDtoToAppointment(appointmentCreateDto);
        appointmentRepository.save(appointment);
        return appointmentMapper.appointmentToAppointmentDto(appointment);
    }

    @Override
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }
}
