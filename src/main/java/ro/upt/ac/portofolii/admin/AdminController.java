package ro.upt.ac.portofolii.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.upt.ac.portofolii.student.Student;
import ro.upt.ac.portofolii.student.StudentRepository;
import ro.upt.ac.portofolii.student.StudentService;

import java.io.IOException;
import java.util.Scanner;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final StudentRepository studentRepository;
    private static final String UPLOAD_DIR = "upload";

    public AdminController(AdminService adminService, StudentRepository studentRepository) {
        this.adminService = adminService;
        this.studentRepository = studentRepository;
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

    @GetMapping("/admin-student-edit/{id}")
    public String edit(@PathVariable("id") int id, Model model)
    {
        Student student = studentRepository.findById(id);
        //.orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));

        model.addAttribute("student", student);
        return "admin-student-update";
    }

    @PostMapping("/admin-student-update/{id}")
    public String update(@PathVariable("id") int id, @Validated Student student, BindingResult result)
    {
        if(result.hasErrors())
        {
            student.setId(id);
            return "admin-student-update";
        }
        Student existingTutore = studentRepository.findById(id);

        if (student.getSemnatura() == null) {
            student.setSemnatura(existingTutore.getSemnatura());
        }

        studentRepository.save(student);
        return "redirect:/student-read";
    }
}
