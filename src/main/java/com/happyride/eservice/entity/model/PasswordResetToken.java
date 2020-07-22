package com.happyride.eservice.entity.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class PasswordResetToken implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long passwordResetId;

    @Column(unique = true)
    private String token;

    @OneToOne
    private Users users;

    private LocalDateTime createTime;

    private LocalDateTime expireTime;

    public Long getPasswordResetId() {
        return passwordResetId;
    }

    public void setPasswordResetId(Long passwordResetId) {
        this.passwordResetId = passwordResetId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
