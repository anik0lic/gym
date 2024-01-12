package raf.gymuserservice.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import raf.gymuserservice.dto.IncrementReservationCountDto;
import raf.gymuserservice.service.ClientService;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class IncrementReservationCountListener {
    private MessageHelper messageHelper;
    private ClientService clientService;

    public IncrementReservationCountListener(MessageHelper messageHelper, ClientService clientService) {
        this.messageHelper = messageHelper;
        this.clientService = clientService;
    }

    @JmsListener(destination = "${destination.incrementReservationCount}", concurrency = "5-10")
    public void incrementReservationCount(Message message) throws JMSException {
        IncrementReservationCountDto incrementReservationCountDto = messageHelper.getMessage(message, IncrementReservationCountDto.class);
        System.out.println(incrementReservationCountDto);
        clientService.incrementReservationCount(incrementReservationCountDto);
    }
}
