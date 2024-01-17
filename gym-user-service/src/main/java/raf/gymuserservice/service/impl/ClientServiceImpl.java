package raf.gymuserservice.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.gymuserservice.domain.*;
import raf.gymuserservice.dto.*;
import raf.gymuserservice.exceptions.NotFoundException;
import raf.gymuserservice.listener.MessageHelper;
import raf.gymuserservice.mapper.UserMapper;
import raf.gymuserservice.repository.*;
import raf.gymuserservice.service.ClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private ManagerRepository managerRepository;
    private UserStatusRepository userStatusRepository;
    private RoleRepository roleRepository;
    private ConfirmationRepository confirmationRepository;
    private UserMapper userMapper;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;
    private String emailCnfQueueDestination;
    private String emailResQueueDestination;
    private String emailCnclQueueDestination;
    private String emailResCnclQueueDestination;

    public ClientServiceImpl(ClientRepository clientRepository, ManagerRepository managerRepository, UserStatusRepository userStatusRepository, RoleRepository roleRepository,
                             ConfirmationRepository confirmationRepository, UserMapper userMapper, JmsTemplate jmsTemplate, MessageHelper messageHelper,
                             @Value("${destination.sendConfirmationEmail}") String emailCnfQueueDestination,
                             @Value("${destination.sendReservationEmail}") String emailResQueueDestination,
                             @Value("${destination.sendCancellationEmail}") String emailCnclQueueDestination,
                             @Value("${destination.sendResCancellationEmail}") String emailResCnclQueueDestination) {
        this.clientRepository = clientRepository;
        this.managerRepository = managerRepository;
        this.userStatusRepository = userStatusRepository;
        this.roleRepository = roleRepository;
        this.confirmationRepository = confirmationRepository;
        this.userMapper = userMapper;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.emailCnfQueueDestination = emailCnfQueueDestination;
        this.emailResQueueDestination = emailResQueueDestination;
        this.emailCnclQueueDestination = emailCnclQueueDestination;
        this.emailResCnclQueueDestination = emailResCnclQueueDestination;
    }

    @Override
    public Page<UserDto> findAllClients(Pageable pageable) {
        return clientRepository.findUserByRole(roleRepository.findRoleByName("ROLE_CLIENT").get(), pageable)
                .map(userMapper::userToUserDto);
    }

    @Override
    public UserDto findClientByID(Long id) {
        User user = clientRepository.findById(id).get();
        return userMapper.userToUserDto(user);
    }

    @Override
    public DiscountDto findDiscount(Long id) {
        User user = clientRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with id: %d not found", id)));
        Client client = (Client) user;
        List<UserStatus> userStatusList = userStatusRepository.findAll();

        Integer discount = userStatusList.stream()
                .filter(userStatus -> userStatus.getMaxNumberOfReservations() >= client.getNumberOfReservations()
                        && userStatus.getMinNumberOfReservations() <= client.getNumberOfReservations())
                .findAny()
                .get()
                .getDiscount();
        return new DiscountDto(discount);
    }

    @Override
    public ClientDto addClient(ClientCreateDto clientCreateDto) {
        if(clientRepository.existsByEmail(clientCreateDto.getEmail())){
            throw new RuntimeException("User already exists!");
        }
        Client client = userMapper.clientCreateDtoToClient(clientCreateDto);
        clientRepository.save(client);

        Confirmation confirmation = new Confirmation(client);
        confirmationRepository.save(confirmation);

        ActivationDto activationDto = new ActivationDto(client.getEmail(), client.getFirstName(), client.getLastName(), confirmation.getToken());
        jmsTemplate.convertAndSend(emailCnfQueueDestination, messageHelper.createTextMessage(activationDto));

        return userMapper.clientToClientDto(client);
    }

    @Override
    public void incrementReservationCount(IncrementReservationCountDto incrementReservationCountDto) {
        Client client = (Client) clientRepository.findById(incrementReservationCountDto.getUserId()).get();
        Manager manager = managerRepository.findByGymName(incrementReservationCountDto.getGymName());
        System.out.println(manager);

        client.setNumberOfReservations(client.getNumberOfReservations() + 1);

        ReservationNotificationDto reservationNotificationDto = new ReservationNotificationDto(client.getEmail(), client.getFirstName(), client.getLastName(), manager.getEmail(), manager.getFirstName(), manager.getLastName());
        jmsTemplate.convertAndSend(emailResQueueDestination, messageHelper.createTextMessage(reservationNotificationDto));
        clientRepository.save(client);
    }

    @Override
    public void appointmentCancellation(CancellationDto cancellationDto) {
        Manager manager = managerRepository.findByGymName(cancellationDto.getGymName());
        List<String> clientEmails = new ArrayList<>();

        for(Long id: cancellationDto.getUserIds()){
            Client client = (Client) clientRepository.findById(id).get();
            clientEmails.add(client.getEmail());
            client.setNumberOfReservations(client.getNumberOfReservations() - 1);
        }

        NotificationCancellationDto notificationCancellationDto = new NotificationCancellationDto(clientEmails, manager.getEmail());
        jmsTemplate.convertAndSend(emailCnclQueueDestination, messageHelper.createTextMessage(notificationCancellationDto));
    }

    @Override
    public void reservationCancellation(CancellationDto cancellationDto) {
        Manager manager = managerRepository.findByGymName(cancellationDto.getGymName());
        Client client = (Client) clientRepository.findById(cancellationDto.getUserIds().get(0)).get();
        client.setNumberOfReservations(client.getNumberOfReservations() - 1);

        NotificationCancellationDto notificationCancellationDto = new NotificationCancellationDto(List.of(client.getEmail()), manager.getEmail());

        jmsTemplate.convertAndSend(emailResCnclQueueDestination, messageHelper.createTextMessage(notificationCancellationDto));
    }
}
