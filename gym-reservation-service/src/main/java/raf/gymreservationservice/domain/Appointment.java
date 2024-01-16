package raf.gymreservationservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDate date;
    private String startTime;
    private String endTime;
    @ManyToOne
    private GymTraining gymTraining;
    private Integer capacity;

    public Appointment(LocalDate date, String startTime, String endTime, GymTraining gymTraining, Integer capacity) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gymTraining = gymTraining;
        this.capacity = capacity;
    }
}
