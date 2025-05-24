package com.ravi.semester.controller;


import com.ravi.semester.dto.FacultyDTO;
import com.ravi.semester.dto.StudentDTO;
import com.ravi.semester.model.Faculty;
import com.ravi.semester.model.Lab;
import com.ravi.semester.model.Student;
import com.ravi.semester.repository.FacultyRepo;
import com.ravi.semester.repository.StudentRepo;
import com.ravi.semester.service.FacultyService;
import com.ravi.semester.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/pic/faculty")
public class FacultyController {
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private  FacultyRepo facultyRepo;
    @Autowired
    private StudentRepo studentRepo;


    // Create Faculty
    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        System.out.println(faculty);
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }

    @GetMapping
    public List<FacultyDTO> getAllFaculties() {
        List<Faculty> faculties = facultyRepo.findAll();
        return faculties.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<FacultyDTO> getFacultyById(@PathVariable("id") String id) {
        return facultyRepo.findById(id).map(this::convertToDTO);
    }

    private FacultyDTO convertToDTO(Faculty faculty) {
        return new FacultyDTO(
                faculty.getId(),
                faculty.getName(),
                faculty.getEmail(),
                faculty.getDepartment(),
                faculty.getLab() != null ? faculty.getLab().getId() : null,
                faculty.getLab() != null ? faculty.getLab().getName() : null
        );
    }

    // Get All Faculties
//    @GetMapping
//    public ResponseEntity<List<Faculty>> getAllFaculties() {
//        return ResponseEntity.ok(facultyService.getAllFaculties());
//    }
//
//    // Get Faculty by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id) {
//        return facultyService.getFacultyById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    // Get Faculty by Email
//    @GetMapping("/email/{email}")
//    public ResponseEntity<Faculty> getFacultyByEmail(@PathVariable String email) {
//        return facultyService.getFacultyByEmail(email)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    // Update Faculty
    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable String id, @RequestBody Faculty faculty) {
        return facultyService.updateFaculty(id, faculty)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete Faculty
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable String id) {
        if (facultyService.deleteFaculty(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }



    @PostMapping("/approve/{studentId}")
    public ResponseEntity<String> approveStudent(@PathVariable String studentId) {
        boolean success = studentService.approveStudent(studentId);
        return success ? ResponseEntity.ok("Student enrolled successfully!") : ResponseEntity.badRequest().body("Request not found.");
    }

    @PostMapping("/reject/{studentId}")
    public ResponseEntity<String> rejectStudent(@PathVariable String studentId) {
        studentService.rejectStudent(studentId);
        return ResponseEntity.ok("Request moved to next faculty.");
    }

    @GetMapping("/requests/{facultyId}")
    public ResponseEntity<List<StudentDTO>> getPendingRequests(@PathVariable String facultyId) {
        Faculty faculty = facultyRepo.findById(facultyId).orElse(null);
        if (faculty == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Lab lab = faculty.getLab();

        if (lab == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

//        System.out.println(lab.getId());
        List<Student> pendingStudents = studentRepo.findAll().stream()
                .filter(student -> student.getStatus().equals("PENDING"))
                .filter(student -> student.getPreferredLabs() != null
                        && !student.getPreferredLabs().isEmpty()
                        && student.getCurrentLabIndex() >= 0
                        && student.getCurrentLabIndex() < student.getPreferredLabs().size()
                        && student.getPreferredLabs().get(student.getCurrentLabIndex()).equals(String.valueOf(lab.getId())))
                .collect(Collectors.toList());

        List<StudentDTO> studentDTOs = pendingStudents.stream().map(StudentDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(studentDTOs, HttpStatus.OK);
    }

}
