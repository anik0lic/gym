package raf.gymreservationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import raf.gymreservationservice.domain.Appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findAppointmentsByGymTrainingGymId(Long id);
    @Query("SELECT a FROM Appointment a WHERE a.date BETWEEN :startDate AND :endDate")
    Optional<Appointment> findAppointmentsForNextFiveDays(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
