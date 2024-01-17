package raf.gymuserservice.service;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.gymuserservice.domain.User;
import raf.gymuserservice.dto.*;

public interface UserService {
    Page<UserDto> findAll(Pageable pageable);
    UserDto findById(Long id);
    UpdateDto update(Long id, UpdateDto updateDto);
    UserDto ban(Long id, boolean ban);
    void deleteById(Long id);
    TokenResponseDto login(TokenRequestDto tokenRequestDto);
    Long verifyToken(String token);
}
