package raf.gymreservationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import raf.gymreservationservice.domain.Gym;
import raf.gymreservationservice.domain.TrainingType;

import java.math.BigDecimal;

@Getter
@Setter
public class GymTrainingDto {
    private Long id;
    @JsonProperty("gym")
    private GymDto gymDto;
    @JsonProperty("trainingType")
    private TrainingTypeDto trainingTypeDto;
    private BigDecimal price;
}
