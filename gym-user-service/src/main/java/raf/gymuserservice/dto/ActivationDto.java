package raf.gymuserservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivationDto {
    private String mail;
    private String firstName;
    private String lastName;
    private String token;

    public ActivationDto(String mail, String firstName, String lastName, String token) {
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
    }
}