package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.Feedback;
import com.happyride.eservice.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;


    public Feedback saveFeedback(Feedback feedback){

        return  feedbackRepository.save(feedback);
    }
}
