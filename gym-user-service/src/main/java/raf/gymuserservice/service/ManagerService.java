package raf.gymuserservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.gymuserservice.dto.ClientDto;
import raf.gymuserservice.dto.ManagerCreateDto;
import raf.gymuserservice.dto.ManagerDto;
import raf.gymuserservice.dto.UserDto;

public interface ManagerService {
    Page<UserDto> findAllManagers(Pageable pageable);
    ManagerDto addManager(ManagerCreateDto managerCreateDto);
    Boolean verifyToken(String token);
}
