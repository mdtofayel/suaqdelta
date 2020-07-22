package com.happyride.eservice.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String imageLink;

    @Transient
    private MultipartFile manufacturerImage;

    @ManyToMany(mappedBy = "manufacturerList", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SubCategory> subCategory;

    @OneToMany(mappedBy = "manufacturer", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ManufacturerModel> manufacturerModelList;

    private LocalDateTime creatAt;

    private LocalDateTime updateAt;

    private LocalDateTime deleteAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubCategory> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<SubCategory> subCategory) {
        this.subCategory = subCategory;
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

    public LocalDateTime getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(LocalDateTime deleteAt) {
        this.deleteAt = deleteAt;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public MultipartFile getManufacturerImage() {
        return manufacturerImage;
    }

    public void setManufacturerImage(MultipartFile manufacturerImage) {
        this.manufacturerImage = manufacturerImage;
    }

    public List<ManufacturerModel> getManufacturerModelList() {
        return manufacturerModelList;
    }

    public void setManufacturerModelList(List<ManufacturerModel> manufacturerModelList) {
        this.manufacturerModelList = manufacturerModelList;
    }
}
