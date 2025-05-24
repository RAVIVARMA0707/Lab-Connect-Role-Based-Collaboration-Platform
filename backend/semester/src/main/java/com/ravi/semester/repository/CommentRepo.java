package com.ravi.semester.repository;

import com.ravi.semester.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Long> {


    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);
}
