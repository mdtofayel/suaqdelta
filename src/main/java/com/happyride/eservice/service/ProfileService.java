package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.Profile;
import com.happyride.eservice.entity.model.Users;
import com.happyride.eservice.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public Optional<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    public void saveProfile(Profile profile) {
        profileRepository.save(profile);
    }

    public Optional<Profile> findByUsers(Users users) {
        return profileRepository.findByUsers(users);
    }
}
