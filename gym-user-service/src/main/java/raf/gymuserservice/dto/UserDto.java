package raf.gymuserservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private LocalDate dateOfBirth;
    private String lastName;
    private String username;
    private boolean ban;
}
