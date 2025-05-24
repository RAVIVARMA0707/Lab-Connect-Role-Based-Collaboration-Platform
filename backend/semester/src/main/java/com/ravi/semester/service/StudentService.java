package com.ravi.semester.service;

import com.ravi.semester.dto.StudentDTO;
import com.ravi.semester.model.Lab;
import com.ravi.semester.model.Student;
import com.ravi.semester.repository.FacultyRepo;
import com.ravi.semester.repository.LabRepo;
import com.ravi.semester.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private FacultyRepo facultyRepository;
    @Autowired
    private LabRepo labRepository;
    @Autowired
    private EmailSenderService emailSenderService;

    public Student createStudent(Student student) {
        return studentRepo.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }


    public boolean registerLabs(String studentId, List<String> labIds) {
        Student student = studentRepo.findById(studentId).orElse(null);
        if (student == null || labIds.size() != 5) return false;
        student.setPreferredLabs(labIds);
        student.setCurrentLabIndex(0);
        student.setStatus("PENDING");
        studentRepo.save(student);
        String htmlBody;

        try {
            ClassPathResource resource = new ClassPathResource("templates/lab_regs_sucess.html");
            htmlBody = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            htmlBody = htmlBody.replace("${student_name}", student.getName());

            String labId = student.getPreferredLabs().get(0);
            Lab lab = labRepository.findById(labId).orElse(null);
            htmlBody = htmlBody.replace("${lab1}", lab.getName() );

            labId = student.getPreferredLabs().get(1);
            lab = labRepository.findById(labId).orElse(null);
            htmlBody = htmlBody.replace("${lab2}", lab.getName() );

            labId = student.getPreferredLabs().get(2);
            lab = labRepository.findById(labId).orElse(null);
            htmlBody = htmlBody.replace("${lab3}", lab.getName() );

            labId = student.getPreferredLabs().get(3);
            lab = labRepository.findById(labId).orElse(null);
            htmlBody = htmlBody.replace("${lab4}", lab.getName() );

            labId = student.getPreferredLabs().get(4);
            lab = labRepository.findById(labId).orElse(null);
            htmlBody = htmlBody.replace("${lab5}", lab.getName() );


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        emailSenderService.sendEmail(student.getEmail(),"Reg :- Lab registration status",htmlBody);
//        emailSenderService.sendEmail(student.getEmail(),"Reg :- Lab registration","You are sucessfully registered the special labs");
        sendRequestToNextLab(student);
        return true;
    }

    private void sendRequestToNextLab(Student student) {
        if (student.getCurrentLabIndex() >= student.getPreferredLabs().size()) {
            student.setStatus("REJECTED");
            student.setEligibleForLab(false);
            studentRepo.save(student);
            emailSenderService.sendEmail(student.getEmail(),"Reg :- Lab registration status","Sory You are not elligible for the selection process ");
            return;
        }

        String labId = student.getPreferredLabs().get(student.getCurrentLabIndex());
        Lab lab = labRepository.findById(labId).orElse(null);
        if (lab != null && lab.getFaculty() != null) {
            System.out.println("Request sent to faculty: " + lab.getFaculty().getName());
        } else {
            student.setCurrentLabIndex(student.getCurrentLabIndex() + 1);
            sendRequestToNextLab(student);
        }
    }

    public boolean approveStudent(String studentId) {
        Student student = studentRepo.findById(studentId).orElse(null);
        if (student == null || student.getEnrolledLab() != null) return false;

        String labId = student.getPreferredLabs().get(student.getCurrentLabIndex());
        Lab lab = labRepository.findById(labId).orElse(null);
        if (lab != null) {
            student.setEnrolledLab(lab);
            student.setStatus("APPROVED");
            studentRepo.save(student);
            String htmlBody;

            try {
                ClassPathResource resource = new ClassPathResource("templates/lab_regs_approved.html");
                htmlBody = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
                htmlBody = htmlBody.replace("${student_name}", student.getName());
                htmlBody = htmlBody.replace("${lab_name}", lab.getName());
                htmlBody = htmlBody.replace("${faculty_name}", lab.getFaculty().getName()); // Replace placeholder with actual username
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            emailSenderService.sendEmail(student.getEmail(),"Reg :- Lab registration status",htmlBody);
            return true;
        }
        return false;
    }

    public void rejectStudent(String studentId) {
        Student student = studentRepo.findById(studentId).orElse(null);
        String labId = student.getPreferredLabs().get(student.getCurrentLabIndex());
        Lab lab = labRepository.findById(labId).orElse(null);
        String htmlBody;
        if(lab!=null) {
            try {
                ClassPathResource resource = new ClassPathResource("templates/lab_regs_rejected.html");
                htmlBody = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
                htmlBody = htmlBody.replace("${student_name}", student.getName());
                htmlBody = htmlBody.replace("${lab_name}", lab.getName());
                htmlBody = htmlBody.replace("${faculty_name}", lab.getFaculty().getName()); // Replace placeholder with actual username
            } catch (IOException e) {
                e.printStackTrace();
                return ;
            }
            emailSenderService.sendEmail(student.getEmail(),"Reg :- Lab registration status",htmlBody);
        }
        if (student == null) return;

        student.setCurrentLabIndex(student.getCurrentLabIndex() + 1);
        studentRepo.save(student);
        sendRequestToNextLab(student);
    }

    public StudentDTO getStudentById(String studentId) {
        Student student = studentRepo.findById(studentId).orElse(null);
        if (student == null) {
            return null;
        }
        return new StudentDTO(student); // Convert to DTO
    }



    public boolean deleteStudent(String id) {
        if (studentRepo.existsById(id)) {
            studentRepo.deleteById(id);
            return true;
        }
        return false;
    }


}
