package raf.gymuserservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Manager extends User{
    private Date startDate;
    private String gym;
}
