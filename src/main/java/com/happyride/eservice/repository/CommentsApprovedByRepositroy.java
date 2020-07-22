package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.CommentApprove;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsApprovedByRepositroy extends JpaRepository<CommentApprove, Long> {
}
