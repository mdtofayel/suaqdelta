package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.PhoneNumberVerificationToken;
import com.happyride.eservice.entity.model.Users;
import com.happyride.eservice.repository.PhoneNumberVerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhoneNumberVerificationTokenService {

    private final PhoneNumberVerificationTokenRepository phoneNumberVerificationTokenRepository;

    @Autowired
    public PhoneNumberVerificationTokenService(PhoneNumberVerificationTokenRepository phoneNumberVerificationTokenRepository) {
        this.phoneNumberVerificationTokenRepository = phoneNumberVerificationTokenRepository;
    }

    public Optional<PhoneNumberVerificationToken> findById(Long id) {
        return phoneNumberVerificationTokenRepository.findById(id);
    }

    public Optional<PhoneNumberVerificationToken> findByUsers(Users users) {
        return phoneNumberVerificationTokenRepository.findByUsers(users);
    }

    public void savePhoneNumberVerificationToken(PhoneNumberVerificationToken phoneNumberVerificationToken) {
        phoneNumberVerificationTokenRepository.save(phoneNumberVerificationToken);
    }

    public Optional<PhoneNumberVerificationToken> getPhoneNumberVerificationTokenByToken(String token) {
        return phoneNumberVerificationTokenRepository.findByToken(token);
    }

    public Optional<PhoneNumberVerificationToken> getPhoneNumberVerificationTokenByMessageId(String messageId) {
        return phoneNumberVerificationTokenRepository.findByMessageId(messageId);
    }

    public void delete(PhoneNumberVerificationToken phoneNumberVerificationToken) {
        phoneNumberVerificationTokenRepository.delete(phoneNumberVerificationToken);
    }
}
