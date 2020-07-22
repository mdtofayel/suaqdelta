package com.happyride.eservice.entity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LikePost {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private boolean likes;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Users likedBy;

    private LocalDateTime localDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isLikes() {
        return likes;
    }

    public void setLikes(boolean likes) {
        this.likes = likes;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Users getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Users likedBy) {
        this.likedBy = likedBy;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }


}
