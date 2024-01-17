package raf.gymuserservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class NotificationCancellationDto {
    List<String> clientEmails;
    String managerEmail;
}
