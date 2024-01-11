package raf.gymreservationservice.mapper;

import org.springframework.stereotype.Component;
import raf.gymreservationservice.domain.Appointment;
import raf.gymreservationservice.domain.GymTraining;
import raf.gymreservationservice.dto.AppointmentCreateDto;
import raf.gymreservationservice.dto.AppointmentDto;
import raf.gymreservationservice.dto.GymTrainingDto;
import raf.gymreservationservice.exceptions.NotFoundException;
import raf.gymreservationservice.repository.GymTrainingRepository;

@Component
public class AppointmentMapper {
    private GymTrainingRepository gymTrainingRepository;
    private GymTrainingMapper gymTrainingMapper;

    public AppointmentMapper(GymTrainingRepository gymTrainingRepository, GymTrainingMapper gymTrainingMapper) {
        this.gymTrainingRepository = gymTrainingRepository;
        this.gymTrainingMapper = gymTrainingMapper;
    }

    public AppointmentDto appointmentToAppointmentDto(Appointment appointment){
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(appointment.getId());
        appointmentDto.setDate(appointment.getDate());
        appointmentDto.setStartTime(appointment.getStartTime());
        appointmentDto.setEndTime(appointment.getEndTime());
        appointmentDto.setGymTrainingDto(gymTrainingMapper.gymTrainingToGymTrainingDto(appointment.getGymTraining()));
        appointmentDto.setCapacity(gymTrainingMapper.gymTrainingToGymTrainingDto(appointment.getGymTraining()).getGymDto().getCapacity());
        return appointmentDto;
    }

    public Appointment appointmentCreateDtoToAppointment(AppointmentCreateDto appointmentCreateDto){
        Appointment appointment = new Appointment();
        appointment.setDate(appointmentCreateDto.getDate());
        appointment.setStartTime(appointmentCreateDto.getStartTime());
        appointment.setEndTime(appointmentCreateDto.getEndTime());
        GymTraining gymTraining = gymTrainingRepository.findById(appointmentCreateDto.getGymTraining())
                .orElseThrow(() -> new NotFoundException(String
                        .format("Gym with id: %d does not exists.", appointmentCreateDto.getGymTraining())));
        appointment.setGymTraining(gymTraining);
        appointment.setCapacity(gymTraining.getGym().getCapacity());
        return appointment;
    }
}
