package raf.gymreservationservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentCreateDto {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long gymTrainingDto;
}
