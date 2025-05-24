package com.ravi.semester.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Student {
    @Id
    private String id;

    private String name;
    private String email;
    private Integer year;
    private String department;

    // A student belongs to one lab (Many-to-One relationship)
    @ManyToOne
    @JoinColumn(name = "lab_id", nullable = true) // A student can have only one lab
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Lab enrolledLab;

    @ElementCollection
    private List<String> preferredLabs = new ArrayList<>(); // Stores selected 5 lab IDs

    private int currentLabIndex = 0; // Keeps track of which lab request is pending
    private boolean eligibleForLab = true; // Default: Eligible
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED


    public Student() {
    }

    public Student(String id, String name, String email, Integer year, String department, Lab enrolledLab, List<String> preferredLabs, int currentLabIndex, boolean eligibleForLab, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.year = year;
        this.department = department;
        this.enrolledLab = enrolledLab;
        this.preferredLabs = preferredLabs;
        this.currentLabIndex = currentLabIndex;
        this.eligibleForLab = eligibleForLab;
        this.status = status;
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

    public Lab getEnrolledLab() {
        return enrolledLab;
    }

    public void setEnrolledLab(Lab enrolledLab) {
        this.enrolledLab = enrolledLab;
    }

    public List<String> getPreferredLabs() {
        return preferredLabs;
    }

    public void setPreferredLabs(List<String> preferredLabs) {
        this.preferredLabs = preferredLabs;
    }

    public int getCurrentLabIndex() {
        return currentLabIndex;
    }

    public void setCurrentLabIndex(int currentLabIndex) {
        this.currentLabIndex = currentLabIndex;
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

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", year=" + year +
                ", department='" + department + '\'' +
                ", enrolledLab=" + enrolledLab +
                ", preferredLabs=" + preferredLabs +
                ", currentLabIndex=" + currentLabIndex +
                ", eligibleForLab=" + eligibleForLab +
                ", status='" + status + '\'' +
                '}';
    }
}
