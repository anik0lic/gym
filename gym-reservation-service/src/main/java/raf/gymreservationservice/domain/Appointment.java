package raf.gymreservationservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    //11:00-13:00
    private String time;
    @ManyToOne
    private GymTraining gymTraining;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appointment", orphanRemoval = true)
    private List<Reservation> reservations;
}
