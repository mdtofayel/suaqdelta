package com.happyride.eservice.entity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PostApproved {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String approved;

    @ManyToOne
    private Users approvedBy;

    @OneToOne
    private Post post;

    private LocalDateTime approvedAt;

    private LocalDateTime approvedUpdateAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public Users getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Users approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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
