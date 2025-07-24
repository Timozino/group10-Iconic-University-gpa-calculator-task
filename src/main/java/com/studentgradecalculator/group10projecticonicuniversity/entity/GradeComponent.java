package com.studentgradecalculator.group10projecticonicuniversity.entity;

import jakarta.persistence.*;

@Entity
public class GradeComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // assignment, midterm, final
    private double score;
    private double weight;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    public GradeComponent() {
    }

    public GradeComponent(Course course, Long id, double score, String type, double weight) {
        this.course = course;
        this.id = id;
        this.score = score;
        this.type = type;
        this.weight = weight;
    }

    public GradeComponent(String assignment, double assignmentScore, double assignmentWeight, Course course) {
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
