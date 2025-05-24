package com.ravi.semester.repository;

import com.ravi.semester.model.LabChangeRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabChangeRequestRepo extends JpaRepository<LabChangeRequest, Long> {
    List<LabChangeRequest> findByCurrentLabId(String currentLabId);
    List<LabChangeRequest> findByDesiredLabId(String desiredLabId);
    List<LabChangeRequest> findByStudentId(String studentId);
    List<LabChangeRequest> findByCurrentLabFacultyIdOrDesiredLabFacultyId(String currentLabFacultyId, String desiredLabFacultyId);
    @Transactional
    @Modifying
    @Query("DELETE FROM LabChangeRequest l WHERE l.currentLab.id = :labId OR l.desiredLab.id = :labId")
    void deleteByCurrentLabIdOrDesiredLabId(@Param("labId") String labId);
}
