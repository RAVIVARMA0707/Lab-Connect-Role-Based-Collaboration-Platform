package com.ravi.semester.controller;

import com.ravi.semester.dto.LabChangeRequestDTO;
import com.ravi.semester.model.Lab;
import com.ravi.semester.model.LabChangeRequest;
import com.ravi.semester.service.LabChangeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/pic/lab-change")
@CrossOrigin
public class LabChangeRequestController {
    @Autowired
    private LabChangeRequestService labChangeRequestService;

    @GetMapping("/student-status/{studentId}")
    public ResponseEntity<LabChangeRequestDTO> labChangeStudentStatus(@PathVariable String studentId){
        Optional<LabChangeRequest> labData = labChangeRequestService.labChangeStudentStatus(studentId);
        if (labData.isPresent()) {

            return new ResponseEntity<>(new LabChangeRequestDTO(labData.get(),"Hii"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @PostMapping("/request/{studentId}/{desiredLabId}")
    public ResponseEntity<String> requestLabChange(@PathVariable String studentId, @PathVariable String desiredLabId) {
        String response = labChangeRequestService.requestLabChange(String.valueOf(studentId), desiredLabId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/approve/{requestId}/{facultyId}")
    public ResponseEntity<String> approveLabChange(@PathVariable Long requestId, @PathVariable String facultyId) {
        String response = labChangeRequestService.approveLabChange(requestId, facultyId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reject/{requestId}")
    public ResponseEntity<String> rejectLabChange(@PathVariable Long requestId) {
        String response = labChangeRequestService.rejectLabChange(requestId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/faculty/{facultyId}/requests")
    public ResponseEntity<List<LabChangeRequestDTO>> getLabChangeRequestsForFaculty(@PathVariable String facultyId) {
        List<LabChangeRequestDTO> requests = labChangeRequestService.getLabChangeRequestsForFaculty(facultyId);
//        if (requests.isEmpty()) {
//            return ResponseEntity.noContent().build(); // 204 No Content if no requests found
//        }
        return ResponseEntity.ok(requests);
    }
}
