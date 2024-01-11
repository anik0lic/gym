package raf.gymuserservice.service.impl;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.gymuserservice.domain.Client;
import raf.gymuserservice.domain.User;
import raf.gymuserservice.dto.*;
import raf.gymuserservice.mapper.UserMapper;
import raf.gymuserservice.repository.ClientRepository;
import raf.gymuserservice.repository.UserRepository;
import raf.gymuserservice.secutiry.service.TokenService;
import raf.gymuserservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private TokenService tokenService;
    private UserRepository userRepository;
    private ClientRepository clientRepository;
    private UserMapper userMapper;

    public UserServiceImpl(TokenService tokenService, UserRepository userRepository, ClientRepository clientRepository, UserMapper userMapper) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::userToUserDto);
        }

    @Override
    public UserDto add(UserCreateDto userCreateDto) {
        User user = userMapper.userCreateDtoToUser(userCreateDto);
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public ClientDto addClient(ClientCreateDto clientCreateDto) {
        Client user = userMapper.clientCreateDtoToUser(clientCreateDto);
        clientRepository.save(user);
        return userMapper.userToClientDto(user);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) throws NotFoundException {
        User user = userRepository
                .findUserByUsernameAndPassword(tokenRequestDto.getUsername(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getUsername(),
                                tokenRequestDto.getPassword())));
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().getName());
        return new TokenResponseDto(tokenService.generate(claims));
    }
}
