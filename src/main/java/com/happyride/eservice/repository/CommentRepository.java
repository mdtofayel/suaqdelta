package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.Comments;
import com.happyride.eservice.entity.model.Post;
import com.happyride.eservice.entity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByPost(Post post);

    List<Comments> findByPostOrderByLocalDateTimeDesc(Post post);

    List<Comments> findByCommentedBy(Users users);

    Optional<Comments> findById(Long id);

    Integer countByPost(Post post);
}
