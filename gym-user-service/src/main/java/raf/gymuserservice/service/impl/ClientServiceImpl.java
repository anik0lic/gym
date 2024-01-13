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
import raf.gymuserservice.repository.ClientRepository;
import raf.gymuserservice.repository.ConfirmationRepository;
import raf.gymuserservice.repository.RoleRepository;
import raf.gymuserservice.repository.UserStatusRepository;
import raf.gymuserservice.service.ClientService;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private UserStatusRepository userStatusRepository;
    private RoleRepository roleRepository;
    private ConfirmationRepository confirmationRepository;
    private UserMapper userMapper;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;
    private String emailQueueDestination;

    public ClientServiceImpl(ClientRepository clientRepository, UserStatusRepository userStatusRepository, RoleRepository roleRepository,
                             ConfirmationRepository confirmationRepository, UserMapper userMapper, JmsTemplate jmsTemplate, MessageHelper messageHelper,
                             @Value("${destination.sendEmails}") String emailQueueDestination) {
        this.clientRepository = clientRepository;
        this.userStatusRepository = userStatusRepository;
        this.roleRepository = roleRepository;
        this.confirmationRepository = confirmationRepository;
        this.userMapper = userMapper;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.emailQueueDestination = emailQueueDestination;
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
        jmsTemplate.convertAndSend(emailQueueDestination, messageHelper.createTextMessage(activationDto));

        return userMapper.clientToClientDto(client);
    }

    @Override
    public void incrementReservationCount(IncrementReservationCountDto incrementReservationCountDto) {
        Client client = (Client) clientRepository.findById(incrementReservationCountDto.getUserId()).get();
        client.setNumberOfReservations(client.getNumberOfReservations() + 1);
        clientRepository.save(client);
    }

    @Override
    public Long verifyToken(String token) {
        Confirmation confirmation = confirmationRepository.findByToken(token);
        Client client = (Client) clientRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());

        client.setBan(false);

        clientRepository.save(client);
        confirmationRepository.delete(confirmation);
        return client.getId();
    }
}
