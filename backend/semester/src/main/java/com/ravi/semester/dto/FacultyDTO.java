package com.ravi.semester.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


public class FacultyDTO {

    private String id;
    private String name;
    private String email;
    private String dept;
    private String labId;
    private String labName;
//    private String labDescription;


    public FacultyDTO(String id, String name, String email, String dept, String labId, String labName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dept = dept;
        this.labId = labId;
        this.labName = labName;
    }

    public FacultyDTO() {
    }

    @Override
    public String toString() {
        return "FacultyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dept='" + dept + '\'' +
                ", labId=" + labId +
                ", labName='" + labName + '\'' +
                '}';
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

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }
}
