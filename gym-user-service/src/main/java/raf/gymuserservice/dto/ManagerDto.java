package raf.gymuserservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ManagerDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private Date startDate;
    private String gym;
}
