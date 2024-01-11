package raf.gymreservationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.gymreservationservice.domain.Gym;

public interface GymRepository extends JpaRepository<Gym, Long> {
}
