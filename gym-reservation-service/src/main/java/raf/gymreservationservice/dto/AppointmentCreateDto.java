package raf.gymreservationservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentCreateDto {
    private LocalDate date;
    private String startTime;
    private String endTime;
    private Long gymTraining;
}
