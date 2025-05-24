package com.ravi.semester.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Lab {
    @Id
    private String id;

    private String name;
    private String description;
    private String photoUrl;

    @OneToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id", nullable = true)
    private Faculty faculty;

    @OneToOne
    @JoinColumn(name = "indus_id", referencedColumnName = "id", nullable = true)
    private IndsPartner indsPartner;

    // One lab can have many students (One-to-Many relationship)
    @OneToMany(mappedBy = "enrolledLab", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "lab", cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    public Lab(String id, String name, String description, String photoUrl, Faculty faculty, IndsPartner indsPartner, List<Student> students, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photoUrl = photoUrl;
        this.faculty = faculty;
        this.indsPartner = indsPartner;
        this.students = students;
        this.categories = categories;
    }

    public Lab() {
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public IndsPartner getIndsPartner() {
        return indsPartner;
    }

    public void setIndsPartner(IndsPartner indsPartner) {
        this.indsPartner = indsPartner;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Lab{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", faculty=" + faculty +
                ", indsPartner=" + indsPartner +
                ", students=" + students +
                '}';
    }
}
