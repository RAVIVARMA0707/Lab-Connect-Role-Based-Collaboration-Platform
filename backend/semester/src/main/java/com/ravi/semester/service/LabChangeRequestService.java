package com.ravi.semester.service;

import com.ravi.semester.dto.LabChangeRequestDTO;
import com.ravi.semester.model.Faculty;
import com.ravi.semester.model.Lab;
import com.ravi.semester.model.LabChangeRequest;
import com.ravi.semester.model.Student;
import com.ravi.semester.repository.FacultyRepo;
import com.ravi.semester.repository.LabChangeRequestRepo;
import com.ravi.semester.repository.LabRepo;
import com.ravi.semester.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LabChangeRequestService {
    @Autowired
    private LabChangeRequestRepo labChangeRequestRepository;

    @Autowired
    private StudentRepo studentRepository;

    @Autowired
    private LabRepo labRepository;

    @Autowired
    private FacultyRepo facultyRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    public Optional<LabChangeRequest> labChangeStudentStatus(String studentId){
        Optional<LabChangeRequest> existingRequest = labChangeRequestRepository
                .findByStudentId(studentId)
                .stream()
                .filter(req -> req.getStatus().equals("PENDING"))
                .findFirst();
    return existingRequest;
    }
    public String requestLabChange(String studentId, String desiredLabId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        Lab desiredLab = labRepository.findById(desiredLabId).orElse(null);

        if (student == null || desiredLab == null || student.getEnrolledLab() == null) {
            return "Invalid request. Check student and lab details.";
        }

        // Check if a request already exists
        Optional<LabChangeRequest> existingRequest = labChangeRequestRepository
                .findByStudentId(studentId)
                .stream()
                .filter(req -> req.getStatus().equals("PENDING"))
                .findFirst();

        if (existingRequest.isPresent()) {
            return "You already have a pending lab change request.";
        }

        // Create a new lab change request
        LabChangeRequest request = new LabChangeRequest();
        request.setStudent(student);
        request.setCurrentLab(student.getEnrolledLab());
        request.setDesiredLab(desiredLab);
        request.setStatus("PENDING");

        labChangeRequestRepository.save(request);
        return "Lab change request submitted.";
    }

    public String approveLabChange(Long requestId, String facultyId) {
        LabChangeRequest request = labChangeRequestRepository.findById(requestId).orElse(null);
        Student student = request.getStudent();
        if (request == null) {
            return "Invalid request ID.";
        }

        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        if (faculty == null) {
            return "Invalid faculty ID.";
        }

        // Check if faculty belongs to the current or desired lab
        if (request.getCurrentLab().getFaculty().getId().equals(facultyId)) {
            request.setApprovedByCurrentFaculty(true);
            String htmlBody;

            try {
                ClassPathResource resource = new ClassPathResource("templates/lab_regs_approved.html");
                htmlBody = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
                htmlBody = htmlBody.replace("${student_name}", student.getName());
                htmlBody = htmlBody.replace("${lab_name}", request.getCurrentLab().getName());
                htmlBody = htmlBody.replace("${faculty_name}", request.getCurrentLab().getFaculty().getName()); // Replace placeholder with actual username
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }

            emailSenderService.sendEmail(student.getEmail(),"Reg :- Lab change request",htmlBody);
        } else if (request.getDesiredLab().getFaculty().getId().equals(facultyId)) {
            request.setApprovedByDesiredFaculty(true);
            String htmlBody;

            try {
                ClassPathResource resource = new ClassPathResource("templates/lab_change_approved.html");
                htmlBody = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
                htmlBody = htmlBody.replace("${student_name}", student.getName());
                htmlBody = htmlBody.replace("${lab_name}", request.getDesiredLab().getName());
                htmlBody = htmlBody.replace("${faculty_name}", request.getDesiredLab().getFaculty().getName()); // Replace placeholder with actual username
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
            emailSenderService.sendEmail(student.getEmail(),"Reg :- Lab change request",htmlBody);
        } else {
            return "Faculty is not authorized to approve this request.";
        }

        // If both faculties approve, update student's lab assignment
        if (request.isFullyApproved()) {
//            Student student = request.getStudent();
            student.setEnrolledLab(request.getDesiredLab());
            studentRepository.save(student);
            request.setStatus("APPROVED");

            String htmlBody;
            try {
                ClassPathResource resource = new ClassPathResource("templates/lab_change_sucess.html");
                htmlBody = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
                htmlBody = htmlBody.replace("${student_name}", student.getName());
                htmlBody = htmlBody.replace("${cur_lab_name}", request.getCurrentLab().getName());
                htmlBody = htmlBody.replace("${des_lab_name}", request.getDesiredLab().getName());
                htmlBody = htmlBody.replace("${faculty_name}", request.getDesiredLab().getFaculty().getName()); // Replace placeholder with actual username
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }

            emailSenderService.sendEmail(student.getEmail(),"Reg :- Lab change request",htmlBody);
        }

        labChangeRequestRepository.save(request);
        return "Approval updated.";
    }

    public String rejectLabChange(Long requestId) {
        LabChangeRequest request = labChangeRequestRepository.findById(requestId).orElse(null);
        if (request == null) {
            return "Invalid request ID.";
        }

        request.setStatus("REJECTED");
        labChangeRequestRepository.save(request);
        return "Lab change request rejected.";
    }

    public List<LabChangeRequestDTO> getLabChangeRequestsForFaculty(String facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        if (faculty == null) {
            return Collections.emptyList(); // Return empty list if faculty not found
        }

        List<LabChangeRequest> requests = labChangeRequestRepository
                .findByCurrentLabFacultyIdOrDesiredLabFacultyId(facultyId, facultyId);

        return requests.stream()
                .map(request -> new LabChangeRequestDTO(request, facultyId)) // Pass facultyId for role determination
                .filter(dto ->
                        (("CURRENT_FACULTY".equals(dto.getFacultyRole()) && !dto.isApprovedByCurrentFaculty()) ||
                                ("DESIRED_FACULTY".equals(dto.getFacultyRole()) && !dto.isApprovedByDesiredFaculty()))&&("PENDING".equals(dto.getStatus()))
                )
                .collect(Collectors.toList());
    }
}
