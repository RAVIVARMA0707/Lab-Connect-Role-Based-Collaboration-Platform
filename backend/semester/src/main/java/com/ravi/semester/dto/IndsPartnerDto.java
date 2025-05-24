package com.ravi.semester.dto;

public class IndsPartnerDto {

    private String id;
    private String name;
    private String email;
    private String labId;
    private String labName;

    public IndsPartnerDto() {
    }

    public IndsPartnerDto(String id, String name, String email, String labId, String labName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.labId = labId;
        this.labName = labName;
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

    @Override
    public String toString() {
        return "IndsPartnerDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", labId=" + labId +
                ", labName='" + labName + '\'' +
                '}';
    }
}
