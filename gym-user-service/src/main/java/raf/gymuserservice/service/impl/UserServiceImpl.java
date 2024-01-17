package raf.gymuserservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.gymuserservice.domain.Client;
import raf.gymuserservice.domain.Confirmation;
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
    public UpdateDto update(Long id, UpdateDto updateDto) {
        User user = adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));
        user.setEmail(updateDto.getEmail());
        user.setFirstName(updateDto.getFirstName());
        user.setLastName(updateDto.getLastName());
        user.setUsername(updateDto.getUsername());
        user.setDateOfBirth(updateDto.getDateOfBirth());
        adminRepository.save(user);
        return userMapper.userToUpdateDto(user);
    }

    @Override
    public UserDto ban(Long id, boolean ban) {
        User user = adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));
        user.setBan(ban);
        return userMapper.userToUserDto(adminRepository.save(user));
    }

    @Override
    public void deleteById(Long id) {
        adminRepository.deleteById(id);
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

    @Override
    public Long verifyToken(String token) {
        Confirmation confirmation = confirmationRepository.findByToken(token);
        Client client = (Client) adminRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());

        client.setBan(false);

        adminRepository.save(client);
        confirmationRepository.delete(confirmation);
        return client.getId();
    }
}
