package raf.gymnotificationservice.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import raf.gymnotificationservice.domain.Notification;
import raf.gymnotificationservice.dto.*;
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
    public void sendPasswordResetEmail(UserDto userDto) throws InterruptedException {
        String content = "Hello " + userDto.getFirstName() + ",\n\nPlease click the link below to reset your password. \n\n" +
                "http://localhost:8084/reset-password/" + userDto.getId();

        Notification notification = new Notification("password", userDto.getEmail(), content);
        notificationRepository.save(notification);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userDto.getEmail());
        message.setSubject("Reset your Password");
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
    public void sendSuccessfulReservationNotification(ReservationNotificationDto reservationNotificationDto) throws InterruptedException {
        String contentClient = "Hello " + reservationNotificationDto.getClientFirstName() + ",\n\nYou successfully booked your appointment. \n\n" + "See you soon";
        String contentManager = "Hello " + reservationNotificationDto.getManagerMail() + ",\n\nYou have new reservation.";

        List<Notification> notifications = notificationMapper.reservationNotifDtoToNotification(reservationNotificationDto, "successful-reservation", contentClient,"new-reservation", contentManager);
        notificationRepository.save(notifications.get(0));
        notificationRepository.save(notifications.get(1));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(reservationNotificationDto.getClientMail());
        message.setSubject("Successful Reservation");
        message.setFrom(fromEmail);
        message.setText(contentClient);

        SimpleMailMessage message2 = new SimpleMailMessage();
        message.setTo(reservationNotificationDto.getManagerMail());
        message.setSubject("New Reservation");
        message.setFrom(fromEmail);
        message.setText(contentManager);

        int maxRetries = 3;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                mailSender.send(message);
                mailSender.send(message2);
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
    public void sendAppointmentCancellationNotification(NotificationCancellationDto notificationCancellationDto) {
        String contentClient = "Training has been cancelled.";
        String contentManager = "You deleted appointment.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notificationCancellationDto.getManagerEmail());
        message.setSubject("Deleted Appointment");
        message.setFrom(fromEmail);
        message.setText(contentManager);
        notificationRepository.save(new Notification("appointment-cancellation", notificationCancellationDto.getManagerEmail(), contentManager));
        mailSender.send(message);

        for(String email: notificationCancellationDto.getClientEmails()){
            SimpleMailMessage message2 = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Training Cancelled");
            message.setFrom(fromEmail);
            message.setText(contentClient);
            notificationRepository.save(new Notification("appointment-cancellation", email, contentClient));
            mailSender.send(message2);
        }
    }

    @Override
    public void sendReservationCancellationNotification(NotificationCancellationDto notificationCancellationDto) {
        String contentClient = "You have deleted your reservation.";
        String contentManager = "Client has canceled his reservation";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notificationCancellationDto.getManagerEmail());
        message.setSubject("Deleted Reservation");
        message.setFrom(fromEmail);
        message.setText(contentManager);
        notificationRepository.save(new Notification("reservation-cancellation", notificationCancellationDto.getManagerEmail(), contentManager));
        mailSender.send(message);

        SimpleMailMessage message2 = new SimpleMailMessage();
        message.setTo(notificationCancellationDto.getClientEmails().get(0));
        message.setSubject("Deleted Reservation");
        message.setFrom(fromEmail);
        message.setText(contentClient);
        notificationRepository.save(new Notification("reservation-cancellation", notificationCancellationDto.getClientEmails().get(0), contentClient));
        mailSender.send(message2);
    }

    @Override
    public void sendReminderNotification(ReservationNotificationDto reservationNotificationDto) {

    }

    @Override
    public Page<NotificationDto> getAllNotificationsForAdmin(Pageable pageable) {
        return notificationRepository.findAll(pageable)
                .map(notificationMapper::notificationToNotificationDto);
    }
}
