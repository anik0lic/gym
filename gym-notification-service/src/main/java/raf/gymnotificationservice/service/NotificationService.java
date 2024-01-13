package raf.gymnotificationservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.gymnotificationservice.domain.Notification;
import raf.gymnotificationservice.dto.ActivationDto;
import raf.gymnotificationservice.dto.NotificationDto;
import raf.gymnotificationservice.dto.ReservationNotificationDto;
import raf.gymnotificationservice.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface NotificationService{
    void sendActivationEmail(ActivationDto activationDto) throws InterruptedException;
    void sendPasswordResetEmail(UserDto userDto);
    void sendSuccessfulReservationNotification(ReservationNotificationDto reservationNotificationDto);
    void sendCancellationNotification(ReservationNotificationDto reservationNotificationDto);
    void sendReminderNotification(ReservationNotificationDto reservationNotificationDto);

    Page<NotificationDto> getAllNotificationsForAdmin(Pageable pageable);
    Page<NotificationDto> getClientNotifications(Long clientId, Pageable pageable);
    Page<NotificationDto> getManagerNotifications(Long managerId, Pageable pageable);
//    List<Notification> filterNotificationsByTypeAndDate(String type, String email, Date startDate, Date endDate);
}
