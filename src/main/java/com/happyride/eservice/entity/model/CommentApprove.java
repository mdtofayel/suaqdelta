package com.happyride.eservice.entity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CommentApprove {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    private Users approvedBy;

    @OneToOne
    private Comments comments;

    private LocalDateTime approvedAt;

    private LocalDateTime approvedUpdateAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Users getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Users approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public LocalDateTime getApprovedUpdateAt() {
        return approvedUpdateAt;
    }

    public void setApprovedUpdateAt(LocalDateTime approvedUpdateAt) {
        this.approvedUpdateAt = approvedUpdateAt;
    }
}
