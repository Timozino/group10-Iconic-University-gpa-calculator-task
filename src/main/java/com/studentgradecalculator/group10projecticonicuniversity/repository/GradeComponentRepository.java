package com.studentgradecalculator.group10projecticonicuniversity.repository;

import com.studentgradecalculator.group10projecticonicuniversity.entity.GradeComponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeComponentRepository extends JpaRepository<GradeComponent, Long> {
    List<GradeComponent> findByCourseId(Long courseId);
}

