package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.OtpSendCounter;
import com.happyride.eservice.entity.model.Users;
import com.happyride.eservice.repository.OtpSendCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OtpSendCounterService {
    private final OtpSendCounterRepository otpSendCounterRepository;

    @Autowired
    public OtpSendCounterService(OtpSendCounterRepository otpSendCounterRepository) {
        this.otpSendCounterRepository = otpSendCounterRepository;
    }


    public void addOtpCount(OtpSendCounter otpSendCounter) {
        otpSendCounterRepository.save(otpSendCounter);
    }

    public void deleteOtpCount(OtpSendCounter otpSendCounter) {
        otpSendCounterRepository.delete(otpSendCounter);
    }

    public Optional<OtpSendCounter> getOtpCounterById(Long id) {
        return otpSendCounterRepository.findById(id);
    }

    public Optional<OtpSendCounter> getOtpCounterByUser(Users users) {
        return otpSendCounterRepository.findByUsers(users);
    }
}
