package com.ravi.semester.service;

import com.ravi.semester.model.Attendance;
import com.ravi.semester.model.Event;
import com.ravi.semester.model.Student;
import com.ravi.semester.repository.AttendanceRepository;
import com.ravi.semester.repository.EventRepository;
import com.ravi.semester.repository.StudentRepo;
import com.sun.jdi.request.EventRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service

public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private StudentRepo studentRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;

    public void createEvent(Event request) {
        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        event.setYear(request.getYear());

        eventRepository.save(event);

        // Auto-generate attendance records
        generateAttendanceRecords(event);
    }

    private void generateAttendanceRecords(Event event) {
        List<Student> eligibleStudents = studentRepository.findByYear(event.getYear());

        for (Student student : eligibleStudents) {
            LocalDate currentDate = event.getStartDate();
            while (!currentDate.isAfter(event.getEndDate())) {
                Attendance attendance = new Attendance();
                attendance.setStudent(student);
                attendance.setDate(currentDate);
                attendance.setForenoonStatus(false);
                attendance.setAfternoonStatus(false);

                attendanceRepository.save(attendance);
                currentDate = currentDate.plusDays(1);
            }
        }
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getCurrentLiveEvent(String studentId, LocalDate currentDate) {
        // Get student's year from Student entity
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        int studentYear = student.getYear();

        // Check for a live event for the student's year
        return eventRepository.findLiveEventOnDate(studentYear, currentDate)
                .orElseThrow(() -> new RuntimeException("No ongoing event for this student."));
    }

    @Transactional
    public boolean deleteEvent(Long eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();

            // Delete corresponding attendance records based on event dates and year
            deleteAttendanceRecords(event.getStartDate(), event.getEndDate(), event.getYear());

            // Delete the event after deleting attendance records
            eventRepository.deleteById(eventId);
            return true;
        }
        return false;
    }

    private void deleteAttendanceRecords(LocalDate startDate, LocalDate endDate, int year) {
        List<Student> eligibleStudents = studentRepository.findByYear(year);

        for (Student student : eligibleStudents) {
            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                attendanceRepository.deleteByStudentAndDate(student, currentDate);
                currentDate = currentDate.plusDays(1);
            }
        }
    }
    @Transactional
    public String updateEventTitleOnly(Event updatedEvent) {
        Long eventId = updatedEvent.getId();
        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isEmpty()) {
            return "Event not found";
        }

        Event existingEvent = optionalEvent.get();
        existingEvent.setTitle(updatedEvent.getTitle());
        eventRepository.save(existingEvent);
        return "Event title updated successfully";
    }
}
