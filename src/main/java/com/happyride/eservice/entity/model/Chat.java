package com.happyride.eservice.entity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String message;

    @ManyToOne
    private Users sendTo;

    @ManyToOne
    private Users sendFrom;

    private boolean send;

    private boolean seen;


    private LocalDateTime time;

    public Chat() {
    }

    public Chat(String message, Users sendTo, Users sendFrom, boolean send, boolean seen, LocalDateTime time) {
        this.message = message;
        this.sendTo = sendTo;
        this.sendFrom = sendFrom;
        this.send = send;
        this.seen = seen;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Users getSendTo() {
        return sendTo;
    }

    public void setSendTo(Users sendTo) {
        this.sendTo = sendTo;
    }

    public Users getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(Users sendFrom) {
        this.sendFrom = sendFrom;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
