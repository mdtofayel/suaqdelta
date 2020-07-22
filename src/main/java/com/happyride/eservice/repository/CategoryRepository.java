package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.Category;
import com.happyride.eservice.entity.model.Post;
import com.happyride.eservice.entity.model.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String categoryName);

    Optional<Category> findById(Long id);

    List<Category> findByPostType(PostType postType);

    List<Category> findListByName(String categoryName);

    Optional<Category> findByNameIgnoreCaseAndPostType(String name, PostType postType);




}
