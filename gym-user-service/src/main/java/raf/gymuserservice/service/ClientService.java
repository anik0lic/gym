package raf.gymuserservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.gymuserservice.dto.*;

public interface ClientService {
    Page<UserDto> findAllClients(Pageable pageable);
    UserDto findClientByID(Long id);
    DiscountDto findDiscount(Long id);
    ClientDto addClient(ClientCreateDto clientCreateDto);
    void incrementReservationCount(IncrementReservationCountDto incrementReservationCountDto);
    Long verifyToken(String token);
}
