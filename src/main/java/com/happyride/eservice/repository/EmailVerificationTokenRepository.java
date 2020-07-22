package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.EmailVerificationToken;
import com.happyride.eservice.entity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
    Optional<EmailVerificationToken> findByToken(String token);

    Optional<EmailVerificationToken> findByUsers(Users token);
}
