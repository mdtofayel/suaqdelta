package com.happyride.eservice.entity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private  String feedbackText;

    @ManyToOne
    private  Users feedbackBy;

    private LocalDateTime feedBackCreatAt;

    private  LocalDateTime FeedBackUpdateAt;

    private  Boolean disableFeedbackStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public Users getFeedbackBy() {
        return feedbackBy;
    }

    public void setFeedbackBy(Users feedbackBy) {
        this.feedbackBy = feedbackBy;
    }

    public LocalDateTime getFeedBackCreatAt() {
        return feedBackCreatAt;
    }

    public void setFeedBackCreatAt(LocalDateTime feedBackCreatAt) {
        this.feedBackCreatAt = feedBackCreatAt;
    }

    public LocalDateTime getFeedBackUpdateAt() {
        return FeedBackUpdateAt;
    }

    public void setFeedBackUpdateAt(LocalDateTime feedBackUpdateAt) {
        FeedBackUpdateAt = feedBackUpdateAt;
    }

    public Boolean getDisableFeedbackStatus() {
        return disableFeedbackStatus;
    }

    public void setDisableFeedbackStatus(Boolean disableFeedbackStatus) {
        this.disableFeedbackStatus = disableFeedbackStatus;
    }
}
