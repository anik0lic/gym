package raf.gymuserservice.service;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.gymuserservice.dto.*;

public interface UserService {
    Page<UserDto> findAll(Pageable pageable);
    TokenResponseDto login(TokenRequestDto tokenRequestDto);
}
