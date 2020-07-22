package com.happyride.eservice.entity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String notificationUrl;

    @ManyToOne
    private Users notificationSender;

    @ManyToOne
    private Users notificationReceiver;

    private String description;

    private Boolean notificationSeenStatus;

    private LocalDateTime creatAt;

    private LocalDateTime updateAt;

    private Boolean notificationStatus;

    private LocalDateTime noticationSeenAt;

    public Notifications() {
    }

    public Notifications(String notificationUrl, Users notificationSender, Users notificationReceiver, String description, Boolean notificationSeenStatus, LocalDateTime creatAt, Boolean notificationStatus) {
        this.notificationUrl = notificationUrl;
        this.notificationSender = notificationSender;
        this.notificationReceiver = notificationReceiver;
        this.description = description;
        this.notificationSeenStatus = notificationSeenStatus;
        this.creatAt = creatAt;
        this.notificationStatus = notificationStatus;
    }

    public Notifications(String notificationUrl, Users notificationSender, Users notificationReceiver, String description, Boolean notificationSeenStatus, LocalDateTime creatAt, LocalDateTime updateAt, Boolean notificationStatus, LocalDateTime noticationSeenAt) {
        this.notificationUrl = notificationUrl;
        this.notificationSender = notificationSender;
        this.notificationReceiver = notificationReceiver;
        this.description = description;
        this.notificationSeenStatus = notificationSeenStatus;
        this.creatAt = creatAt;
        this.updateAt = updateAt;
        this.notificationStatus = notificationStatus;
        this.noticationSeenAt = noticationSeenAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public void setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    public Users getNotificationSender() {
        return notificationSender;
    }

    public void setNotificationSender(Users notificationSender) {
        this.notificationSender = notificationSender;
    }

    public Users getNotificationReceiver() {
        return notificationReceiver;
    }

    public void setNotificationReceiver(Users notificationReceiver) {
        this.notificationReceiver = notificationReceiver;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(LocalDateTime creatAt) {
        this.creatAt = creatAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Boolean getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Boolean notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public LocalDateTime getNoticationSeenAt() {
        return noticationSeenAt;
    }

    public void setNoticationSeenAt(LocalDateTime noticationSeenAt) {
        this.noticationSeenAt = noticationSeenAt;
    }

    public Boolean getNotificationSeenStatus() {
        return notificationSeenStatus;
    }

    public void setNotificationSeenStatus(Boolean notificationSeenStatus) {
        this.notificationSeenStatus = notificationSeenStatus;
    }
}
