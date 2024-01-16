package raf.gymuserservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Client extends User{
    private Integer numberOfReservations;

    @Override
    public String toString() {
        return "Client{" +
                "numberOfReservations=" + numberOfReservations +
                '}';
    }
}
