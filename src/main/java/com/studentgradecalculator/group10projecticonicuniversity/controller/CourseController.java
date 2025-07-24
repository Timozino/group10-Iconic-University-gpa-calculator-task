package com.studentgradecalculator.group10projecticonicuniversity.controller;

import com.studentgradecalculator.group10projecticonicuniversity.entity.Course;
import com.studentgradecalculator.group10projecticonicuniversity.entity.GradeComponent;
import com.studentgradecalculator.group10projecticonicuniversity.entity.User;
import com.studentgradecalculator.group10projecticonicuniversity.repository.CourseRepository;
import com.studentgradecalculator.group10projecticonicuniversity.repository.GradeComponentRepository;
import com.studentgradecalculator.group10projecticonicuniversity.repository.UserRepository;
import com.studentgradecalculator.group10projecticonicuniversity.utils.GradeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseRepository courseRepo;
    private final GradeComponentRepository gradeRepo;
    private final UserRepository userRepo;

    public CourseController(CourseRepository courseRepo,
                            GradeComponentRepository gradeRepo,
                            UserRepository userRepo) {
        this.courseRepo = courseRepo;
        this.gradeRepo = gradeRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "add-course";
    }

    @PostMapping("/save")
    public String saveCourse(@RequestParam String name,
                             @RequestParam int creditHours,
                             @RequestParam String semester,
                             @RequestParam String code,
                             @RequestParam double assignmentScore,
                             @RequestParam double assignmentMax,
                             @RequestParam double midtermScore,
                             @RequestParam double midtermMax,
                             @RequestParam double finalScore,
                             @RequestParam double finalMax,
                             Principal principal) {

        User user = userRepo.findByEmail(principal.getName()).orElseThrow();

        double totalMax = assignmentMax + midtermMax + finalMax;
        if (Math.round(totalMax) != 100) {
            throw new IllegalArgumentException("Total of max scores must equal 100 (currently " + totalMax + ")");
        }

        double totalScore = assignmentScore + midtermScore + finalScore;
        String letter = GradeUtil.getLetterGrade(totalScore);

        Course course = new Course();
        course.setName(name);
        course.setCreditHours(creditHours);
        course.setSemester(semester);
        course.setCode(code);
        course.setTotalScore(totalScore);
        course.setLetterGrade(letter);
        course.setUser(user);
        courseRepo.save(course);

        List<GradeComponent> components = List.of(
            new GradeComponent("Assignment", assignmentScore, assignmentMax, course),
            new GradeComponent("Midterm", midtermScore, midtermMax, course),
            new GradeComponent("Final", finalScore, finalMax, course)
        );

        gradeRepo.saveAll(components);
        return "redirect:/dashboard";
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
//import com.studentgradecalculator.group10projecticonicuniversity.service.CourseService;
//import com.studentgradecalculator.group10projecticonicuniversity.utils.GradeUtil;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//import java.util.Arrays;
//import java.util.List;
//
//@Controller
//@RequestMapping("/course")
//public class CourseController {
//
//    private final CourseRepository courseRepo;
//    private final GradeComponentRepository gradeRepo;
//    private final UserRepository userRepo;
//
//    public CourseController(CourseRepository courseRepo,
//                            GradeComponentRepository gradeRepo,
//                            UserRepository userRepo) {
//        this.courseRepo = courseRepo;
//        this.gradeRepo = gradeRepo;
//        this.userRepo = userRepo;
//    }
//
//    @GetMapping("/add")
//    public String showAddForm() {
//        return "add-course";
//    }
//
//    @PostMapping("/save")
//    public String saveCourse(@RequestParam String name,
//                             @RequestParam int creditHours,
//                             @RequestParam double assignmentScore,
//                             @RequestParam double assignmentWeight,
//                             @RequestParam double midtermScore,
//                             @RequestParam double midtermWeight,
//                             @RequestParam double finalScore,
//                             @RequestParam double finalWeight,
//                             Principal principal) {
//
//        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
//
//        double totalScore = (assignmentScore * assignmentWeight +
//                             midtermScore * midtermWeight +
//                             finalScore * finalWeight) / 100.0;
//
//        String letter = GradeUtil.getLetterGrade(totalScore);
//
//        Course course = new Course();
//        course.setName(name);
//        course.setCreditHours(creditHours);
//        course.setTotalScore(totalScore);
//        course.setLetterGrade(letter);
//        course.setUser(user);
//        courseRepo.save(course);
//
//        List<GradeComponent> components = List.of(
//            new GradeComponent("Assignment", assignmentScore, assignmentWeight, course),
//            new GradeComponent("Midterm", midtermScore, midtermWeight, course),
//            new GradeComponent("Final", finalScore, finalWeight, course)
//        );
//
//        gradeRepo.saveAll(components);
//        return "redirect:/dashboard";
//    }
//}
//
