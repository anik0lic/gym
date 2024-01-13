package raf.gymnotificationservice.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import raf.gymnotificationservice.dto.UserDto;
import raf.gymnotificationservice.listener.helper.MessageHelper;
import raf.gymnotificationservice.service.EmailService;

import javax.jms.JMSException;
import javax.jms.Message;

import static raf.gymnotificationservice.utils.EmailUtils.getEmailMessage;

@Component
public class EmailListener {
    private MessageHelper messageHelper;
    private EmailService emailService;

    public EmailListener(MessageHelper messageHelper, EmailService emailService) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
    }

//    @JmsListener(destination = "${destination.sendEmails}", concurrency = "5-10")
//    public void confirmationMail(Message message) throws JMSException {
//        UserDto user = messageHelper.getMessage(message, UserDto.class);
//        emailService.sendSimpleMessage(user.getEmail(), "Confirmation Email", getEmailMessage(user.getFirstName(), "http://localhost:8080", "12345"));
//    }

//    @JmsListener(destination = "${destination.sendEmails}", concurrency = "5-10")
//    public void addOrder(Message message) {
//        emailService.sendSimpleMessage("email", "subject", "poruka");
//    }
}
