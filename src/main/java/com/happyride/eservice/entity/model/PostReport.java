package com.happyride.eservice.entity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PostReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    private Users reportBy;

    @ManyToOne
    private Post post;

    private String description;

    private LocalDateTime creatAt;

    private LocalDateTime updateAt;

    private Boolean reportStatus;

    private LocalDateTime reportSeenAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Users getReportBy() {
        return reportBy;
    }

    public void setReportBy(Users reportBy) {
        this.reportBy = reportBy;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    public Boolean getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Boolean reportStatus) {
        this.reportStatus = reportStatus;
    }

    public LocalDateTime getReportSeenAt() {
        return reportSeenAt;
    }

    public void setReportSeenAt(LocalDateTime reportSeenAt) {
        this.reportSeenAt = reportSeenAt;
    }
}
