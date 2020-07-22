package com.happyride.eservice.repository;


import com.happyride.eservice.entity.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {


}
