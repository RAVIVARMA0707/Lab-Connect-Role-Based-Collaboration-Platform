package com.ravi.semester.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "inds_partner_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private IndsPartner indsPartner;

    public Comment(Long id, String content, LocalDateTime createdAt, Post post, Student student, Faculty faculty, IndsPartner indsPartner) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.post = post;
        this.student = student;
        this.faculty = faculty;
        this.indsPartner = indsPartner;
    }

    public Comment(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", post=" + post +
                ", student=" + student +
                ", faculty=" + faculty +
                ", indsPartner=" + indsPartner +
                '}';
    }
}
