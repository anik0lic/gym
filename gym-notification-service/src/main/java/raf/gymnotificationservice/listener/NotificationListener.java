package raf.gymnotificationservice.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import raf.gymnotificationservice.dto.ActivationDto;
import raf.gymnotificationservice.dto.NotificationCancellationDto;
import raf.gymnotificationservice.dto.ReservationNotificationDto;
import raf.gymnotificationservice.dto.UserDto;
import raf.gymnotificationservice.listener.helper.MessageHelper;
import raf.gymnotificationservice.service.NotificationService;

import javax.jms.JMSException;
import javax.jms.Message;

import static raf.gymnotificationservice.utils.EmailUtils.getEmailMessage;

@Component
public class NotificationListener {
    private MessageHelper messageHelper;
    private NotificationService notificationService;

    public NotificationListener(MessageHelper messageHelper, NotificationService notificationService) {
        this.messageHelper = messageHelper;
        this.notificationService = notificationService;
    }

    @JmsListener(destination = "${destination.sendConfirmationEmail}", concurrency = "5-10")
    public void confirmationMail(Message message) throws JMSException, InterruptedException {
        ActivationDto activationDto = messageHelper.getMessage(message, ActivationDto.class);
        notificationService.sendActivationEmail(activationDto);
    }

    @JmsListener(destination = "${destination.sendPasswordEmail}", concurrency = "5-10")
    public void passwordResetMail(Message message) throws JMSException, InterruptedException {
        UserDto user = messageHelper.getMessage(message, UserDto.class);
        notificationService.sendPasswordResetEmail(user);
    }

    @JmsListener(destination = "${destination.sendReservationEmail}", concurrency = "5-10")
    public void successfulReservationMail(Message message) throws JMSException, InterruptedException {
        ReservationNotificationDto reservationNotificationDto = messageHelper.getMessage(message, ReservationNotificationDto.class);
        notificationService.sendSuccessfulReservationNotification(reservationNotificationDto);
    }

    @JmsListener(destination = "${destination.sendCancellationEmail}", concurrency = "5-10")
    public void cancellationMail(Message message) throws JMSException {
        NotificationCancellationDto notificationCancellationDto = messageHelper.getMessage(message, NotificationCancellationDto.class);
        notificationService.sendAppointmentCancellationNotification(notificationCancellationDto);
    }

    @JmsListener(destination = "${destination.sendResCancellationEmail}", concurrency = "5-10")
    public void resCancellationMail(Message message) throws JMSException {
        NotificationCancellationDto notificationCancellationDto = messageHelper.getMessage(message, NotificationCancellationDto.class);
        notificationService.sendReservationCancellationNotification(notificationCancellationDto);
    }

    @JmsListener(destination = "${destination.sendRemainderEmail}", concurrency = "5-10")
    public void remainderMail(Message message) throws JMSException {
        UserDto user = messageHelper.getMessage(message, UserDto.class);
//        notificationService.sendCancellationNotification(user);
    }
}
