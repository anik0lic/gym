package raf.gymuserservice.service.impl;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.gymuserservice.domain.Client;
import raf.gymuserservice.domain.Manager;
import raf.gymuserservice.domain.User;
import raf.gymuserservice.dto.*;
import raf.gymuserservice.mapper.UserMapper;
import raf.gymuserservice.repository.ClientRepository;
import raf.gymuserservice.repository.ManagerRepository;
import raf.gymuserservice.repository.UserRepository;
import raf.gymuserservice.secutiry.service.TokenService;
import raf.gymuserservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private TokenService tokenService;
    private ManagerRepository managerRepository;
    private UserMapper userMapper;

    public UserServiceImpl(TokenService tokenService, ManagerRepository managerRepository, UserMapper userMapper) {
        this.tokenService = tokenService;
        this.managerRepository = managerRepository;
        this.userMapper = userMapper;
    }


    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return managerRepository.findAll(pageable)
                .map(userMapper::userToUserDto);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) throws NotFoundException {
        User user = managerRepository
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
