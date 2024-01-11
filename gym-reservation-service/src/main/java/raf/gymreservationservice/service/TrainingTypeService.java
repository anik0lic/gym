package raf.gymreservationservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.gymreservationservice.dto.TrainingTypeCreateDto;
import raf.gymreservationservice.dto.TrainingTypeDto;

public interface TrainingTypeService {
    Page<TrainingTypeDto> findAll(Pageable pageable);
    TrainingTypeDto add(TrainingTypeCreateDto trainingTypeCreateDto);
    void deleteById(Long id);
}
