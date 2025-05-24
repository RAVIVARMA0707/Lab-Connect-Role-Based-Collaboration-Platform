package com.ravi.semester.service;

import com.ravi.semester.model.Attendance;
import com.ravi.semester.model.Event;
import com.ravi.semester.model.Faculty;
import com.ravi.semester.model.Student;
import com.ravi.semester.repository.AttendanceRepository;
import com.ravi.semester.repository.EventRepository;
import com.ravi.semester.repository.FacultyRepo;
import com.ravi.semester.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private FacultyRepo facultyRepository;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private EventRepository eventRepo;


    public List<Attendance> getEligibleStudents(LocalDate date, String facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found"));

        return attendanceRepository.findByDateAndStudent_EnrolledLab_Id(date, faculty.getLab().getId());

    }

    public void updateAttendance(Attendance request) {
        Attendance attendance = attendanceRepository.findByStudentIdAndDate(request.getStudent().getId(), request.getDate())
                .orElseThrow(() -> new IllegalArgumentException("Attendance record not found"));

        attendance.setForenoonStatus(request.isForenoonStatus());
        attendance.setAfternoonStatus(request.isAfternoonStatus());

        attendanceRepository.save(attendance);
    }

    public Attendance getAttendanceStatus(String studentId, LocalDate date) {
        Attendance attendance = attendanceRepository.findByStudentIdAndDate(studentId, date)
                .orElseThrow(() -> new IllegalArgumentException("Attendance record not found"));

//        return (attendance.isForenoonStatus() && attendance.isAfternoonStatus()) ? "PRESENT" : "ABSENT";
        return attendance;
    }

    public Long getStudentCountForLabOnDate(String labId, LocalDate date) {
        return attendanceRepository.countPresentStudentsByLabIdAndDate(labId, date);
    }

    public double calculateAttendancePercentage(String studentId, LocalDate currentDate) {
        // Get student's year from Student entity
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        int studentYear = student.getYear();

        // Check for a live event for the student's year
        Optional<Event> liveEventOpt = eventRepo.findLiveEventOnDate(studentYear, currentDate);
        Event event;

        if (liveEventOpt.isPresent()) {
            // If event is live, calculate attendance till current date
            event = liveEventOpt.get();
            return calculatePercentageForEvent(studentId, event.getStartDate(), currentDate);
        } else {
            // Get the last completed event for the student's year
            List<Event> completedEvents = eventRepo.findCompletedEventsTillDate(studentYear, currentDate);
            if (completedEvents.isEmpty()) {
                throw new RuntimeException("No completed events found for this year.");
            }
            event = completedEvents.get(0);
            return calculatePercentageForEvent(studentId, event.getStartDate(), event.getEndDate());
        }
    }

    private double calculatePercentageForEvent(String studentId, LocalDate startDate, LocalDate endDate) {
        Long totalDays = attendanceRepository.countTotalDays(studentId, startDate, endDate);
        Long presentDays = attendanceRepository.countPresentDays(studentId, startDate, endDate);

        if (totalDays == 0) {
            return 0.0;
        }

        return (presentDays.doubleValue() / totalDays.doubleValue()) * 100.0;
    }


}
