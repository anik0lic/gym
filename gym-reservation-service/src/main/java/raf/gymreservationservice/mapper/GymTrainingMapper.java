package raf.gymreservationservice.mapper;

import org.springframework.stereotype.Component;
import raf.gymreservationservice.domain.GymTraining;
import raf.gymreservationservice.dto.GymTrainingCreateDto;
import raf.gymreservationservice.dto.GymTrainingDto;
import raf.gymreservationservice.exceptions.NotFoundException;
import raf.gymreservationservice.repository.GymRepository;
import raf.gymreservationservice.repository.TrainingTypeRepository;

@Component
public class GymTrainingMapper {
    private GymRepository gymRepository;
    private TrainingTypeRepository trainingTypeRepository;
    private GymMapper gymMapper;
    private TrainingTypeMapper trainingTypeMapper;

    public GymTrainingMapper(GymRepository gymRepository, TrainingTypeRepository trainingTypeRepository, GymMapper gymMapper, TrainingTypeMapper trainingTypeMapper) {
        this.gymRepository = gymRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.gymMapper = gymMapper;
        this.trainingTypeMapper = trainingTypeMapper;
    }

    public GymTrainingDto gymTrainingToGymTrainingDto(GymTraining gymTraining){
        GymTrainingDto gymTrainingDto = new GymTrainingDto();
        gymTrainingDto.setId(gymTraining.getId());
        gymTrainingDto.setGymDto(gymMapper.gymToGymDto(gymTraining.getGym()));
        gymTrainingDto.setTrainingTypeDto(trainingTypeMapper.trainingTypeToTrainingTypeDto(gymTraining.getTrainingType()));
        gymTrainingDto.setPrice(gymTraining.getPrice());
        return gymTrainingDto;
    }

    public GymTraining gymTrainingCreateDtoToGymTraining(GymTrainingCreateDto gymTrainingCreateDto){
        GymTraining gymTraining = new GymTraining();
        gymTraining.setGym(gymRepository.findById(gymTrainingCreateDto.getGym())
                .orElseThrow(() -> new NotFoundException(String
                        .format("Gym with id: %d does not exists.", gymTrainingCreateDto.getGym()))));
        gymTraining.setTrainingType(trainingTypeRepository.findById(gymTrainingCreateDto.getTrainingType())
                .orElseThrow(() -> new NotFoundException(String
                .format("TrainingType with id: %d does not exists.", gymTrainingCreateDto.getTrainingType()))));
        gymTraining.setPrice(gymTrainingCreateDto.getPrice());
        return gymTraining;
    }
}
