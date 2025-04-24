package ro.upt.ac.portofolii.portofoliu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.upt.ac.portofolii.admin.Admin;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidactic;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidacticRepository;
import ro.upt.ac.portofolii.student.Student;
import ro.upt.ac.portofolii.student.StudentRepository;
import ro.upt.ac.portofolii.tutore.Tutore;
import ro.upt.ac.portofolii.tutore.TutoreRepository;
import ro.upt.ac.portofolii.tutore.TutoreService;
import ro.upt.ac.portofolii.utils.PdfGenerator;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PortofoliuController
{
	@Autowired
	private PortofoliuRepository portofoliuRepository;
	@Autowired
	private CadruDidacticRepository cadruDidacticRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TutoreService tutoreService;
    @Autowired
    private TutoreRepository tutoreRepository;

	@GetMapping("/")
	public String root(Model model)
	{
		Admin admin = (Admin) cadruDidacticRepository.findById(1);
		model.addAttribute("admin", admin);
		return "index";
	}

	@GetMapping("/portofoliu-create/{id}")
	public String create(@PathVariable("id") int id, Portofoliu portofoliu, Model model)
	{
		Student student = studentRepository.findById(id);
		if (student == null) {
			throw new RuntimeException("Student inexistent cu ID: " + id);
		}
		model.addAttribute("portofoliu", portofoliu);
		model.addAttribute("cadreDidactice", cadruDidacticRepository.findAll());
		model.addAttribute("student", student);
		return "portofoliu-create";
	}

	@PostMapping("/portofoliu-create-save")
	public String createSave(@RequestParam("studentId") int studentId,
							 @Validated Portofoliu portofoliu,
							 @RequestParam("tutoreEmail") String tutoreEmail,
							 BindingResult result,
							 Model model) {
		Student student = studentRepository.findById(studentId);
		if (student == null) {
			throw new RuntimeException("Student inexistent.");
		}

		if (result.hasErrors()) {
			System.out.println("Validation errors: " + result.getAllErrors());
			model.addAttribute("cadreDidactice", cadruDidacticRepository.findAll());
			model.addAttribute("student", student);
			return "portofoliu-create";
		}

		portofoliu.setStudent(student);

		if (portofoliu.getCadruDidactic() == null || portofoliu.getStudent() == null) {
			model.addAttribute("cadreDidactice", cadruDidacticRepository.findAll());
			model.addAttribute("studenti", studentRepository.findAll());
			model.addAttribute("error", "Trebuie să selectați un cadru didactic și un student.");
			return "portofoliu-create";
		}

		CadruDidactic cadru = cadruDidacticRepository.findById(portofoliu.getCadruDidactic().getId());
		if (cadru == null) {
			throw new RuntimeException("Cadru didactic inexistent");
		}

		portofoliu.setCadruDidactic(cadru);

		Tutore t = tutoreRepository.findByEmail(tutoreEmail);
		if (t == null) {
			portofoliu.setTutore(tutoreService.create(tutoreEmail));
		} else {
			portofoliu.setTutore(t);
		}

		portofoliuRepository.save(portofoliu);

		return "redirect:/student-portofoliu-read/" + student.getId();
	}


	@GetMapping("/portofoliu-read")
	public String read(Model model)
	{
	    model.addAttribute("portofolii", portofoliuRepository.findAll());
	    return "portofoliu-read";
	}

	@GetMapping("/student-portofoliu-read/{id}")
	public String readPortofoliuStudent(@PathVariable("id") int id, Model model)
	{
		Student student = studentRepository.findById(id);
		if(student == null)
		{
			throw new RuntimeException("Student ID not found");
		}
		List<Portofoliu> portfolios = portofoliuRepository.findAll();
		List<Portofoliu> portfolioStudents = new ArrayList<>();
		for(Portofoliu p : portfolios){
			if(p.getStudent().getId() == student.getId()){
				portfolioStudents.add(p);
			}
		}
		model.addAttribute("student", student);
		model.addAttribute("portofolii", portfolioStudents);
		return "student-portofolii-read";
	}

	@GetMapping("/cadru-portofoliu-read/{id}")
	public String readPortofoliuCadru(@PathVariable("id") int id, Model model)
	{
		CadruDidactic cadru = cadruDidacticRepository.findById(id);
		if(cadru == null)
		{
			throw new RuntimeException("Student ID not found");
		}
		List<Portofoliu> portfolios = portofoliuRepository.findAll();
		List<Portofoliu> portofolioProfs = new ArrayList<>();
		for(Portofoliu p : portfolios){
			if(p.getCadruDidactic().getId() == cadru.getId()){
				portofolioProfs.add(p);
			}
		}
		model.addAttribute("cadru", cadru);
		model.addAttribute("portofolii", portofolioProfs);
		return "cadru-portofoliu-read";
	}

	@GetMapping("/portofoliu-edit/{id}")
	public String edit(@PathVariable("id") int id, Model model)
	{
	    Portofoliu portofoliu = portofoliuRepository.findById(id);
	    model.addAttribute("portofoliu", portofoliu);

		model.addAttribute("cadreDidactice", cadruDidacticRepository.findAll());

	    return "portofoliu-update";
	}

	@PostMapping("/portofoliu-update/{id}")
	public String update(@PathVariable("id") int id,
						 @Validated Portofoliu portofoliu,
						 @RequestParam("tutoreEmail") String tutoreEmail,
						 BindingResult result,
						 Model model) {
		if (result.hasErrors()) {
			portofoliu.setId(id);
			return "portofoliu-update";
		}

		Portofoliu existingPortofoliu = portofoliuRepository.findById(id);

		portofoliu.setStudent(existingPortofoliu.getStudent());

		Tutore existingTutore = existingPortofoliu.getTutore();
		if (existingTutore == null) {
			portofoliu.setTutore(tutoreService.create(tutoreEmail));
		} else {
			portofoliu.setTutore(existingTutore);
		}

		CadruDidactic cadru = cadruDidacticRepository.findById(portofoliu.getCadruDidactic().getId());
		if (cadru == null) {
			model.addAttribute("error", "Cadru didactic nu a fost găsit.");
			model.addAttribute("cadreDidactice", cadruDidacticRepository.findAll());
			return "portofoliu-update";
		}
		portofoliu.setCadruDidactic(cadru);

		portofoliu.setId(id);
		portofoliuRepository.save(portofoliu);

		return "redirect:/portofoliu-read";
	}


	@GetMapping("/portofoliu-delete/{id}")
	public String delete(@PathVariable("id") int id, RedirectAttributes redirectAttributes)
	{
	    Portofoliu portofoliu = portofoliuRepository.findById(id);
		if (portofoliu == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "Portofoliul nu a fost găsit.");
			return "redirect:/portofoliu-read";
		}
		portofoliu.setStudent(null);
		portofoliu.setTutore(null);
		portofoliu.setCadruDidactic(null);
	    portofoliuRepository.delete(portofoliu);
	    return "redirect:/portofoliu-read";
	}

	@GetMapping("/portofoliu-view/{id}")
	public String viewPortofoliu(@PathVariable("id") int id, Model model) {
		Portofoliu portofoliu = portofoliuRepository.findById(id);

		if (portofoliu == null) {
			model.addAttribute("errorMessage", "Portofoliul nu a fost găsit.");
			return "redirect:/portofoliu-read";
		}

		model.addAttribute("portofoliuId", id);
		return "portofoliu-vizualizare";
	}

	@GetMapping("/portofoliu-generate-pdf/{id}")
	@ResponseBody
	public ResponseEntity<byte[]> generatePortofoliuPDF(@PathVariable("id") int id) {
		Portofoliu portofoliu = portofoliuRepository.findById(id);

		if (portofoliu == null) {
			return ResponseEntity.notFound().build();
		}

		byte[] pdfBytes = PdfGenerator.generatePortofoliuPdf(portofoliu);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);

		return ResponseEntity.ok()
				.headers(headers)
				.body(pdfBytes);
	}
}
