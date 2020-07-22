package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.LikePost;
import com.happyride.eservice.entity.model.Post;
import com.happyride.eservice.entity.model.Users;
import com.happyride.eservice.repository.LikePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikePostService {
    @Autowired
    private LikePostRepository likePostRepository;

    public List<LikePost> findByPost(Post post) {
        return likePostRepository.findByPost(post);
    }

    public List<LikePost> findByLikedBy(Users users) {
        return likePostRepository.findByLikedBy(users);
    }

    public int countAllByPost(Post post) {
        return likePostRepository.countAllByPost(post);
    }

    public void saveLike(LikePost likePost) {
        likePostRepository.save(likePost);
    }

    private void deleteLike(LikePost likePost) {
        likePostRepository.delete(likePost);
    }

    public Optional<LikePost> checkLike(Users users, Post post){
        return  likePostRepository.findByLikedByAndPost(users,post);
    }


    public Page<LikePost> findPostByLikeItem(Users users, PageRequest pageRequest){
        return  likePostRepository.findByLikedBy(users, pageRequest);
    }

    public Optional<LikePost> findLikePostByIdAndPostId(Users users, Post post){

        return  likePostRepository.findByLikedByAndPost(users,post);
    }

    public Integer getNumberLike(Post post){

        return likePostRepository.countByPost(post);
    }
}
