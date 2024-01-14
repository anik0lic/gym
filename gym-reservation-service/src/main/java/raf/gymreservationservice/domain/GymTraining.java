package raf.gymreservationservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class GymTraining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Gym gym;
    @ManyToOne
    private TrainingType trainingType;
    private BigDecimal price;

    public GymTraining(Gym gym, TrainingType trainingType, BigDecimal price) {
        this.gym = gym;
        this.trainingType = trainingType;
        this.price = price;
    }
}
