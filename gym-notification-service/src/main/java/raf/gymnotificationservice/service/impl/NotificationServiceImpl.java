package raf.gymnotificationservice.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import raf.gymnotificationservice.domain.Notification;
import raf.gymnotificationservice.dto.ActivationDto;
import raf.gymnotificationservice.dto.NotificationDto;
import raf.gymnotificationservice.dto.ReservationNotificationDto;
import raf.gymnotificationservice.dto.UserDto;
import raf.gymnotificationservice.mapper.NotificationMapper;
import raf.gymnotificationservice.repository.NotificationRepository;
import raf.gymnotificationservice.service.NotificationService;

import java.util.List;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {
    public JavaMailSender mailSender;
    private String fromEmail;
    private NotificationRepository notificationRepository;
    private NotificationMapper notificationMapper;

    public NotificationServiceImpl(JavaMailSender mailSender, @Value("${spring.mail.username}") String fromEmail, NotificationRepository notificationRepository,
                                   NotificationMapper notificationMapper) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }

    @Override
    public void sendActivationEmail(ActivationDto activationDto) throws InterruptedException {
        String content = "Hello " + activationDto.getFirstName() + ",\n\nYour new account has been created. Please click the link below to verify your account. \n\n" +
                "http://localhost:8083/gym-user-service/api/user/confirm-account?token=" + activationDto.getToken();

        Notification notification = notificationMapper.activationDtoToNotification(activationDto, "actication", content);
        notificationRepository.save(notification);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(activationDto.getMail());
        message.setSubject("Activate You Account");
        message.setFrom(fromEmail);
        message.setText(content);
        int maxRetries = 3;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                mailSender.send(message);
                System.out.println("email sent successfully");
                return;
            } catch (MailAuthenticationException e){
                System.err.println("Authentication failed. Waiting before the next attempt.");
                try {
                    Thread.sleep(5000); // 5 seconds delay
                } catch (InterruptedException interruptedException){
                    Thread.currentThread().interrupt();
                }
            }
        }
        try {
            mailSender.send(message);
        } catch (MailAuthenticationException e){
            System.err.println("Authentication failed. Waiting before the next attempt.");
            Thread.sleep(5000); // 5 seconds delay
        }
    }

    @Override
    public void sendPasswordResetEmail(UserDto userDto) {

    }

    @Override
    public void sendSuccessfulReservationNotification(ReservationNotificationDto reservationNotificationDto) {
        String contentClient = "Hello " + reservationNotificationDto.getClientFirstName() + ",\n\nYou successfully booked your appointment. \n\n" +
                "See you soon";
        String contentManager = "Hello " + reservationNotificationDto.getManagerMail() + ",\n\nYou have new reservation.";

        List<Notification> notifications = notificationMapper.reservationNotifDtoToNotification(reservationNotificationDto, "successful-reservation", contentClient,"new-reservation", contentManager);
        notificationRepository.save(notifications.get(0));
        notificationRepository.save(notifications.get(1));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(reservationNotificationDto.getClientMail());
        message.setSubject("Successful Reservation");
        message.setFrom(fromEmail);
        message.setText(contentClient);
        mailSender.send(message);

        SimpleMailMessage message2 = new SimpleMailMessage();
        message.setTo(reservationNotificationDto.getManagerMail());
        message.setSubject("New Reservation");
        message.setFrom(fromEmail);
        message.setText(contentManager);
        mailSender.send(message2);
    }

    @Override
    public void sendCancellationNotification(ReservationNotificationDto reservationNotificationDto) {

    }

    @Override
    public void sendReminderNotification(ReservationNotificationDto reservationNotificationDto) {

    }

    @Override
    public Page<NotificationDto> getAllNotificationsForAdmin(Pageable pageable) {
        return notificationRepository.findAll(pageable)
                .map(notificationMapper::notificationToNotificationDto);
    }

    @Override
    public Page<NotificationDto> getClientNotifications(Long clientId, Pageable pageable) {
        //nadji klijenta
        return null;
    }

    @Override
    public Page<NotificationDto> getManagerNotifications(Long managerId, Pageable pageable) {
        return null;
    }
}
