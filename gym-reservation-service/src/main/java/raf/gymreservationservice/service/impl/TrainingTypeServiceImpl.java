package raf.gymreservationservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.gymreservationservice.domain.TrainingType;
import raf.gymreservationservice.dto.TrainingTypeCreateDto;
import raf.gymreservationservice.dto.TrainingTypeDto;
import raf.gymreservationservice.mapper.TrainingTypeMapper;
import raf.gymreservationservice.repository.TrainingTypeRepository;
import raf.gymreservationservice.service.TrainingTypeService;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private TrainingTypeRepository trainingTypeRepository;
    private TrainingTypeMapper trainingTypeMapper;

    public TrainingTypeServiceImpl(TrainingTypeRepository trainingTypeRepository, TrainingTypeMapper trainingTypeMapper) {
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingTypeMapper = trainingTypeMapper;
    }

    @Override
    public Page<TrainingTypeDto> findAll(Pageable pageable) {
        return trainingTypeRepository.findAll(pageable)
                .map(trainingTypeMapper::trainingTypeToTrainingTypeDto);
    }

    @Override
    public TrainingTypeDto add(TrainingTypeCreateDto trainingTypeCreateDto) {
        TrainingType trainingType = trainingTypeMapper.trainingTypeCreateDtoToTrainingType(trainingTypeCreateDto);
        trainingTypeRepository.save(trainingType);
        return trainingTypeMapper.trainingTypeToTrainingTypeDto(trainingType);
    }

    @Override
    public void deleteById(Long id) {
        trainingTypeRepository.deleteById(id);
    }
}
