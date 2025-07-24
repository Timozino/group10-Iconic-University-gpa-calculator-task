package com.studentgradecalculator.group10projecticonicuniversity.controller;

import com.studentgradecalculator.group10projecticonicuniversity.entity.Course;
import com.studentgradecalculator.group10projecticonicuniversity.entity.GradeComponent;
import com.studentgradecalculator.group10projecticonicuniversity.entity.User;
import com.studentgradecalculator.group10projecticonicuniversity.repository.CourseRepository;
import com.studentgradecalculator.group10projecticonicuniversity.repository.GradeComponentRepository;
import com.studentgradecalculator.group10projecticonicuniversity.repository.UserRepository;
import com.studentgradecalculator.group10projecticonicuniversity.utils.GradeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private final CourseRepository courseRepo;
    private final UserRepository userRepo;
    private final GradeComponentRepository gradeComponentRepo;

    public DashboardController(CourseRepository courseRepo, UserRepository userRepo, GradeComponentRepository gradeComponentRepo) {
        this.courseRepo = courseRepo;
        this.userRepo = userRepo;
        this.gradeComponentRepo = gradeComponentRepo;
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        List<Course> allCourses = courseRepo.findByUserId(user.getId());

        // Load grade components and ensure letter grades are set
        for (Course course : allCourses) {
            List<GradeComponent> components = course.getId() != null ?
                    gradeComponentRepo.findByCourseId(course.getId()) : List.of();
            course.setComponents(components);

            // IMPORTANT: Always calculate and set letter grade for consistency
            // This ensures both GPA calculation and chart display work properly
            String letterGrade = GradeUtil.getLetterGrade(course.getTotalScore());
            course.setLetterGrade(letterGrade);
        }

        // Group by semester for per-semester GPA
        Map<String, List<Course>> grouped = allCourses.stream()
                .collect(Collectors.groupingBy(Course::getSemester));

        Map<String, Double> semesterGpas = grouped.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> GradeUtil.calculateGpa(entry.getValue())
                ));

        double cgpa = GradeUtil.calculateGpa(allCourses);

        // Debug logging
        System.out.println("=== DEBUG INFO ===");
        System.out.println("Total courses: " + allCourses.size());
        for (Course course : allCourses) {
            System.out.println("Course: " + course.getName() +
                             ", Semester: " + course.getSemester() +
                             ", Score: " + course.getTotalScore() +
                             ", Letter Grade: " + course.getLetterGrade());
        }
        System.out.println("CGPA: " + cgpa);
        System.out.println("==================");

        model.addAttribute("user", user);
        model.addAttribute("courses", allCourses);
        model.addAttribute("cgpa", String.format("%.2f", cgpa));
        model.addAttribute("semesterGpas", semesterGpas);

        return "dashboard";
    }

    /**
     * Use the existing GradeUtil to calculate letter grade
     */
    private String calculateLetterGrade(double totalScore) {
        return GradeUtil.getLetterGrade(totalScore);
    }
}





//package com.studentgradecalculator.group10projecticonicuniversity.controller;
//
//import com.studentgradecalculator.group10projecticonicuniversity.entity.Course;
//import com.studentgradecalculator.group10projecticonicuniversity.entity.GradeComponent;
//import com.studentgradecalculator.group10projecticonicuniversity.entity.User;
//import com.studentgradecalculator.group10projecticonicuniversity.repository.CourseRepository;
//import com.studentgradecalculator.group10projecticonicuniversity.repository.GradeComponentRepository;
//import com.studentgradecalculator.group10projecticonicuniversity.repository.UserRepository;
//import com.studentgradecalculator.group10projecticonicuniversity.utils.GradeUtil;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.security.Principal;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Controller
//public class DashboardController {
//
//    private final CourseRepository courseRepo;
//    private final UserRepository userRepo;
//    private final GradeComponentRepository gradeComponentRepo;
//
//    public DashboardController(CourseRepository courseRepo, UserRepository userRepo, GradeComponentRepository gradeComponentRepo) {
//        this.courseRepo = courseRepo;
//        this.userRepo = userRepo;
//        this.gradeComponentRepo = gradeComponentRepo;
//    }
//
//    @GetMapping("/dashboard")
//    public String dashboard(Principal principal, Model model) {
//        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
//        List<Course> allCourses = courseRepo.findByUserId(user.getId());
//
//        // Load grade components for each course
//        for (Course course : allCourses) {
//            List<GradeComponent> components = course.getId() != null ?
//                    gradeComponentRepo.findByCourseId(course.getId()) : List.of();
//            course.setComponents(components);  // Now frontend will see components
//        }
//
//        // Group by semester for per-semester GPA
//        Map<String, List<Course>> grouped = allCourses.stream()
//                .collect(Collectors.groupingBy(Course::getSemester));
//
//        Map<String, Double> semesterGpas = grouped.entrySet().stream()
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        entry -> GradeUtil.calculateGpa(entry.getValue())
//                ));
//
//        double cgpa = GradeUtil.calculateGpa(allCourses);
//
//        model.addAttribute("user", user);
//        model.addAttribute("courses", allCourses);
//        model.addAttribute("cgpa", String.format("%.2f", cgpa));
//        model.addAttribute("semesterGpas", semesterGpas);
//
//        return "dashboard";
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////package com.studentgradecalculator.group10projecticonicuniversity.controller;
////
////import com.studentgradecalculator.group10projecticonicuniversity.entity.Course;
////import com.studentgradecalculator.group10projecticonicuniversity.entity.User;
////import com.studentgradecalculator.group10projecticonicuniversity.repository.CourseRepository;
////import com.studentgradecalculator.group10projecticonicuniversity.repository.UserRepository;
////import org.springframework.stereotype.Controller;
////import org.springframework.ui.Model;
////import org.springframework.web.bind.annotation.GetMapping;
////
////import java.security.Principal;
////import java.util.List;
////
////@Controller
////public class DashboardController {
////
////    private final CourseRepository courseRepo;
////    private final UserRepository userRepo;
////
////    public DashboardController(CourseRepository courseRepo, UserRepository userRepo) {
////        this.courseRepo = courseRepo;
////        this.userRepo = userRepo;
////    }
////
////    @GetMapping("/dashboard")
////    public String dashboard(Principal principal, Model model) {
////        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
////        List<Course> courses = courseRepo.findByUserId(user.getId());
////
////        double totalPoints = 0;
////        int totalCredits = 0;
////
////        for (Course course : courses) {
////            totalPoints += course.getTotalScore() * course.getCreditHours();
////            totalCredits += course.getCreditHours();
////        }
////
////        double gpa = totalCredits > 0 ? totalPoints / totalCredits : 0;
////
////        model.addAttribute("user", user);
////        model.addAttribute("courses", courses);
////        model.addAttribute("gpa", String.format("%.2f", gpa));
////
////        return "dashboard";
////    }
////}
////
