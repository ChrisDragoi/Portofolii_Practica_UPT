package ro.upt.ac.portofolii.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidacticRepository;
import ro.upt.ac.portofolii.portofoliu.Portofoliu;
import ro.upt.ac.portofolii.portofoliu.PortofoliuRepository;
import ro.upt.ac.portofolii.student.Student;
import ro.upt.ac.portofolii.student.StudentRepository;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final StudentRepository studentRepository;
    private static final String UPLOAD_DIR = "upload";
    private final CadruDidacticRepository cadruDidacticRepository;
    @Autowired
    private PortofoliuRepository portofoliuRepository;

    public AdminController(AdminService adminService, StudentRepository studentRepository, CadruDidacticRepository cadruDidacticRepository) {
        this.adminService = adminService;
        this.studentRepository = studentRepository;
        this.cadruDidacticRepository = cadruDidacticRepository;
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

    @PostMapping("/sign/{portofoliuId}")
    public String semneazaPortofoliu(@PathVariable int portofoliuId) {
        Portofoliu portofoliu = portofoliuRepository.findById(portofoliuId);
        if(portofoliu == null){
            return "redirect:/";
        }
        Admin admin = (Admin) cadruDidacticRepository.findById(1);
        if (admin != null) {
            portofoliu.setCadruDidactic(admin);
            portofoliu.setSemnaturaCadruDidactic(true);
            portofoliu.setDataSemnarii(Date.valueOf(LocalDate.now()));
            portofoliuRepository.save(portofoliu);
        }

        return "redirect:/";
    }
}
