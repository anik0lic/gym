package raf.gymuserservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.gymuserservice.domain.Confirmation;

public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
    Confirmation findByToken(String token);
}
