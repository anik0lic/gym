package raf.gymreservationservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.gymreservationservice.dto.GymCreateDto;
import raf.gymreservationservice.dto.GymDto;

public interface GymService {
    Page<GymDto> findAll(Pageable pageable);
    GymDto add(GymCreateDto gymCreateDto);
    void deleteById(Long id);
}
