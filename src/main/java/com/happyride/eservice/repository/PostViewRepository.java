package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.Post;
import com.happyride.eservice.entity.model.PostView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostViewRepository extends JpaRepository<PostView, Long> {
    int countAllByPost(Post post);

    Optional<PostView> findByPost(Post post);

    Integer countByPost(Post post);

    PostView getPostViewByPost(Post post);
}
