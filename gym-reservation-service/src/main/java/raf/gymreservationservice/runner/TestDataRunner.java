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
import java.time.LocalDateTime;
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
        //Insert gyms
        Gym gym1 = new Gym("Gym01", "First Gym for Group trainings", 5, 12);
        Gym gym2 = new Gym("Gym02", "Second Gym for Individual trainings", 2, 1);
        Gym gym3 = new Gym("Gym03", "Third Gym for Group trainings", 3, 10);
        Gym gym4 = new Gym("Gym04", "Fourth Gym for Individual trainings", 1, 1);
        gymRepository.save(gym1);
        gymRepository.save(gym2);
        gymRepository.save(gym3);
        gymRepository.save(gym4);

        //Insert training types
        TrainingType tt1 = new TrainingType("Group", "Yoga");
        TrainingType tt2 = new TrainingType("Group", "Pilates");
        TrainingType tt3 = new TrainingType("Individual", "Powerlifting");
        TrainingType tt4 = new TrainingType("Individual", "Calisthenics");
        trainingTypeRepository.save(tt1);
        trainingTypeRepository.save(tt2);
        trainingTypeRepository.save(tt3);
        trainingTypeRepository.save(tt4);

        //Insert gym trainings
        GymTraining gt1 = new GymTraining(gym1, tt1, BigDecimal.valueOf(10));
        GymTraining gt2 = new GymTraining(gym1, tt2, BigDecimal.valueOf(15));
        GymTraining gt3 = new GymTraining(gym2, tt3, BigDecimal.valueOf(15));
        GymTraining gt4 = new GymTraining(gym3, tt1, BigDecimal.valueOf(20));
        GymTraining gt5 = new GymTraining(gym4, tt3, BigDecimal.valueOf(20));
        GymTraining gt6 = new GymTraining(gym4, tt4, BigDecimal.valueOf(25));

        gymTrainingRepository.save(gt1);
        gymTrainingRepository.save(gt2);
        gymTrainingRepository.save(gt3);
        gymTrainingRepository.save(gt4);
        gymTrainingRepository.save(gt5);
        gymTrainingRepository.save(gt6);

        //Insert appointments
        //gym1
        appointmentRepository.save(new Appointment(LocalDate.parse("17/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "11:00", "12:00", gt1, gt1.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("17/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "12:00", "13:00", gt2, gt2.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("17/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "18:00", "19:00", gt1, gt1.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("19/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "20:00", "21:00", gt2, gt2.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("19/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "22:00", "23:00", gt2, gt2.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("20/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "09:00", "10:00", gt1, gt1.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("20/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "12:00", "13:00", gt2, gt2.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("30/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "12:00", "13:00", gt2, gt2.getGym().getCapacity()));
        //gym2
        appointmentRepository.save(new Appointment(LocalDate.parse("17/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "09:00", "10:00", gt3, gt3.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("17/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "12:00", "13:00", gt3, gt3.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("17/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "14:00", "15:00", gt3, gt3.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("19/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "15:00", "16:00", gt3, gt3.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("19/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "19:00", "20:00", gt3, gt3.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("20/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "08:00", "09:00", gt3, gt3.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("20/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "11:00", "12:00", gt3, gt3.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("01/02/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "11:00", "12:00", gt3, gt3.getGym().getCapacity()));
        //gym3
        appointmentRepository.save(new Appointment(LocalDate.parse("17/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "10:00", "11:00", gt4, gt4.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("17/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "17:00", "18:00", gt4, gt4.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("17/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "20:00", "21:00", gt4, gt4.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("19/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "09:00", "10:00", gt4, gt4.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("19/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "14:00", "15:00", gt4, gt4.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("20/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "15:00", "16:00", gt4, gt4.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("20/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "21:00", "22:00", gt4, gt4.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("31/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "21:00", "22:00", gt4, gt4.getGym().getCapacity()));
        //gym4
        appointmentRepository.save(new Appointment(LocalDate.parse("17/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "09:00", "10:00", gt5, gt5.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("17/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "19:00", "20:00", gt6, gt6.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("17/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "21:00", "22:00", gt6, gt6.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("19/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "10:00", "11:00", gt5, gt5.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("19/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "18:00", "19:00", gt6, gt6.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("20/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "09:00", "10:00", gt5, gt5.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("20/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "20:00", "21:00", gt5, gt5.getGym().getCapacity()));
        appointmentRepository.save(new Appointment(LocalDate.parse("20/02/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "20:00", "21:00", gt5, gt5.getGym().getCapacity()));
    }
}
