package raf.gymreservationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
public class AppointmentDto {
    private Long id;
    private LocalDate date;
    private String startTime;
    private String endTime;
    @JsonProperty("gymTraining")
    private GymTrainingDto gymTrainingDto;
    private Integer capacity;
}
