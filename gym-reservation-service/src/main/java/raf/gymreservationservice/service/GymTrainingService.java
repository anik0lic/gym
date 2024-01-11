package raf.gymreservationservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.gymreservationservice.dto.GymTrainingCreateDto;
import raf.gymreservationservice.dto.GymTrainingDto;

public interface GymTrainingService {
    Page<GymTrainingDto> findAll(Pageable pageable);
    GymTrainingDto add(GymTrainingCreateDto gymTrainingCreateDto);
    void deleteById(Long id);
}
