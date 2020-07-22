package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.EmailVerificationToken;
import com.happyride.eservice.entity.model.PhoneNumberVerificationToken;
import com.happyride.eservice.entity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneNumberVerificationTokenRepository extends JpaRepository<PhoneNumberVerificationToken, Long> {
    Optional<PhoneNumberVerificationToken> findByToken(String token);

    Optional<PhoneNumberVerificationToken> findByMessageId(String messageId);

    Optional<PhoneNumberVerificationToken> findByUsers(Users token);
}
