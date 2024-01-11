package raf.gymuserservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.gymuserservice.dto.ClientCreateDto;
import raf.gymuserservice.dto.ClientDto;
import raf.gymuserservice.dto.UserDto;

public interface ClientService {
    Page<UserDto> findAllClients(Pageable pageable);
    ClientDto addClient(ClientCreateDto clientCreateDto);
}
