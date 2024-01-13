package raf.gymnotificationservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NotificationDto {
    private Long id;
    private String type;
    private String recipientEmail;
    private String content;
    private LocalDate sentDate;
}
