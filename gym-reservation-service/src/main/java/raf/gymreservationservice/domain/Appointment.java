package raf.gymreservationservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    @ManyToOne
    private GymTraining gymTraining;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appointment", orphanRemoval = true)
//    private List<Reservation> reservations;
}
