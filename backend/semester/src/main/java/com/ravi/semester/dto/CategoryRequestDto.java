package com.ravi.semester.dto;

public class CategoryRequestDto {
    private String facultyId;
    private String name;

    public CategoryRequestDto(String facultyId, String name) {
        this.facultyId = facultyId;
        this.name = name;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
