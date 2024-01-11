package raf.gymreservationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.gymreservationservice.domain.TrainingType;

public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long> {
}
