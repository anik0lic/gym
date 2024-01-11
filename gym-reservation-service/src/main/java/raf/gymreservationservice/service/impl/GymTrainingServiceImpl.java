package raf.gymreservationservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.gymreservationservice.domain.Gym;
import raf.gymreservationservice.domain.GymTraining;
import raf.gymreservationservice.dto.GymTrainingCreateDto;
import raf.gymreservationservice.dto.GymTrainingDto;
import raf.gymreservationservice.mapper.GymTrainingMapper;
import raf.gymreservationservice.repository.GymTrainingRepository;
import raf.gymreservationservice.service.GymTrainingService;

@Service
public class GymTrainingServiceImpl implements GymTrainingService {
    private GymTrainingRepository gymTrainingRepository;
    private GymTrainingMapper gymTrainingMapper;

    public GymTrainingServiceImpl(GymTrainingRepository gymTrainingRepository, GymTrainingMapper gymTrainingMapper) {
        this.gymTrainingRepository = gymTrainingRepository;
        this.gymTrainingMapper = gymTrainingMapper;
    }

    @Override
    public Page<GymTrainingDto> findAll(Pageable pageable) {
        return gymTrainingRepository.findAll(pageable)
                .map(gymTrainingMapper::gymTrainingToGymTrainingDto);
    }

    @Override
    public GymTrainingDto add(GymTrainingCreateDto gymTrainingCreateDto) {
        GymTraining gymTraining = gymTrainingMapper.gymTrainingCreateDtoToGymTraining(gymTrainingCreateDto);
        gymTrainingRepository.save(gymTraining);
        return gymTrainingMapper.gymTrainingToGymTrainingDto(gymTraining);
    }

    @Override
    public void deleteById(Long id) {
        gymTrainingRepository.deleteById(id);
    }
}
