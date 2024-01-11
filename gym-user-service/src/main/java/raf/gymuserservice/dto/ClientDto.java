package raf.gymuserservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private Integer numberOfReservations;
}
