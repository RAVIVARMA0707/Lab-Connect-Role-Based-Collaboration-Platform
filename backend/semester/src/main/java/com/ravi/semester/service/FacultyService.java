package com.ravi.semester.service;

import com.ravi.semester.dto.StudentDTO;
import com.ravi.semester.model.Faculty;
import com.ravi.semester.model.Lab;
import com.ravi.semester.model.Student;
import com.ravi.semester.repository.FacultyRepo;
import com.ravi.semester.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    @Autowired
    private FacultyRepo facultyRepository;


    // Create Faculty
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    // Get All Faculties
    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    // Get Faculty by ID
    public Optional<Faculty> getFacultyById(String id) {
        return facultyRepository.findById(id);
    }

//    // Get Faculty by Email
//    public Optional<Faculty> getFacultyByEmail(String email) {
//        return facultyRepository.findByEmail(email);
//    }

    // Update Faculty
    public Optional<Faculty> updateFaculty(String id, Faculty updatedFaculty) {
        return facultyRepository.findById(id).map(faculty -> {
            faculty.setName(updatedFaculty.getName());
            faculty.setDepartment(updatedFaculty.getDepartment());
            faculty.setEmail(updatedFaculty.getEmail());
//            faculty.setLab(updatedFaculty.getLab());
            return facultyRepository.save(faculty);
        });
    }

    // Delete Faculty
    public boolean deleteFaculty(String id) {
        if (facultyRepository.existsById(id)) {
            facultyRepository.deleteById(id);
            return true;
        }
        return false;
    }




}
