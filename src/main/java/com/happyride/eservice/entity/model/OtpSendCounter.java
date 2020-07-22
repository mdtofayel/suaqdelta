package com.happyride.eservice.entity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class OtpSendCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    private Users users;

    private int count;

    private LocalDateTime tokenCreateTimeAndDate;

    private boolean readyToSendOtp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LocalDateTime getTokenCreateTimeAndDate() {
        return tokenCreateTimeAndDate;
    }

    public void setTokenCreateTimeAndDate(LocalDateTime tokenCreateTimeAndDate) {
        this.tokenCreateTimeAndDate = tokenCreateTimeAndDate;
    }

    public boolean isReadyToSendOtp() {
        return readyToSendOtp;
    }

    public void setReadyToSendOtp(boolean readyToSendOtp) {
        this.readyToSendOtp = readyToSendOtp;
    }
}
