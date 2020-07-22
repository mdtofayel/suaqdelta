package com.happyride.eservice.entity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FavoritePost {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Post post;

    private Boolean favorite;

    @ManyToOne
    private Users favoriteBy;

    private LocalDateTime localDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Users getFavoriteBy() {
        return favoriteBy;
    }

    public void setFavoriteBy(Users favoriteBy) {
        this.favoriteBy = favoriteBy;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
