package com.studentgradecalculator.group10projecticonicuniversity.controller;

import com.studentgradecalculator.group10projecticonicuniversity.entity.Course;
import com.studentgradecalculator.group10projecticonicuniversity.entity.User;
import com.studentgradecalculator.group10projecticonicuniversity.repository.CourseRepository;
import com.studentgradecalculator.group10projecticonicuniversity.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.OutputStream;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class PdfExportController {

    private final CourseRepository courseRepo;
    private final UserRepository userRepo;

    public PdfExportController(CourseRepository courseRepo, UserRepository userRepo) {
        this.courseRepo = courseRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/export/pdf")
    public void exportToPdf(HttpServletResponse response, Principal principal) throws Exception {
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        List<Course> courses = courseRepo.findByUserId(user.getId());

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=grades.pdf");

        String html = generateHtml(user, courses);

        OutputStream out = response.getOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(out);
        out.close();
    }

    private String generateHtml(User user, List<Course> courses) {
        double totalScore = 0;
        int totalCredits = 0;

        for (Course c : courses) {
            totalScore += c.getTotalScore() * c.getCreditHours();
            totalCredits += c.getCreditHours();
        }
        double gpa = totalCredits > 0 ? totalScore / totalCredits : 0;
        String printDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));

        String logoPath = "file:///absolute/path/to/logo.png"; // replace with your absolute path or base64

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><style>");
        sb.append("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; color: #25435b; padding: 20px; }");
        sb.append(".header { display: flex; align-items: center; gap: 20px; margin-bottom: 20px; }");
        sb.append("img.logo { height: 60px; }");
        sb.append("h1 { margin: 0; color: #25435b; }");
        sb.append("table { width: 100%; border-collapse: collapse; margin-top: 1rem; }");
        sb.append("th, td { border: 1px solid #25435b; padding: 8px 12px; text-align: left; }");
        sb.append("th { background-color: #25435b; color: white; }");
        sb.append("tr:nth-child(even) { background-color: #f2f2f2; }");
        sb.append(".footer { margin-top: 2rem; font-size: 0.9rem; color: #666666; }");
        sb.append("</style></head><body>");

        // Header
        sb.append("<div class='header'>")
          .append("<img class='logo' src='").append(logoPath).append("' alt='Logo'>")
          .append("<div><h1>Grade Report</h1></div>")
          .append("</div>");

        // Student Info
        sb.append("<p><strong>Name:</strong> ").append(user.getName()).append("</p>");
        sb.append("<p><strong>Admission Number:</strong> ").append(user.getAdmissionNumber()).append("</p>");

        // Table
        sb.append("<table>");
        sb.append("<tr><th>Course Title</th><th>Course Code</th><th>Credit Unit</th><th>Score (%)</th><th>Grade</th></tr>");
        for (Course c : courses) {
            sb.append("<tr>");
            sb.append("<td>").append(c.getName()).append("</td>");
            sb.append("<td>").append(c.getCode() != null ? c.getCode() : "N/A").append("</td>");
            sb.append("<td>").append(c.getCreditHours()).append("</td>");
            sb.append("<td>").append(String.format("%.2f", c.getTotalScore())).append("</td>");
            sb.append("<td>").append(c.getLetterGrade()).append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");

        // Footer
        sb.append("<div class='footer'>");
        sb.append("<p><strong>GPA:</strong> ").append(String.format("%.2f", gpa)).append("</p>");
        sb.append("<p>Generated on ").append(printDate).append("</p>");
        sb.append("</div>");

        sb.append("</body></html>");
        return sb.toString();
    }
}
