package raf.gymuserservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.gymuserservice.domain.Client;
import raf.gymuserservice.domain.Role;
import raf.gymuserservice.domain.User;
import raf.gymuserservice.domain.UserStatus;
import raf.gymuserservice.dto.*;
import raf.gymuserservice.exceptions.NotFoundException;
import raf.gymuserservice.mapper.UserMapper;
import raf.gymuserservice.repository.ClientRepository;
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
    private UserMapper userMapper;

    public ClientServiceImpl(ClientRepository clientRepository, UserStatusRepository userStatusRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.clientRepository = clientRepository;
        this.userStatusRepository = userStatusRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserDto> findAllClients(Pageable pageable) {
        return clientRepository.findUserByRole(roleRepository.findRoleByName("ROLE_CLIENT").get(), pageable)
                .map(userMapper::userToUserDto);
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
        Client client = userMapper.clientCreateDtoToClient(clientCreateDto);
        clientRepository.save(client);
        return userMapper.clientToClientDto(client);
    }

    @Override
    public void incrementReservationCount(IncrementReservationCountDto incrementReservationCountDto) {
        Client client = (Client) clientRepository.findById(incrementReservationCountDto.getUserId()).get();
        client.setNumberOfReservations(client.getNumberOfReservations() + 1);
        clientRepository.save(client);
    }
}
