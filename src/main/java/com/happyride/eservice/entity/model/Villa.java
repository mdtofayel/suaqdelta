package com.happyride.eservice.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Villa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String furnished;

    private String numberOfRoom;

    private String numberOfBathroom;

    private String kitchenType;

    private String parking;

    private String deposit;

    @OneToOne
    @JsonIgnore
    private Post post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.lang.String getFurnished() {
        return furnished;
    }

    public void setFurnished(java.lang.String furnished) {
        this.furnished = furnished;
    }

    public String getNumberOfRoom() {
        return numberOfRoom;
    }

    public void setNumberOfRoom(String numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }

    public String getNumberOfBathroom() {
        return numberOfBathroom;
    }

    public void setNumberOfBathroom(String numberOfBathroom) {
        this.numberOfBathroom = numberOfBathroom;
    }

    public java.lang.String getKitchenType() {
        return kitchenType;
    }

    public void setKitchenType(java.lang.String kitchenType) {
        this.kitchenType = kitchenType;
    }

    public java.lang.String getParking() {
        return parking;
    }

    public void setParking(java.lang.String parking) {
        this.parking = parking;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public java.lang.String getDeposit() {
        return deposit;
    }

    public void setDeposit(java.lang.String deposit) {
        this.deposit = deposit;
    }
}