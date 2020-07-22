package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.Users;
import com.happyride.eservice.entity.model.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    Optional<Verification> getByUsers(Users users);
}
