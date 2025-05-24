package com.ravi.semester.repository;

import com.ravi.semester.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Long> {
    List<Category> findByLabId(String labId);
}
