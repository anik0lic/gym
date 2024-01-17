package raf.gymreservationservice.configuration.userservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IncrementReservationCountDto {
    private Long userId;
    private String gymName;

    public IncrementReservationCountDto(Long userId, String gymName) {
        this.userId = userId;
        this.gymName = gymName;
    }
}