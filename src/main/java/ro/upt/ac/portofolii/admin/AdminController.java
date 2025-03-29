package ro.upt.ac.portofolii.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Scanner;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private static final String UPLOAD_DIR = "upload";

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "/upload-students", consumes ={"multipart/form-data"})
    public String uploadStudentCsv(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String filePath = adminService.saveStudentCsv(file, UPLOAD_DIR);
            model.addAttribute("success_message", filePath + "\n");
        } catch (IOException e) {
            model.addAttribute("success_message", "Eroare la încărcare: " + e.getMessage());
        }
        model.addAttribute("studenti", adminService.getAllStudents());
        return "student-read";
    }
}
