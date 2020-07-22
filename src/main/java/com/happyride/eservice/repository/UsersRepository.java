package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String username);

    Users findUserByEmail(String email);

    Optional<Users> findById(Long id);
}
