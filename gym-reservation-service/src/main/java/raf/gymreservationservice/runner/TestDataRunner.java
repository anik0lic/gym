package raf.gymreservationservice.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.gymreservationservice.domain.Appointment;
import raf.gymreservationservice.domain.Gym;
import raf.gymreservationservice.domain.GymTraining;
import raf.gymreservationservice.domain.TrainingType;
import raf.gymreservationservice.repository.AppointmentRepository;
import raf.gymreservationservice.repository.GymRepository;
import raf.gymreservationservice.repository.GymTrainingRepository;
import raf.gymreservationservice.repository.TrainingTypeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {
    private GymRepository gymRepository;
    private TrainingTypeRepository trainingTypeRepository;
    private GymTrainingRepository gymTrainingRepository;
    private AppointmentRepository appointmentRepository;

    public TestDataRunner(GymRepository gymRepository, TrainingTypeRepository trainingTypeRepository, GymTrainingRepository gymTrainingRepository, AppointmentRepository appointmentRepository) {
        this.gymRepository = gymRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.gymTrainingRepository = gymTrainingRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Insert gym
        Gym gym = new Gym();
        gym.setName("Sala01");
        gym.setDescription("Prva sala");
        gym.setNumberOfCoaches(5);
        gym.setCapacity(12);
        gymRepository.save(gym);

        //Insert training type
        TrainingType trainingType = new TrainingType();
        trainingType.setName("Joga");
        trainingType.setType("Grupni");
        trainingTypeRepository.save(trainingType);

        //Insert gym training
        GymTraining gymTraining = new GymTraining();
        gymTraining.setGym(gym);
        gymTraining.setTrainingType(trainingType);
        gymTraining.setPrice(BigDecimal.valueOf(20));
        gymTrainingRepository.save(gymTraining);

        //Insert appointment
        Appointment appointment = new Appointment();
        appointment.setDate(LocalDate.parse("21/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        appointment.setStartTime(LocalTime.parse("11:00", DateTimeFormatter.ofPattern("HH:mm")));
        appointment.setEndTime(LocalTime.parse("13:00", DateTimeFormatter.ofPattern("HH:mm")));
        appointment.setGymTraining(gymTraining);
        appointmentRepository.save(appointment);
    }
}
