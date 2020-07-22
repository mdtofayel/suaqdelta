package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.PostType;
import com.happyride.eservice.repository.PostTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class PostTypeService {

    private final PostTypeRepository postTypeRepository;

    @Autowired
    public PostTypeService(PostTypeRepository postTypeRepository) {
        this.postTypeRepository = postTypeRepository;
    }

    public void savePostType(PostType postType){
        postTypeRepository.save(postType);
    }

    public List<PostType> findAll(){
        return postTypeRepository.findAll();
    }

    public Optional<PostType> findPostTypeByName( String  name){
        return postTypeRepository.findPostTypeByName(name);
    }

    public Optional<PostType> getPostTypebyId(Long id){
        return  postTypeRepository.findPostTypeById(id);
    }
}
