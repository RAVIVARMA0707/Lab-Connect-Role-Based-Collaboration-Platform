package com.ravi.semester.controller;

import com.ravi.semester.model.Attendance;
import com.ravi.semester.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/pic/attendance")

public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/eligible/{date}/{facultyId}")
    public ResponseEntity<List<Attendance>> getEligibleStudents(
            @PathVariable LocalDate date,
            @PathVariable String facultyId) {
        return ResponseEntity.ok(attendanceService.getEligibleStudents(date, facultyId));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAttendance(@RequestBody Attendance request) {

        attendanceService.updateAttendance(request);
        return ResponseEntity.ok("Attendance updated successfully.");
    }

    @GetMapping("/status/{studentId}/{date}")
    public ResponseEntity<Attendance> getAttendanceStatus(
            @PathVariable String studentId,
            @PathVariable LocalDate date) {
        Attendance status = attendanceService.getAttendanceStatus(studentId, date);
        return ResponseEntity.ok(status);
    }
    @GetMapping("/percentage/{studentId}/{date}")
    public ResponseEntity<Double> getAttendancePercentage(
            @PathVariable String studentId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        double percentage = attendanceService.calculateAttendancePercentage(studentId, date);
        return ResponseEntity.ok(percentage);
    }

    @GetMapping("/count/{labId}/{date}")
    public ResponseEntity<Long> getStudentCountForLabOnDate(
            @PathVariable String labId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Long count = attendanceService.getStudentCountForLabOnDate(labId, date);
        return ResponseEntity.ok(count);
    }
}
