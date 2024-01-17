package raf.gymreservationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.gymreservationservice.domain.Gym;
import raf.gymreservationservice.dto.GymDto;

public interface GymRepository extends JpaRepository<Gym, Long> {
    Gym findGymByName(String name);
}
