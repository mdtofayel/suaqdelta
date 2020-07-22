package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.Profile;
import com.happyride.eservice.entity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUsers(Users users);
}
