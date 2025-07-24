package com.studentgradecalculator.group10projecticonicuniversity.entity;

import jakarta.persistence.*;

import java.util.List;

@Table(name="users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false, unique=true)
    private String admissionNumber;


    @Column(nullable=false)
    private String password;

    // One-to-many mapping
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Course> courses;

    public User() {
    }

    public User(String admissionNumber, List<Course> courses, String email, Long id, String name, String password) {
        this.admissionNumber = admissionNumber;
        this.courses = courses;
        this.email = email;
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
