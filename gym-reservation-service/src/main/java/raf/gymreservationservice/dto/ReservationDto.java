package raf.gymreservationservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReservationDto {
    private Long id;
    private AppointmentDto appointment;
    private Long userId;
    private BigDecimal price;
}
