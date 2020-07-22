package com.happyride.eservice;

import com.happyride.eservice.entity.model.*;
import com.happyride.eservice.service.PostModelService;
import com.happyride.eservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;


@SpringBootApplication/*(exclude = {ErrorMvcAutoConfiguration.class})*/
public class EserviceApplication {
    private final PostModelService postModelService;

    private final RoleService roleService;

    @Autowired
    public EserviceApplication(PostModelService postModelService, RoleService roleService) {
        this.postModelService = postModelService;
        this.roleService = roleService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EserviceApplication.class, args);
    }

    @Bean
    public void makeDirectory() {
          String path = "/tmp/Eservice_Files";
        // String path = "/Temp/Eservice_Files";

        File pathDir = new File(path);
        if (!pathDir.exists()) {
            pathDir.mkdir();
        }
        File imagesDir = new File(path + "/images");
        if (!imagesDir.exists()) {
            imagesDir.mkdir();
        }
        File videoDir = new File(path + "/videos");
        if (!videoDir.exists()) {
            videoDir.mkdir();
        }
        File sliderDir = new File(path + "/images/sliderImage");
        if (!sliderDir.exists()) {
            sliderDir.mkdir();
        }
        File categoryImageDir = new File(path + "/images/category");
        if (!categoryImageDir.exists()) {
            categoryImageDir.mkdir();
        }
        File postImageDir = new File(path + "/images/post");
        if (!postImageDir.exists()) {
            postImageDir.mkdir();
        }
        File postVideoDir = new File(path + "/videos/post");
        if (!postVideoDir.exists()) {
            postVideoDir.mkdir();
        }

        File profileImageDir = new File(path + "/images/profileImage");
        if (!profileImageDir.exists()) {
            profileImageDir.mkdir();
        }

        File manufacturerImage = new File(path + "/images/manufacturer");
        if (!manufacturerImage.exists()) {
            manufacturerImage.mkdir();
        }
    }

    @Bean
    public void modelInsert() {
        try {
            postModelService.savePostModel(new PostModel("Generic", new Generic().getClass()));
            postModelService.savePostModel(new PostModel("Hire", new Hire().getClass()));
            postModelService.savePostModel(new PostModel("JobSeeker", new JobSeeker().getClass()));
            postModelService.savePostModel(new PostModel("Laptop", new Laptop().getClass()));
            postModelService.savePostModel(new PostModel("PetAnimal", new PetAnimal().getClass()));
            postModelService.savePostModel(new PostModel("Properties", new Properties().getClass()));
            postModelService.savePostModel(new PostModel("SmartPhones", new SmartPhones().getClass()));
            postModelService.savePostModel(new PostModel("Vehicles", new Vehicles().getClass()));
            postModelService.savePostModel(new PostModel("Villa", new Villa().getClass()));
        } catch (Exception e) {
        }
    }

    @Bean
    public void roleInsert() {
        try {
            roleService.save(new Role("ADMIN"));
            roleService.save(new Role("MANAGER"));
            roleService.save(new Role("USER"));
        } catch (Exception e) {
        }
    }

}

