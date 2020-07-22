package com.happyride.eservice.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "*Please provide a valid Email or Phone Number")
    @Length(min = 8, max = 50, message = "*Invalid email or Phone number")
    private String email;

    @Length(min = 6, message = "*Your password must have at least 6 characters")
    @NotEmpty(message = "*Please provide your password")
    @JsonIgnore
    private String password;

    @Transient
    @Length(min = 6, message = "*Your password must have at least 6 characters")
    @NotEmpty(message = "*Please provide your password")
    @JsonIgnore
    private String confirmPassword;

    private int active;

    @OneToMany(mappedBy = "approvedBy", orphanRemoval = true)
    @JsonIgnore
    private List<CommentApprove> commentApproveArrayList = new ArrayList<>();

    @OneToMany(mappedBy = "approvedBy", orphanRemoval = true)
    @JsonIgnore
    private List<PostApproved> postApprovedArrayList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToOne(mappedBy = "users", orphanRemoval = true, cascade = CascadeType.ALL)
    private Profile profile;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Verification verification;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private PasswordResetToken passwordResetToken;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private EmailVerificationToken emailVerificationToken;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private OtpSendCounter otpSendCounter;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private PhoneNumberVerificationToken phoneNumberVerificationToken;

    @OneToMany(mappedBy = "sendTo", orphanRemoval = true)
    @JsonIgnore
    private List<Chat> sentToChatList;

    @OneToMany(mappedBy = "sendFrom", orphanRemoval = true)
    @JsonIgnore
    private List<Chat> sendFromChatList;

    @OneToMany(mappedBy = "commentedBy", orphanRemoval = true)
    @JsonIgnore
    private List<Comments> commentsList;

    @OneToMany(mappedBy = "likedBy", orphanRemoval = true)
    @JsonIgnore
    private List<LikePost> likePostList;

    @OneToMany(mappedBy = "favoriteBy", orphanRemoval = true)
    @JsonIgnore
    private List<FavoritePost> favoritePostList;

    @OneToMany(mappedBy = "postedBy", orphanRemoval = true)
    @JsonIgnore
    private List<Post> postList;

    @OneToMany(mappedBy = "notificationSender", orphanRemoval = true)
    @JsonIgnore
    private List<Notifications> notificationsSenderList;

    @OneToMany(mappedBy = "notificationReceiver", orphanRemoval = true)
    @JsonIgnore
    private List<Notifications> notificationReceiverList;

    @OneToMany(mappedBy = "reportBy", orphanRemoval = true)
    @JsonIgnore
    private List<PostReport> reportedByList;

    @OneToMany(mappedBy = "feedbackBy", orphanRemoval = true)
    @JsonIgnore
    private List<Feedback> feedbackList;

    @OneToMany(mappedBy = "loggedUsers", orphanRemoval = true)
    @JsonIgnore
    private List<UsersLogInfo> usersLogInfoList;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime disabledDate;

    public Users() {
    }

    public Users(Users users) {
        this.id = users.getId();
        this.email = users.getEmail();
        this.password = users.getPassword();
        this.confirmPassword = users.getConfirmPassword();
        this.active = users.getActive();
        this.commentApproveArrayList = users.getCommentApproveArrayList();
        this.postApprovedArrayList = users.getPostApprovedArrayList();
        this.reportedByList = users.getReportedByList();
        this.roles = users.getRoles();
        this.createdDate = users.getCreatedDate();
        this.updatedDate = users.getUpdatedDate();
        this.disabledDate = users.getDisabledDate();
        this.profile = users.getProfile();
        this.verification = users.getVerification();
        this.passwordResetToken = users.getPasswordResetToken();
        this.emailVerificationToken = users.getEmailVerificationToken();
        this.otpSendCounter = users.getOtpSendCounter();
        this.phoneNumberVerificationToken = users.getPhoneNumberVerificationToken();
        this.sentToChatList = users.getSentToChatList();
        this.sendFromChatList = users.getSendFromChatList();
        this.commentsList = users.getCommentsList();
        this.likePostList = users.getLikePostList();
        this.favoritePostList = users.getFavoritePostList();
        this.postList = users.getPostList();
        this.notificationReceiverList = users.getNotificationReceiverList();
        this.notificationsSenderList = users.getNotificationsSenderList();
        this.feedbackList = users.feedbackList;
        this.usersLogInfoList = users.usersLogInfoList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public List<CommentApprove> getCommentApproveArrayList() {
        return commentApproveArrayList;
    }

    public void setCommentApproveArrayList(List<CommentApprove> commentApproveArrayList) {
        this.commentApproveArrayList = commentApproveArrayList;
    }

    public List<PostApproved> getPostApprovedArrayList() {
        return postApprovedArrayList;
    }

    public void setPostApprovedArrayList(List<PostApproved> postApprovedArrayList) {
        this.postApprovedArrayList = postApprovedArrayList;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Verification getVerification() {
        return verification;
    }

    public void setVerification(Verification verification) {
        this.verification = verification;
    }

    public PasswordResetToken getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(PasswordResetToken passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public EmailVerificationToken getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(EmailVerificationToken emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public OtpSendCounter getOtpSendCounter() {
        return otpSendCounter;
    }

    public void setOtpSendCounter(OtpSendCounter otpSendCounter) {
        this.otpSendCounter = otpSendCounter;
    }

    public PhoneNumberVerificationToken getPhoneNumberVerificationToken() {
        return phoneNumberVerificationToken;
    }

    public void setPhoneNumberVerificationToken(PhoneNumberVerificationToken phoneNumberVerificationToken) {
        this.phoneNumberVerificationToken = phoneNumberVerificationToken;
    }

    public List<Chat> getSentToChatList() {
        return sentToChatList;
    }

    public void setSentToChatList(List<Chat> sentToChatList) {
        this.sentToChatList = sentToChatList;
    }

    public List<Chat> getSendFromChatList() {
        return sendFromChatList;
    }

    public void setSendFromChatList(List<Chat> sendFromChatList) {
        this.sendFromChatList = sendFromChatList;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public List<LikePost> getLikePostList() {
        return likePostList;
    }

    public void setLikePostList(List<LikePost> likePostList) {
        this.likePostList = likePostList;
    }

    public List<FavoritePost> getFavoritePostList() {
        return favoritePostList;
    }

    public void setFavoritePostList(List<FavoritePost> favoritePostList) {
        this.favoritePostList = favoritePostList;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public List<Notifications> getNotificationsSenderList() {
        return notificationsSenderList;
    }

    public void setNotificationsSenderList(List<Notifications> notificationsSenderList) {
        this.notificationsSenderList = notificationsSenderList;
    }

    public List<Notifications> getNotificationReceiverList() {
        return notificationReceiverList;
    }

    public void setNotificationReceiverList(List<Notifications> notificationReceiverList) {
        this.notificationReceiverList = notificationReceiverList;
    }

    public List<PostReport> getReportedByList() {
        return reportedByList;
    }

    public void setReportedByList(List<PostReport> reportedByList) {
        this.reportedByList = reportedByList;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public List<UsersLogInfo> getUsersLogInfoList() {
        return usersLogInfoList;
    }

    public void setUsersLogInfoList(List<UsersLogInfo> usersLogInfoList) {
        this.usersLogInfoList = usersLogInfoList;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LocalDateTime getDisabledDate() {
        return disabledDate;
    }

    public void setDisabledDate(LocalDateTime disabledDate) {
        this.disabledDate = disabledDate;
    }
}