package raf.gymuserservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.gymuserservice.domain.UserStatus;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
}
