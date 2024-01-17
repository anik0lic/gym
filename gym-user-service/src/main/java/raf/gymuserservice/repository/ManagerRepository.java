package raf.gymuserservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.gymuserservice.domain.Manager;
import raf.gymuserservice.domain.User;

@Repository
public interface ManagerRepository extends UserRepository{
    @Query("SELECT m FROM Manager m WHERE m.gym = :name")
    Manager findByGymName(@Param("name") String name);
}
