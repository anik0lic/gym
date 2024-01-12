package raf.gymnotificationservice.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import raf.gymnotificationservice.listener.helper.MessageHelper;
import raf.gymnotificationservice.service.EmailService;

import javax.jms.Message;

@Component
public class EmailListener {
    private MessageHelper messageHelper;
    private EmailService emailService;

    public EmailListener(MessageHelper messageHelper, EmailService emailService) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
    }

    @JmsListener(destination = "${destination.sendEmails}", concurrency = "5-10")
    public void addOrder(Message message) {
        emailService.sendSimpleMessage("email", "subject", "poruka");
    }
}
