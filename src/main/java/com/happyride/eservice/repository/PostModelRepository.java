package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface PostModelRepository extends JpaRepository<PostModel, Long> {
    Optional<PostModel> findByModelName(String name);
}
