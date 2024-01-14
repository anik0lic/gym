package raf.gymreservationservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class Gym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer numberOfCoaches;
    private Integer capacity;

    public Gym(String name, String description, Integer numberOfCoaches, Integer capacity) {
        this.name = name;
        this.description = description;
        this.numberOfCoaches = numberOfCoaches;
        this.capacity = capacity;
    }
}
