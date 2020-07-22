package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.Users;
import com.happyride.eservice.entity.model.Verification;
import com.happyride.eservice.repository.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationService {
    private VerificationRepository verificationRepository;

    @Autowired
    public VerificationService(VerificationRepository verificationRepository) {
        this.verificationRepository = verificationRepository;
    }

    public Optional<Verification> getVerificationByUser(Users users) {
        return verificationRepository.getByUsers(users);
    }

    public Optional<Verification> getVerificationById(Long id) {
        return verificationRepository.findById(id);
    }

    public Verification saveVerification(Verification verification) {
        return verificationRepository.save(verification);
    }
}
