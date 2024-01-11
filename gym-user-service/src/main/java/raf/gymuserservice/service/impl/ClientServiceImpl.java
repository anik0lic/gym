package raf.gymuserservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.gymuserservice.domain.Client;
import raf.gymuserservice.domain.Role;
import raf.gymuserservice.domain.User;
import raf.gymuserservice.dto.ClientCreateDto;
import raf.gymuserservice.dto.ClientDto;
import raf.gymuserservice.dto.UserDto;
import raf.gymuserservice.mapper.UserMapper;
import raf.gymuserservice.repository.ClientRepository;
import raf.gymuserservice.repository.RoleRepository;
import raf.gymuserservice.service.ClientService;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private RoleRepository roleRepository;
    private UserMapper userMapper;

    public ClientServiceImpl(ClientRepository clientRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserDto> findAllClients(Pageable pageable) {
        return clientRepository.findUserByRole(roleRepository.findRoleByName("ROLE_CLIENT").get(), pageable)
                .map(userMapper::userToUserDto);
    }

    @Override
    public ClientDto addClient(ClientCreateDto clientCreateDto) {
        Client client = userMapper.clientCreateDtoToClient(clientCreateDto);
        clientRepository.save(client);
        return userMapper.clientToClientDto(client);
    }
}
