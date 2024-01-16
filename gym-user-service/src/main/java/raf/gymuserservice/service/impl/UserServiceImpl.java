package raf.gymuserservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.gymuserservice.domain.User;
import raf.gymuserservice.dto.*;
import raf.gymuserservice.exceptions.NotFoundException;
import raf.gymuserservice.mapper.UserMapper;
import raf.gymuserservice.repository.*;
import raf.gymuserservice.security.service.TokenService;
import raf.gymuserservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private TokenService tokenService;
    private AdminRepository adminRepository;
    private ConfirmationRepository confirmationRepository;
    private UserMapper userMapper;

    public UserServiceImpl(TokenService tokenService, AdminRepository adminRepository, ConfirmationRepository confirmationRepository, UserMapper userMapper) {
        this.tokenService = tokenService;
        this.adminRepository = adminRepository;
        this.confirmationRepository = confirmationRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return adminRepository.findAll(pageable)
                .map(userMapper::userToUserDto);
    }

    @Override
    public UserDto findById(Long id) {
        User user = adminRepository.findById(id).get();
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        User user = adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setDateOfBirth(userDto.getDateOfBirth());
        return userMapper.userToUserDto(adminRepository.save(user));
    }

    @Override
    public UserDto ban(Long id, boolean ban) {
        User user = adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));
        user.setBan(ban);
        return userMapper.userToUserDto(adminRepository.save(user));
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto){
        User user = adminRepository
                .findUserByUsernameAndPassword(tokenRequestDto.getUsername(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new raf.gymuserservice.exceptions.NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getUsername(),
                                tokenRequestDto.getPassword())));
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().getName());
        return new TokenResponseDto(tokenService.generate(claims));
    }
}
