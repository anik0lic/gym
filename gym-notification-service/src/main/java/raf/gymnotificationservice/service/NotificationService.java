package raf.gymnotificationservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.gymnotificationservice.domain.Notification;
import raf.gymnotificationservice.dto.*;

import java.util.List;
import java.util.Map;

public interface NotificationService{
    void sendActivationEmail(ActivationDto activationDto) throws InterruptedException;
    void sendPasswordResetEmail(UserDto userDto) throws InterruptedException;
    void sendSuccessfulReservationNotification(ReservationNotificationDto reservationNotificationDto) throws InterruptedException;
    void sendAppointmentCancellationNotification(NotificationCancellationDto notificationCancellationDto);
    void sendReservationCancellationNotification(NotificationCancellationDto notificationCancellationDto);
    void sendReminderNotification(ReservationNotificationDto reservationNotificationDto);

    Page<NotificationDto> getAllNotificationsForAdmin(Pageable pageable);
}
