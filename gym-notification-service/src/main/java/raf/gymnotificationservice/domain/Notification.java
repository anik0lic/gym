package raf.gymnotificationservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String recipientEmail;
    private String content;
    @CreatedDate
    private LocalDate sentDate;

    public Notification(String type, String recipientEmail, String content) {
        this.type = type;
        this.recipientEmail = recipientEmail;
        this.content = content;
    }
}
