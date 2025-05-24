package com.ravi.semester.repository;

import com.ravi.semester.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacultyRepo extends JpaRepository<Faculty,String> {
    Optional<Faculty> findByEmail(String email);

    boolean existsByEmail(String email);
}
