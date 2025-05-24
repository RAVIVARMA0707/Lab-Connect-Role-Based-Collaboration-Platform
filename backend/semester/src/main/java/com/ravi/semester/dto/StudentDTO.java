package com.ravi.semester.dto;

import com.ravi.semester.model.Student;

import java.util.List;
import java.util.Optional;

public class StudentDTO {
    private String id;
    private String name;
    private String email;
    private Integer year;
    private String department;
    private LabDTO lab; // Include the Lab details
    private List<String> preferredLabs;
    private boolean eligibleForLab;
    private String status;
    private int currentLabIndex;


    public StudentDTO(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.email = student.getEmail();
        // Null check for lab
        this.lab = (student.getEnrolledLab() != null) ? new LabDTO(student.getEnrolledLab()) : null;
        this.preferredLabs=student.getPreferredLabs();
        this.eligibleForLab=student.isEligibleForLab();
        this.status =student.getStatus();
        this.year=student.getYear();
        this.department=student.getDepartment();
        this.currentLabIndex = student.getCurrentLabIndex();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LabDTO getLab() {
        return lab;
    }

    public void setLab(LabDTO lab) {
        this.lab = lab;
    }

    public List<String> getPreferredLabs() {
        return preferredLabs;
    }

    public void setPreferredLabs(List<String> preferredLabs) {
        this.preferredLabs = preferredLabs;
    }

    public boolean isEligibleForLab() {
        return eligibleForLab;
    }

    public void setEligibleForLab(boolean eligibleForLab) {
        this.eligibleForLab = eligibleForLab;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCurrentLabIndex() {
        return currentLabIndex;
    }

    public void setCurrentLabIndex(int currentLabIndex) {
        this.currentLabIndex = currentLabIndex;
    }
}
