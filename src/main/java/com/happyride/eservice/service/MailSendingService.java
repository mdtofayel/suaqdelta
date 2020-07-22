package com.happyride.eservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailSendingService {

    @Autowired
    public JavaMailSender emailSender;

    @Async
    public void sendMail(SimpleMailMessage message) {
        emailSender.send(message);
    }
}
