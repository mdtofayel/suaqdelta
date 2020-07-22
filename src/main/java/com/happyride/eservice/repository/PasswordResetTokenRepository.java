package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.PasswordResetToken;
import com.happyride.eservice.entity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUsers(Users token);
}
