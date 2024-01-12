package raf.gymreservationservice.configuration.userservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IncrementReservationCountDto {
    private Long userId;
    public IncrementReservationCountDto(Long userId){
        this.userId = userId;
    }
}