package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.OtpSendCounter;
import com.happyride.eservice.entity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpSendCounterRepository extends JpaRepository<OtpSendCounter, Long> {
    Optional<OtpSendCounter> findByUsers(Users users);
}
