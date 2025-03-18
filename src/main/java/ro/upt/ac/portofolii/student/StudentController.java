package ro.upt.ac.portofolii.student;

import org.springframework.beans.factory.annotation.Autowired;
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
import ro.upt.ac.portofolii.security.User;
import ro.upt.ac.portofolii.security.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class StudentController
{
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
    private UserRepository userRepository;
	@Autowired
	private AdminService adminService;
	@Autowired
	private PasswordEncoder passwordEncoder;
    @Autowired
    private StudentService studentService;

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
		studentService.makeSign(studentRepository.save(student));
		try {
			adminService.addStudentCredentialsToCSV(student);
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
		//.orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));

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
		Student existingTutore = studentRepository.findById(id);

		if (student.getSemnatura() == null) {
			student.setSemnatura(existingTutore.getSemnatura());
		}

		studentRepository.save(student);
		return "redirect:/student-read";
	}

	@GetMapping("/student-delete/{id}")
	public String delete(@PathVariable("id") int id) throws IOException {
		Student student = studentRepository.findById(id);
		//.orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));

		Optional<User> user=userRepository.findByEmail(student.getEmail());
		if(user.isEmpty()){
			throw new RuntimeException("User not found");
		}
		studentService.deleteStudentsPortofolios(id);
		User u = user.get();
		adminService.deleteStudentFolder(student);
		studentRepository.delete(student);
		userRepository.delete(u);
		return "redirect:/student-read";
	}
}