package com.ravi.semester.service;


import com.ravi.semester.model.Faculty;
import com.ravi.semester.model.IndsPartner;
import com.ravi.semester.model.Lab;
import com.ravi.semester.model.Student;
import com.ravi.semester.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LabService {

    @Autowired
    private LabRepo labRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private FacultyRepo facultyRepository;
    @Autowired
    private LabChangeRequestRepo labChangeRequestRepo;
    @Autowired
    private IndsPartnerRepo indsPartnerRepo;

    public Lab createLab(Lab lab) throws Exception {
        if (lab.getFaculty() != null && lab.getFaculty().getId() != null) {
            // Debug: print the incoming faculty ID
            System.out.println("Incoming Faculty ID: " + lab.getFaculty().getId());

            // Fetch Faculty from the database
            Faculty faculty = facultyRepository.findById(lab.getFaculty().getId())
                    .orElseThrow(() -> new Exception("Faculty not found"));

            // Debug: print the fetched faculty object
            System.out.println("Fetched Faculty: " + faculty);

            // Set the Faculty to the Lab entity
            lab.setFaculty(faculty);
        } else {
            // If no Faculty is provided, set it to null
            lab.setFaculty(null);
        }

        if (lab.getIndsPartner() != null && lab.getIndsPartner().getId() != null) {

            // Fetch Faculty from the database
            IndsPartner indsPartner = indsPartnerRepo.findById(lab.getIndsPartner().getId())
                    .orElseThrow(() -> new Exception("Industrial Partner not found"));

            lab.setIndsPartner(indsPartner);
        } else {
            // If no Faculty is provided, set it to null
            lab.setIndsPartner(null);
        }

        // Save and return the Lab entity
        Lab savedLab = labRepo.save(lab);

        // Debug: print the saved Lab
        System.out.println("Saved Lab: " + savedLab);

        return savedLab;
    }

    public List<Lab> getAllLabs() {
        return labRepo.findAll();
    }

    public Optional<Lab> getLabById(String id) {
        return labRepo.findById(id);
    }

    public Lab updateLab(String id, Lab lab) {
        Optional<Lab> labData = labRepo.findById(id);
        if (labData.isPresent()) {
            Lab existingLab = labData.get();
            existingLab.setName(lab.getName());
            existingLab.setDescription(lab.getDescription());
            existingLab.setPhotoUrl(lab.getPhotoUrl());
            existingLab.setFaculty(lab.getFaculty());
            existingLab.setIndsPartner(lab.getIndsPartner());
            return labRepo.save(existingLab);
        } else {
            return null;
        }
    }

    public boolean deleteLab(String id) {
        Optional<Lab> labData = labRepo.findById(id);
        if (labData.isPresent()) {
            Lab lab = labData.get();
            if (lab.getFaculty() != null) {
                lab.getFaculty().setLab(null);
            }
            if (lab.getIndsPartner() != null) {
                lab.getIndsPartner().setLab(null);
            }
            labChangeRequestRepo.deleteByCurrentLabIdOrDesiredLabId(lab.getId() );
            lab.getStudents().forEach(student -> {
                student.setEnrolledLab(null);
                student.setPreferredLabs(null);
                student.setCurrentLabIndex(0);
                student.setEligibleForLab(true);
                student.setStatus("PENDING");
                studentRepo.save(student); // Save the updated student to ensure the changes are persisted
            });
            labRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Student> getStudents(String id){
        Optional<Lab> labData = labRepo.findById(id);
        if (labData.isPresent()) {
            Lab lab = labData.get();
            return lab.getStudents();
        } else {
            return null;
        }
    }
}
