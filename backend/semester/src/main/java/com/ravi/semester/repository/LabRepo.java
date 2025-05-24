package com.ravi.semester.repository;

import com.ravi.semester.model.Lab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabRepo extends JpaRepository<Lab,String> {
}
