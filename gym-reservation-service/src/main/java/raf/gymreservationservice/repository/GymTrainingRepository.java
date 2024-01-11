package raf.gymreservationservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import raf.gymreservationservice.domain.GymTraining;

public interface GymTrainingRepository extends JpaRepository<GymTraining, Long> {
}
