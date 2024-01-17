package raf.gymuserservice.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import raf.gymuserservice.dto.CancellationDto;
import raf.gymuserservice.dto.ClientCreateDto;
import raf.gymuserservice.dto.IncrementReservationCountDto;
import raf.gymuserservice.service.ClientService;
import raf.gymuserservice.service.UserService;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class CancellationListener {
    private MessageHelper messageHelper;
    private ClientService clientService;

    public CancellationListener(MessageHelper messageHelper, ClientService clientService) {
        this.messageHelper = messageHelper;
        this.clientService = clientService;
    }

    @JmsListener(destination = "${destination.appointmentCancel}", concurrency = "5-10")
    public void appointmentCancellation(Message message) throws JMSException {
        CancellationDto cancellationDto = messageHelper.getMessage(message, CancellationDto.class);
        System.out.println(cancellationDto);
        clientService.appointmentCancellation(cancellationDto);
    }

    @JmsListener(destination = "${destination.reservationCancel}", concurrency = "5-10")
    public void reservationCancellation(Message message) throws JMSException {
        CancellationDto cancellationDto = messageHelper.getMessage(message, CancellationDto.class);
        System.out.println(cancellationDto);
        clientService.reservationCancellation(cancellationDto);
    }
}
