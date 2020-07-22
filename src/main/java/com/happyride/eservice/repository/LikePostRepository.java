package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.LikePost;
import com.happyride.eservice.entity.model.Post;
import com.happyride.eservice.entity.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    List<LikePost> findByPost(Post post);

    List<LikePost> findByLikedBy(Users users);

    Optional<LikePost> findByLikedByAndPost(Users users, Post post);

    int countAllByPost(Post post);

    Page<LikePost> findByLikedBy(Users users, Pageable pageable);

    Integer countByPost(Post post);
}
