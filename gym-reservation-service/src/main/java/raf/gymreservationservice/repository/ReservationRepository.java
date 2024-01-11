package raf.gymreservationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.gymreservationservice.domain.Appointment;
import raf.gymreservationservice.domain.Reservation;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByUserIdAndAppointmentId(Long user, Long appointment);
}
