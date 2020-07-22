package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.Post;
import com.happyride.eservice.entity.model.PostView;
import com.happyride.eservice.repository.PostViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostViewService {
    @Autowired
    private PostViewRepository postViewRepository;

    public int countAllByPost(Post post) {
        return postViewRepository.countAllByPost(post);
    }

    public void savePostView(PostView postView) {
        postViewRepository.save(postView);
    }

    private void deletePostView(PostView postView) {
        postViewRepository.delete(postView);
    }

    public Optional<PostView> findViewCountByPost(Post post) {
        return postViewRepository.findByPost(post);
    }

    public Integer getNumberView(Post post) {
        return postViewRepository.countByPost(post);
    }

    public PostView getPostViewByPost(Post post) {
        return postViewRepository.getPostViewByPost(post);
    }
}
