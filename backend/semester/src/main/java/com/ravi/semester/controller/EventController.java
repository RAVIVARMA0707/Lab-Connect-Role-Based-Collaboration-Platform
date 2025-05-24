package com.ravi.semester.controller;

import com.ravi.semester.model.Event;
import com.ravi.semester.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/pic/events")

public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping()
    public ResponseEntity<List<Event>> getAllEvents(){
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @PostMapping()
    public ResponseEntity<Event> createEvent(@RequestBody Event eventRequest) {
        eventService.createEvent(eventRequest);
        return ResponseEntity.ok(eventRequest);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId) {
        boolean isDeleted = eventService.deleteEvent(eventId);
        if (isDeleted) {
            return ResponseEntity.ok("Event and associated attendance records deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.");
        }
    }

    @GetMapping("/current/{studentId}/{date}")
    public ResponseEntity<Event> getCurrentEvent(
            @PathVariable String studentId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Event event = eventService.getCurrentLiveEvent(studentId, date);
        return ResponseEntity.ok(event);
    }

    @PutMapping()
    public ResponseEntity<?> updateEventTitle(@RequestBody Event updatedEvent) {
        String result = eventService.updateEventTitleOnly(updatedEvent);
        if (result.equals("Event not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }
}