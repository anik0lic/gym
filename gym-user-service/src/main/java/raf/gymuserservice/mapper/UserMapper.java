package raf.gymuserservice.mapper;

import raf.gymuserservice.domain.Client;
import raf.gymuserservice.domain.User;
//import raf.gymuserservice.dto.UserCreateDto;
import raf.gymuserservice.dto.ClientCreateDto;
import raf.gymuserservice.dto.ClientDto;
import raf.gymuserservice.dto.UserCreateDto;
import raf.gymuserservice.dto.UserDto;
import raf.gymuserservice.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        return userDto;
    }

    public ClientDto userToClientDto(Client user) {
        ClientDto userDto = new ClientDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setNumberOfReservations(user.getNumberOfReservations());
        return userDto;
    }

    public User userCreateDtoToUser(UserCreateDto userCreateDto) {
        User user = new User();
        user.setEmail(userCreateDto.getEmail());
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setRole(roleRepository.findRoleByName("ROLE_USER").get());
        user.setDateOfBirth(userCreateDto.getDateOfBirth());
//        user.setNumberOfReservations(0);
        return user;
    }

    public Client clientCreateDtoToUser(ClientCreateDto clientCreateDto) {
        Client user = new Client();
        user.setEmail(clientCreateDto.getEmail());
        user.setFirstName(clientCreateDto.getFirstName());
        user.setLastName(clientCreateDto.getLastName());
        user.setUsername(clientCreateDto.getUsername());
        user.setPassword(clientCreateDto.getPassword());
        user.setRole(roleRepository.findRoleByName("ROLE_CLIENT").get());
        user.setDateOfBirth(clientCreateDto.getDateOfBirth());
        user.setNumberOfReservations(clientCreateDto.getNumberOfReservations());
//        user.setNumberOfReservations(0);
        return user;
    }
}
