package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.FavoritePost;
import com.happyride.eservice.entity.model.Post;
import com.happyride.eservice.entity.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface FavoritePostRepository extends JpaRepository<FavoritePost, Long> {
    List<FavoritePost> findByPost(Post post);

    List<FavoritePost> findByFavoriteBy(Users users);

    Optional<FavoritePost> findByFavoriteByAndPost(Users users, Post post);

    Page<FavoritePost> findPostByFavoriteBy(Users users, Pageable pageable);
}
