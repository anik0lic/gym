package raf.gymreservationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.gymreservationservice.domain.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
