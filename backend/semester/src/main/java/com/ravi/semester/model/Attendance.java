package com.ravi.semester.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student student;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private boolean forenoonStatus = false;

    @Column(nullable = false)
    private boolean afternoonStatus = false;

    public Attendance() {}

    public Attendance(Student student, LocalDate date, boolean forenoonStatus, boolean afternoonStatus) {
        this.student = student;
        this.date = date;
        this.forenoonStatus = forenoonStatus;
        this.afternoonStatus = afternoonStatus;
    }

    public boolean isPresent() {
        return forenoonStatus && afternoonStatus;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isForenoonStatus() {
        return forenoonStatus;
    }

    public void setForenoonStatus(boolean forenoonStatus) {
        this.forenoonStatus = forenoonStatus;
    }

    public boolean isAfternoonStatus() {
        return afternoonStatus;
    }

    public void setAfternoonStatus(boolean afternoonStatus) {
        this.afternoonStatus = afternoonStatus;
    }
}
