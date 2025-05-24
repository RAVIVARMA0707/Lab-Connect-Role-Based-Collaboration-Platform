package com.ravi.semester.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class LabChangeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)  // Add this
    private Student student;

    @ManyToOne
    @JoinColumn(name = "current_lab_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)  // Add this
    private Lab currentLab;

    @ManyToOne
    @JoinColumn(name = "desired_lab_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)  // Add this
    private Lab desiredLab;

    private boolean approvedByCurrentFaculty = false;
    private boolean approvedByDesiredFaculty = false;

    private String status = "PENDING"; // PENDING, APPROVED, REJECTED

    public boolean isFullyApproved() {
        return approvedByCurrentFaculty && approvedByDesiredFaculty;
    }

    @Override
    public String toString() {
        return "LabChangeRequest{" +
                "id=" + id +
                ", student=" + student +
                ", currentLab=" + currentLab +
                ", desiredLab=" + desiredLab +
                ", approvedByCurrentFaculty=" + approvedByCurrentFaculty +
                ", approvedByDesiredFaculty=" + approvedByDesiredFaculty +
                ", status='" + status + '\'' +
                '}';
    }

    public LabChangeRequest() {
    }

    public LabChangeRequest(Long id, Student student, Lab currentLab, Lab desiredLab, boolean approvedByCurrentFaculty, boolean approvedByDesiredFaculty, String status) {
        this.id = id;
        this.student = student;
        this.currentLab = currentLab;
        this.desiredLab = desiredLab;
        this.approvedByCurrentFaculty = approvedByCurrentFaculty;
        this.approvedByDesiredFaculty = approvedByDesiredFaculty;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Lab getCurrentLab() {
        return currentLab;
    }

    public void setCurrentLab(Lab currentLab) {
        this.currentLab = currentLab;
    }

    public Lab getDesiredLab() {
        return desiredLab;
    }

    public void setDesiredLab(Lab desiredLab) {
        this.desiredLab = desiredLab;
    }

    public boolean isApprovedByCurrentFaculty() {
        return approvedByCurrentFaculty;
    }

    public void setApprovedByCurrentFaculty(boolean approvedByCurrentFaculty) {
        this.approvedByCurrentFaculty = approvedByCurrentFaculty;
    }

    public boolean isApprovedByDesiredFaculty() {
        return approvedByDesiredFaculty;
    }

    public void setApprovedByDesiredFaculty(boolean approvedByDesiredFaculty) {
        this.approvedByDesiredFaculty = approvedByDesiredFaculty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
