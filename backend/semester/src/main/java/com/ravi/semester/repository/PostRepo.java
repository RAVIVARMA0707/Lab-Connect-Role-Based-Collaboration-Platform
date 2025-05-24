package com.ravi.semester.repository;

import com.ravi.semester.model.Category;
import com.ravi.semester.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Long> {

    List<Post> findByCategoryOrderByCreatedAtDesc(Category category);

    List<Post> findByCategory(Category category);
}
