package com.happyride.eservice.service;


import com.happyride.eservice.entity.model.FavoritePost;
import com.happyride.eservice.entity.model.Post;
import com.happyride.eservice.entity.model.Users;
import com.happyride.eservice.repository.FavoritePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritePostService {
    @Autowired
    private FavoritePostRepository favoritePostRepository;

    public List<FavoritePost> findByPost(Post post) {
        return favoritePostRepository.findByPost(post);
    }

    public List<FavoritePost> findByFavoriteBy(Users users) {
        return favoritePostRepository.findByFavoriteBy(users);
    }

    public void saveFavorite(FavoritePost favoritePost) {
        favoritePostRepository.save(favoritePost);
    }

    private void deleteFavorite(FavoritePost favoritePost) {
        favoritePostRepository.delete(favoritePost);
    }

    public Optional<FavoritePost> checkFavorite(Users users, Post post){

        return favoritePostRepository.findByFavoriteByAndPost(users,post);
    }

    public Page<FavoritePost> findPostByFavouriteItem(Users users, PageRequest pageRequest){
        return  favoritePostRepository.findPostByFavoriteBy(users, pageRequest);
    }
}
