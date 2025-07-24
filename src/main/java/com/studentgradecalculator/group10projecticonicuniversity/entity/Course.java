package com.studentgradecalculator.group10projecticonicuniversity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @Column(nullable = false)
    private String semester; // e.g. "First Semester" or "Second Semester"

    private int creditHours;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<GradeComponent> components;

    private String code;


    private double totalScore;
    private String letterGrade;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Course(String code, List<GradeComponent> components, LocalDateTime createdAt, int creditHours, Long id, String letterGrade, String name, String semester, double totalScore, User user) {
        this.code = code;
        this.components = components;
        this.createdAt = createdAt;
        this.creditHours = creditHours;
        this.id = id;
        this.letterGrade = letterGrade;
        this.name = name;
        this.semester = semester;
        this.totalScore = totalScore;
        this.user = user;
    }

    //    public Course(String code, List<GradeComponent> components, LocalDateTime createdAt, int creditHours, Long id, String letterGrade, String name, double totalScore, User user) {
//        this.code = code;
//        this.components = components;
//        this.createdAt = createdAt;
//        this.creditHours = creditHours;
//        this.id = id;
//        this.letterGrade = letterGrade;
//        this.name = name;
//        this.totalScore = totalScore;
//        this.user = user;
//    }

    public Course() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<GradeComponent> getComponents() {
        return components;
    }

    public void setComponents(List<GradeComponent> components) {
        this.components = components;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSemester() {
    return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
