package com.happyride.eservice.controller;

import com.happyride.eservice.entity.model.Feedback;
import com.happyride.eservice.service.FavoritePostService;
import com.happyride.eservice.service.FeedbackService;
import com.happyride.eservice.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller()
@RequestMapping("/feedback")
public class FeedbackController {
    private FeedbackService feedbackService;
    private UsersService usersService;

    public FeedbackController(FeedbackService feedbackService, UsersService usersService) {
        this.feedbackService = feedbackService;
        this.usersService = usersService;
    }




    @GetMapping("/fpReturn")
    public ModelAndView profileFind() {
        ModelAndView modelAndView = new ModelAndView("feedback");
        modelAndView.addObject("feedback", new Feedback());
        return modelAndView;
    }
    @PostMapping("/save")
    public @ResponseBody String  saveFeedback(@RequestParam("feedbackBody") String feedbackBody){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Feedback feedback = new Feedback();
        feedback.setFeedbackText(feedbackBody);
        feedback.setFeedbackBy(usersService.findByEmail(auth.getName()).get());
        feedback.setDisableFeedbackStatus(false);
        feedback.setFeedBackCreatAt(LocalDateTime.now());
        feedbackService.saveFeedback(feedback);
        return "success";
    }

}
