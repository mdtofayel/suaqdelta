package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.PasswordResetToken;
import com.happyride.eservice.entity.model.Users;
import com.happyride.eservice.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public Optional<PasswordResetToken> findById(Long id) {
        return passwordResetTokenRepository.findById(id);
    }

    public Optional<PasswordResetToken> findByUsers(Users users) {
        return passwordResetTokenRepository.findByUsers(users);
    }

    public void savePasswordReset(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.save(passwordResetToken);
    }

    public Optional<PasswordResetToken> getPasswordResetByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    public void delete(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.delete(passwordResetToken);
    }
}
