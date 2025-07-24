package com.studentgradecalculator.group10projecticonicuniversity.service;

import com.studentgradecalculator.group10projecticonicuniversity.entity.Course;
import com.studentgradecalculator.group10projecticonicuniversity.entity.GradeComponent;
import com.studentgradecalculator.group10projecticonicuniversity.entity.User;
import com.studentgradecalculator.group10projecticonicuniversity.repository.CourseRepository;
import com.studentgradecalculator.group10projecticonicuniversity.repository.GradeComponentRepository;
import com.studentgradecalculator.group10projecticonicuniversity.utils.GradeUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepo;
    private final GradeComponentRepository componentRepo;

    public CourseService(CourseRepository courseRepo, GradeComponentRepository componentRepo) {
        this.courseRepo = courseRepo;
        this.componentRepo = componentRepo;
    }

    public Course saveCourse(Course course, User user) {
        course.setUser(user);

        // Fetch all grade components for this course
        List<GradeComponent> components = course.getComponents();  // Assuming set via form OR preload from DB

        double total = 0;
        double weightSum = 0;

        for (GradeComponent comp : components) {
            total += (comp.getScore() * comp.getWeight()) / 100;
            weightSum += comp.getWeight();
            comp.setCourse(course); // Make sure bidirectional relation is set
        }

        if (Math.round(weightSum) != 100) {
            throw new IllegalArgumentException("Total weight must equal 100%");
        }

        course.setTotalScore(total);
        course.setLetterGrade(GradeUtil.getLetterGrade(total));

        Course savedCourse = courseRepo.save(course);
        componentRepo.saveAll(components);  // Persist all components linked to this course

        return savedCourse;
    }
}
