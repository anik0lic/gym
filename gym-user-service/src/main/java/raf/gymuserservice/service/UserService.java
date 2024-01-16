package raf.gymuserservice.service;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.gymuserservice.dto.*;

public interface UserService {
    Page<UserDto> findAll(Pageable pageable);
    UserDto findById(Long id);
    UserDto update(Long id, UserDto userDto);
    UserDto ban(Long id, boolean ban);
    TokenResponseDto login(TokenRequestDto tokenRequestDto);
    Long verifyToken(String token);
}
