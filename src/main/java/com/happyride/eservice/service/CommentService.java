package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.Comments;
import com.happyride.eservice.entity.model.Post;
import com.happyride.eservice.entity.model.Users;
import com.happyride.eservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.xml.stream.events.Comment;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comments> findByPost(Post post) {
        return commentRepository.findByPost(post);
    }
    public List<Comments> findByPostDesc(Post post) {
        return commentRepository.findByPostOrderByLocalDateTimeDesc(post);
    }

    public List<Comments> findByCommentedBy(Users users) {
        return commentRepository.findByCommentedBy(users);
    }

    public void saveComments(Comments comments) {
        commentRepository.save(comments);
    }

    public void deleteComments(Comments comments) {
        commentRepository.delete(comments);
    }

    public Optional<Comments> findComment(Long id){
        return commentRepository.findById(id);
    }

    public Integer getNumberComment(Post post){

        return commentRepository.countByPost(post);
    }
}
