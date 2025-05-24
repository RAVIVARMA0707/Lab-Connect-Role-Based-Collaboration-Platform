package com.ravi.semester.controller;


import com.ravi.semester.dto.LabDTO;
import com.ravi.semester.dto.StudentDTO;
import com.ravi.semester.model.Faculty;
import com.ravi.semester.model.IndsPartner;
import com.ravi.semester.model.Lab;
import com.ravi.semester.model.Student;
import com.ravi.semester.repository.FacultyRepo;
import com.ravi.semester.repository.IndsPartnerRepo;
import com.ravi.semester.service.EmailSenderService;
import com.ravi.semester.service.LabService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pic/labs")
@CrossOrigin 
public class LabController {

    @Autowired
    private LabService labService;
    @Autowired
    private FacultyRepo facultyRepository;
    @Autowired
    private IndsPartnerRepo indsPartnerRepo;
    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping("/sendMail")
    public String sendMail() {
        String student_name="Ravivarma";
        String lab_name1 = "AI Based Industrial Automation" ;
        String lab_name2= "AI Based Industrial Automation" ;
        String lab_name3= "AI Based Industrial Automation" ;
        String lab_name4= "AI Based Industrial Automation" ;
        String lab_name5= "AI Based Industrial Automation" ;
//        String faculty_name="Sivabalakrishnan";

        String htmlBody;
        try {
            ClassPathResource resource = new ClassPathResource("templates/lab_regs_sucess.html");
            htmlBody = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            htmlBody = htmlBody.replace("${student_name}", student_name);
            htmlBody = htmlBody.replace("${lab1}", lab_name1);
            htmlBody = htmlBody.replace("${lab2}", lab_name2);
            htmlBody = htmlBody.replace("${lab3}", lab_name3);
            htmlBody = htmlBody.replace("${lab4}", lab_name4);
            htmlBody = htmlBody.replace("${lab5}", lab_name5);

//            htmlBody = htmlBody.replace("${des_lab_name}", lab_name2);
//            htmlBody = htmlBody.replace("${faculty_name}", faculty_name); // Replace placeholder with actual username
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

            emailSenderService.sendEmail("sanjay.ae21@bitsathy.ac.in", "Greeting", htmlBody);


        return "success";
}


    @PostMapping("/add")
    public ResponseEntity<LabDTO> createLab(@RequestBody Lab lab) {
        try {
            System.out.println("Lab Before Save: " + lab);

            // Set the faculty if it exists in the request
            if (lab.getFaculty() != null && lab.getFaculty().getId() != null) {
                Faculty faculty = facultyRepository.findById(lab.getFaculty().getId())
                        .orElseThrow(() -> new Exception("Faculty not found"));
                lab.setFaculty(faculty);
            }
            if (lab.getIndsPartner() != null && lab.getIndsPartner().getId() != null) {
                IndsPartner indsPartner = indsPartnerRepo.findById(lab.getIndsPartner().getId())
                        .orElseThrow(() -> new Exception("Industrial Partner not found"));
                lab.setIndsPartner(indsPartner);
            }

            Lab savedLab = labService.createLab(lab);
            LabDTO labDTO = new LabDTO(savedLab);
            return new ResponseEntity<>(labDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<LabDTO>> getAllLabs() {
        try {
            List<Lab> labs = labService.getAllLabs();
            if (labs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            // Convert labs to DTOs
            List<LabDTO> labDTOs = labs.stream()
                    .map(LabDTO::new)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(labDTOs, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Lab> getLabById(@PathVariable("id") String id) {
        Optional<Lab> labData = labService.getLabById(id);
        if (labData.isPresent()) {
            return new ResponseEntity<>(labData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getStudentsById(@PathVariable("id") String id) {
        try {
            List<Student> students = labService.getStudents(id);
            if (students.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}/students_count")
    public ResponseEntity<Long> getCount(@PathVariable("id") String id) {
        try {
            List<Student> students = labService.getStudents(id);
            if (students.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(students.stream().count(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Lab> updateLab(@PathVariable("id") String id, @RequestBody Lab lab) {
        System.out.println(lab);
        Lab updatedLab = labService.updateLab(id, lab);
        if (updatedLab != null) {
            return new ResponseEntity<>(updatedLab, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//It is Developing Stage
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteLab(@PathVariable("id") String id) {
        try {
            if (labService.deleteLab(id)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/byType/{labType}")
//    public ResponseEntity<List<Lab>> getLabsByType(@PathVariable("labType") String labType) {
//        try {
//            List<Lab> labs = labService.getLabsByType(labType);
//            if (labs.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(labs, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}




}
