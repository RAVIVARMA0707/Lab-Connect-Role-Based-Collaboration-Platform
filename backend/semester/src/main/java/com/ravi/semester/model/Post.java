package com.ravi.semester.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    private LocalDateTime createdAt; // Added Date & Time Field

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;  // Post belongs to a Lab's Category

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student student;  // If posted by a student

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Faculty faculty;  // If posted by faculty

    @ManyToOne
    @JoinColumn(name = "inds_partner_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private IndsPartner indsPartner;  // If posted by an Industrial Partner

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Comment> comments = new ArrayList<>();

    public Post(Long id, String title, String content, LocalDateTime createdAt, Category category, Student student, Faculty faculty, IndsPartner indsPartner, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.category = category;
        this.student = student;
        this.faculty = faculty;
        this.indsPartner = indsPartner;
        this.comments = comments;
    }

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public IndsPartner getIndsPartner() {
        return indsPartner;
    }

    public void setIndsPartner(IndsPartner indsPartner) {
        this.indsPartner = indsPartner;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", category=" + category +
                ", student=" + student +
                ", faculty=" + faculty +
                ", indsPartner=" + indsPartner +
                ", comments=" + comments +
                '}';
    }
}

