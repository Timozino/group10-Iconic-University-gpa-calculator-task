package com.studentgradecalculator.group10projecticonicuniversity.utils;

import com.studentgradecalculator.group10projecticonicuniversity.entity.Course;

import java.util.List;

public class GradeUtil {

    public static String getLetterGrade(double score) {
        if (score >= 70) return "A";
        else if (score >= 60) return "B";
        else if (score >= 50) return "C";
        else if (score >= 45) return "D";
        else if (score >= 40) return "E";
        else return "F";
    }

    public static double calculateGpa(List<Course> courses) {
        int totalUnits = 0;
        int totalPoints = 0;

        for (Course course : courses) {
            int unit = course.getCreditHours();
            int point = getPointFromGrade(course.getLetterGrade());
            totalUnits += unit;
            totalPoints += unit * point;
        }

        return totalUnits == 0 ? 0.0 : (double) totalPoints / totalUnits;
    }

    private static int getPointFromGrade(String grade) {
        return switch (grade.toUpperCase()) {
            case "A" -> 5;
            case "B" -> 4;
            case "C" -> 3;
            case "D" -> 2;
            case "E" -> 1;
            default -> 0;
        };
    }
}
