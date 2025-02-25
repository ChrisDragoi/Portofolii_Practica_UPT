package ro.upt.ac.portofolii.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private static final String UPLOAD_DIR = "upload";

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "/upload-students", consumes ={"multipart/form-data"})
    @ResponseBody
    public ResponseEntity<String> uploadStudentCsv(@RequestParam("file")MultipartFile file) {
        try {
            String filePath=adminService.saveStudentCsv(file, UPLOAD_DIR);
            return ResponseEntity.ok("Fișier salvat cu succes la: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Eroare la salvarea fișierului: " + e.getMessage());
        }
    }
}
