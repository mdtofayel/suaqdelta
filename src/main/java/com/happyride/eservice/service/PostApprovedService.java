package com.happyride.eservice.service;


import com.happyride.eservice.repository.PostApprovedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostApprovedService {

    @Autowired
    private PostApprovedRepository postApprovedRepository;

}
