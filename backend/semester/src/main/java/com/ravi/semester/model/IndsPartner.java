package com.ravi.semester.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class IndsPartner {
    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String email; // Unique email for faculty

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "indsPartner", cascade = CascadeType.ALL)
    private Lab lab;

    public IndsPartner() {
    }

    public IndsPartner(String id, String email, String name, Lab lab) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.lab = lab;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    @Override
    public String toString() {
        return "IndsPartner{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lab=" + lab +
                '}';
    }
}
