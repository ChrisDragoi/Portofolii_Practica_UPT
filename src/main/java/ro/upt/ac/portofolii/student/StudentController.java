package ro.upt.ac.portofolii.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.upt.ac.portofolii.admin.AdminService;
import ro.upt.ac.portofolii.portofoliu.Portofoliu;
import ro.upt.ac.portofolii.portofoliu.PortofoliuRepository;
import org.springframework.web.multipart.MultipartFile;
import ro.upt.ac.portofolii.security.Role;
import ro.upt.ac.portofolii.utils.PasswordGenerator;

import java.io.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class StudentController
{
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private AdminService adminService;
	@Autowired
    private StudentService studentService;
	@Autowired
	private PortofoliuRepository portofoliuRepository;

    @GetMapping("/student-create")
	public String create(Model model)
	{
		model.addAttribute("student", new Student());
		return "student-create";
	}

	@PostMapping("/student-create-save")
	public String createSave(@Validated Student student, BindingResult result, Model model)
	{
		if(result.hasErrors())
		{
			return "student-create";
		}
		PasswordGenerator passwordGenerator = new PasswordGenerator();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = passwordGenerator.generateRandomPassword();
		student.setPassword(passwordEncoder.encode(rawPassword));
		student.setRole(Role.valueOf("STUDENT"));
		studentService.makeSign(studentRepository.save(student));
		try {
			adminService.addStudentCredentialsToCSV(student, rawPassword);
		} catch (IOException e) {
			model.addAttribute("error_message", "Eroare la salvarea în fișier CSV!");
			return "student-create";
		}
		return "redirect:/student-read";
	}

	@GetMapping("/student-read")
	public String read(Model model)
	{
		model.addAttribute("studenti", studentRepository.findAll());
		return "student-read";
	}

	@GetMapping("/student-edit/{id}")
	public String edit(@PathVariable("id") int id, Model model)
	{
		Student student = studentRepository.findById(id);

		model.addAttribute("student", student);
		return "student-update";
	}

	@PostMapping("/student-update/{id}")
	public String update(@PathVariable("id") int id, @Validated Student student, BindingResult result)
	{
		if(result.hasErrors())
		{
			student.setId(id);
			return "student-update";
		}

		Student existingCadru = studentRepository.findById(id);

		student.setPassword(existingCadru.getPassword());
		student.setSemnatura(existingCadru.getSemnatura());
		student.setRole(existingCadru.getRole());

		studentRepository.save(student);
		return "redirect:/student/"+id+"/index";
	}

	@GetMapping("/student-delete/{id}")
	public String delete(@PathVariable("id") int id) throws IOException {
		Student student = studentRepository.findById(id);

		studentService.deleteStudentsPortofolios(id);
		adminService.deleteStudentFolder(student);
		studentRepository.delete(student);
		return "redirect:/student-read";
	}

	@PostMapping(value = "/student/{id}/upload-signature", consumes = {"multipart/form-data"})
	public String uploadSignature(@PathVariable int id,
								  @RequestParam("signature") MultipartFile file,
								  RedirectAttributes redirectAttributes) {
		try {
            String baseDir = "semnaturi/studenti/";
            Path studentDir = Paths.get(baseDir, "student" + id);
			if (!Files.exists(studentDir)) {
				System.out.println("Eroare la incarcarea semnaturii");
				redirectAttributes.addFlashAttribute("error", "Folderul studentului nu există!");
				return "redirect:/student/"+id+"/index";
			}

			Path filePath = studentDir.resolve("signature.png");
			Files.write(filePath, file.getBytes());
			System.out.println("Semnătura a fost salvată la: " + filePath.toRealPath());

			redirectAttributes.addFlashAttribute("success", "Semnătura a fost actualizată cu succes!");
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error", "Eroare la salvarea semnăturii!");
		}

		return "redirect:/student/"+id+"/index";
	}

	@PostMapping("/student/{sid}/sign/portofoliu/{pid}")
	public String signStudent(@PathVariable int sid, @PathVariable int pid, RedirectAttributes redirectAttributes) {
		Portofoliu portofoliu = portofoliuRepository.findById(pid);
		if (portofoliu == null) {
			redirectAttributes.addFlashAttribute("error", "Portofoliul nu a fost găsit.");
			return "redirect:/portofoliu-read";
		}

		String signaturePath = portofoliu.getStudent().getSemnatura() + "/signature.png";
		File signatureFile = new File(signaturePath);

		if (!signatureFile.exists()) {
			redirectAttributes.addFlashAttribute("signStudentError", pid);
			return "redirect:/student-portofoliu-read/" + sid;
		}

		portofoliu.setSemnaturaStudent(true);
		portofoliuRepository.save(portofoliu);
		redirectAttributes.addFlashAttribute("success", "Semnătura studentului a fost înregistrată.");
		return "redirect:/student-portofoliu-read/" + sid;
	}

	@GetMapping("student/{sid}/portofoliu-view/{pid}")
	public String viewPortofoliu(@PathVariable("sid") int sid, @PathVariable("pid") int pid, Model model) {
		Portofoliu portofoliu = portofoliuRepository.findById(pid);

		if (portofoliu == null) {
			model.addAttribute("errorMessage", "Portofoliul nu a fost găsit.");
			return "redirect:/student-portofoliu-read/" + sid;
		}

		Student student = portofoliu.getStudent();

		model.addAttribute("portofoliuId", pid);
		model.addAttribute("student", student);

		return "student-vizualizare-portofoliu";
	}

	@GetMapping("student/{sid}/portofoliu-delete/{id}")
	public String portofoliuDelete(@PathVariable("id") int id, @PathVariable("sid") int sid, RedirectAttributes redirectAttributes)
	{
		Portofoliu portofoliu = portofoliuRepository.findById(id);
		if (portofoliu == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "Portofoliul nu a fost găsit.");
			return "redirect:/student-portofoliu-read/" + sid;
		}
		portofoliu.setStudent(null);
		portofoliu.setTutore(null);
		portofoliu.setCadruDidactic(null);
		portofoliuRepository.delete(portofoliu);
		return "redirect:/student-portofoliu-read/" + sid;
	}
}