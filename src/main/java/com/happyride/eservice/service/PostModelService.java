package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.PostModel;
import com.happyride.eservice.repository.PostModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class PostModelService {

    private final PostModelRepository postModelRepository;

    @Autowired
    public PostModelService(PostModelRepository postModelRepository) {
        this.postModelRepository = postModelRepository;
    }

    public void savePostModel(PostModel postModel) {
        postModelRepository.save(postModel);
    }

    public List<PostModel> findAll() {
        return postModelRepository.findAll();
    }

    public Optional<PostModel> findById(Long id) {
        return postModelRepository.findById(id);
    }

    public Optional<PostModel> findByModelName(String name) {
        return postModelRepository.findByModelName(name);
    }
}
