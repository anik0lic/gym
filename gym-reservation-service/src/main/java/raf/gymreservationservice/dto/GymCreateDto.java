package raf.gymreservationservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GymCreateDto {
    private String name;
    private String description;
    private Integer numberOfCoaches;
    private Integer capacity;
}
