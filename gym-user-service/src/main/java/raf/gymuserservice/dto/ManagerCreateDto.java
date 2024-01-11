package raf.gymuserservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class ManagerCreateDto {
    @Email
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String username;
    @Length(min = 8, max = 20)
    private String password;
    @NotNull
    private Date dateOfBirth;
    @NotNull
    private Date startDate;
    @NotBlank
    private String gym;
}
