package com.studentgradecalculator.group10projecticonicuniversity.repository;

import com.studentgradecalculator.group10projecticonicuniversity.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByUserId(Long userId);
}

