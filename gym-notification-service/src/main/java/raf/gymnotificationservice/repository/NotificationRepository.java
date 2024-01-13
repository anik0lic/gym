package raf.gymnotificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.gymnotificationservice.domain.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientEmail(String recipientEmail);
}
