package com.happyride.eservice.entity.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    @Column(length = 3000)
    private String description;

    private double price;

    @Transient
    @Column(nullable = true)
    private MultipartFile[] photos;

    @Transient
    @Column(nullable = true)
    private MultipartFile[] videos;

    @ElementCollection
    @JsonIgnore
    private List<String> photosLink;

    @ElementCollection
    @JsonIgnore
    private List<String> videosLink;

    private String address;

    private String latitude;

    private String longitude;

    private String contactNumber;

    @OneToOne(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private PostView postView;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comments> commentsList = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<FavoritePost> favoritePostList = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LikePost> likePostList = new ArrayList<>();

    @ManyToOne
    private Users postedBy;

    @OneToOne(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private PostApproved approved;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    @JsonIgnore
    private List<PostReport> postReportList = new ArrayList<>();

    @ManyToOne
    private SubCategory subCategory;

    @OneToOne(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private Generic generic;

    @OneToOne(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private Hire hire;

    @OneToOne(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private JobSeeker jobSeeker;

    @OneToOne(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private Laptop laptop;

    @OneToOne(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private PetAnimal petAnimal;

    @OneToOne(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private Properties properties;

    @OneToOne(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private SmartPhones smartPhones;

    @OneToOne(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private Vehicles vehicles;

    @OneToOne(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private Villa villa;

    private LocalDateTime postCreateTime;

    private LocalDateTime postUpdateTime;

    private LocalDateTime postDisableTime;

    private boolean promotedAds;

    private long priorityLevel;

    @OneToOne(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private PostStatus postStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public MultipartFile[] getPhotos() {
        return photos;
    }

    public void setPhotos(MultipartFile[] photos) {
        this.photos = photos;
    }

    public MultipartFile[] getVideos() {
        return videos;
    }

    public void setVideos(MultipartFile[] videos) {
        this.videos = videos;
    }

    public List<String> getPhotosLink() {
        return photosLink;
    }

    public void setPhotosLink(List<String> photosLink) {
        this.photosLink = photosLink;
    }

    public List<String> getVideosLink() {
        return videosLink;
    }

    public void setVideosLink(List<String> videosLink) {
        this.videosLink = videosLink;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public PostView getPostView() {
        return postView;
    }

    public void setPostView(PostView postView) {
        this.postView = postView;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public List<FavoritePost> getFavoritePostList() {
        return favoritePostList;
    }

    public void setFavoritePostList(List<FavoritePost> favoritePostList) {
        this.favoritePostList = favoritePostList;
    }

    public List<LikePost> getLikePostList() {
        return likePostList;
    }

    public void setLikePostList(List<LikePost> likePostList) {
        this.likePostList = likePostList;
    }

    public Users getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(Users postedBy) {
        this.postedBy = postedBy;
    }

    public PostApproved getApproved() {
        return approved;
    }

    public void setApproved(PostApproved approved) {
        this.approved = approved;
    }

    public List<PostReport> getPostReportList() {
        return postReportList;
    }

    public void setPostReportList(List<PostReport> postReportList) {
        this.postReportList = postReportList;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Generic getGeneric() {
        return generic;
    }

    public void setGeneric(Generic generic) {
        this.generic = generic;
    }

    public Hire getHire() {
        return hire;
    }

    public void setHire(Hire hire) {
        this.hire = hire;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public PetAnimal getPetAnimal() {
        return petAnimal;
    }

    public void setPetAnimal(PetAnimal petAnimal) {
        this.petAnimal = petAnimal;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public SmartPhones getSmartPhones() {
        return smartPhones;
    }

    public void setSmartPhones(SmartPhones smartPhones) {
        this.smartPhones = smartPhones;
    }

    public Vehicles getVehicles() {
        return vehicles;
    }

    public void setVehicles(Vehicles vehicles) {
        this.vehicles = vehicles;
    }

    public Villa getVilla() {
        return villa;
    }

    public void setVilla(Villa villa) {
        this.villa = villa;
    }

    public LocalDateTime getPostCreateTime() {
        return postCreateTime;
    }

    public void setPostCreateTime(LocalDateTime postCreateTime) {
        this.postCreateTime = postCreateTime;
    }

    public LocalDateTime getPostUpdateTime() {
        return postUpdateTime;
    }

    public void setPostUpdateTime(LocalDateTime postUpdateTime) {
        this.postUpdateTime = postUpdateTime;
    }

    public LocalDateTime getPostDisableTime() {
        return postDisableTime;
    }

    public void setPostDisableTime(LocalDateTime postDisableTime) {
        this.postDisableTime = postDisableTime;
    }

    public boolean isPromotedAds() {
        return promotedAds;
    }

    public void setPromotedAds(boolean promotedAds) {
        this.promotedAds = promotedAds;
    }

    public long getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(long priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }
}