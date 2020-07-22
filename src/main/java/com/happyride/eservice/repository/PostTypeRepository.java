package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.Post;
import com.happyride.eservice.entity.model.PostType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface PostTypeRepository extends JpaRepository<PostType, Long> {

    Optional<PostType> findPostTypeByName(String Name);

    Optional<PostType> findPostTypeById(Long id);
}

