package raf.gymreservationservice.mapper;

import org.springframework.stereotype.Component;
import raf.gymreservationservice.domain.TrainingType;
import raf.gymreservationservice.dto.TrainingTypeCreateDto;
import raf.gymreservationservice.dto.TrainingTypeDto;

@Component
public class TrainingTypeMapper {
    public TrainingTypeDto trainingTypeToTrainingTypeDto(TrainingType trainingType){
        TrainingTypeDto trainingTypeDto = new TrainingTypeDto();
        trainingTypeDto.setId(trainingType.getId());
        trainingTypeDto.setType(trainingType.getType());
        trainingTypeDto.setName(trainingType.getName());
        return trainingTypeDto;
    }

    public TrainingType trainingTypeCreateDtoToTrainingType(TrainingTypeCreateDto trainingTypeCreateDto){
        TrainingType trainingType = new TrainingType();
        trainingType.setName(trainingTypeCreateDto.getName());
        trainingType.setType(trainingTypeCreateDto.getType());
        trainingType.setName(trainingTypeCreateDto.getName());
        return trainingType;
    }
}
