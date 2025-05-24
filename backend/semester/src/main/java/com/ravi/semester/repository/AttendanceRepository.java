package com.ravi.semester.repository;

import com.ravi.semester.model.Attendance;
import com.ravi.semester.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentAndDate(Student student, LocalDate date);
    List<Attendance> findByDateAndStudent_EnrolledLab_Id(LocalDate date, String labId);

    Optional<Attendance> findByStudentIdAndDate(String studentId, LocalDate date);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.enrolledLab.id = :labId AND a.date = :date AND a.forenoonStatus = true AND a.afternoonStatus = true")
    Long countPresentStudentsByLabIdAndDate(@Param("labId") String labId, @Param("date") LocalDate date);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.date BETWEEN :startDate AND :endDate AND a.forenoonStatus = true AND a.afternoonStatus = true")
    Long countPresentDays(@Param("studentId") String studentId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(DISTINCT a.date) FROM Attendance a WHERE a.student.id = :studentId AND a.date BETWEEN :startDate AND :endDate")
    Long countTotalDays(@Param("studentId") String studentId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    void deleteByStudentAndDate(Student student, LocalDate date);
}
