package com.ravi.semester.dto;

import com.ravi.semester.model.Lab;

public class LabDTO {
    private String id;
    private String name;
    private String description;
    private String faculty;
    private String indsPartner;

    public LabDTO(Lab lab) {
        this.id = lab.getId();
        this.name = lab.getName();
        this.description = lab.getDescription();
//        this.faculty = lab.getPhotoUrl();
        this.faculty = (lab.getFaculty() != null) ? lab.getFaculty().getName() : null;
        this.indsPartner = (lab.getIndsPartner()!=null)?lab.getIndsPartner().getName():null;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getIndsPartner() {
        return indsPartner;
    }

    public void setIndsPartner(String indsPartner) {
        this.indsPartner = indsPartner;
    }
}

