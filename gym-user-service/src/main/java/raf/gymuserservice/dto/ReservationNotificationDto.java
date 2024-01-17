package raf.gymuserservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReservationNotificationDto {
    private String clientMail;
    private String clientFirstName;
    private String clientLastName;
    private String managerMail;
    private String managerFirstName;
    private String managerLastName;
}
