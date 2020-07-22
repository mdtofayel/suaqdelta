package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.EmailVerificationToken;
import com.happyride.eservice.entity.model.Users;
import com.happyride.eservice.repository.EmailVerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailVerificationTokenService {

    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Autowired
    public EmailVerificationTokenService(EmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    public Optional<EmailVerificationToken> findById(Long id) {
        return emailVerificationTokenRepository.findById(id);
    }

    public Optional<EmailVerificationToken> findByUsers(Users users) {
        return emailVerificationTokenRepository.findByUsers(users);
    }

    public void saveEmailVerificationToken(EmailVerificationToken emailVerificationToken) {
        emailVerificationTokenRepository.save(emailVerificationToken);
    }

    public Optional<EmailVerificationToken> getEmailVerificationTokenByToken(String token) {
        return emailVerificationTokenRepository.findByToken(token);
    }

    public void delete(EmailVerificationToken emailVerificationToken) {
        emailVerificationTokenRepository.delete(emailVerificationToken);
    }
}
