package raf.gymuserservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.domain.Pageable;
import raf.gymuserservice.domain.Manager;
import raf.gymuserservice.domain.Role;
import raf.gymuserservice.domain.User;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsernameAndPassword(String username, String password);
    Page<User> findUserByRole(Role role, Pageable pageable);
    User findByEmailIgnoreCase(String email);
    Boolean existsByEmail(String email);
}
