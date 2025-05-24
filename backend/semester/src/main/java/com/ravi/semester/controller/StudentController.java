package com.ravi.semester.controller;



import com.ravi.semester.dto.StudentDTO;
import com.ravi.semester.model.Lab;
import com.ravi.semester.model.Student;
import com.ravi.semester.repository.StudentRepo;
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
@RequestMapping("/pic/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepo studentRepo;

    @PostMapping("/add")
    public ResponseEntity<Student> createLab(@RequestBody Student student) {
        try {
            System.out.println(student);
            Student student1 = studentService.createStudent(student);
            return new ResponseEntity<>(student1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            if (students.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            // Convert students to DTOs
            List<StudentDTO> studentDTOs = students.stream()
                    .map(StudentDTO::new)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(studentDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable String studentId) {


        StudentDTO studentDTO = studentService.getStudentById(studentId);
//        System.out.println(studentDTO.getPreferredLabs().get(studentDTO.getCurrentLabIndex()));
        if (studentDTO != null) {
//            return ResponseEntity.ok(studentDTO);
            return new ResponseEntity<>(studentDTO, HttpStatus.OK);
        } else {
//            return ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStudentFields(@PathVariable String id, @RequestBody Student updatedStudent) {
        Optional<Student> optionalStudent = studentRepo.findById(id);

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();

            // Update only non-null fields
            if (updatedStudent.getName() != null) {
                student.setName(updatedStudent.getName());
            }
            if (updatedStudent.getEmail() != null) {
                student.setEmail(updatedStudent.getEmail());
            }
            if (updatedStudent.getYear() != null) {
                student.setYear(updatedStudent.getYear());
            }
            if (updatedStudent.getDepartment() != null) {
                student.setDepartment(updatedStudent.getDepartment());
            }

            studentRepo.save(student);
            return ResponseEntity.ok("Student updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        if (studentService.deleteStudent(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/registerLabs/{studentId}")
    public ResponseEntity<String> registerLabs(@PathVariable String studentId, @RequestBody List<String> labIds) {
        boolean registered = studentService.registerLabs(studentId, labIds);
        System.out.println(labIds);
        if (registered) {
            return ResponseEntity.ok("Labs registered successfully. First faculty will receive request.");
        } else {
            return ResponseEntity.badRequest().body("Invalid student or labs.");
        }
    }
}
