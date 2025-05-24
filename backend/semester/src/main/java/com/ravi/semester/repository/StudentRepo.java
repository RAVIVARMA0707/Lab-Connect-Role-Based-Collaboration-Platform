package com.ravi.semester.repository;

import com.ravi.semester.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student,String> {
    List<Student> findByYear(Integer year);

    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);
}

