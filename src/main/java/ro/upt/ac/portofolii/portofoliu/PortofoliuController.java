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
import ro.upt.ac.portofolii.cadruDidactic.CadruDidactic;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidacticRepository;
import ro.upt.ac.portofolii.student.Student;
import ro.upt.ac.portofolii.student.StudentRepository;
import ro.upt.ac.portofolii.tutore.Tutore;
import ro.upt.ac.portofolii.tutore.TutoreRepository;
import ro.upt.ac.portofolii.tutore.TutoreService;
import ro.upt.ac.portofolii.utils.PdfGenerator;
import ro.upt.ac.portofolii.utils.WordGenerator;

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
	public String root()
	{
		return "index";
	}
	
	@GetMapping("/portofoliu-create")
	public String create(Portofoliu portofoliu, Model model)
	{
		model.addAttribute("portofoliu", portofoliu);
		model.addAttribute("cadreDidactice", cadruDidacticRepository.findAll());
		model.addAttribute("studenti", studentRepository.findAll());
		return "portofoliu-create";
	}

	@PostMapping("/portofoliu-create-save")
	public String createSave(@Validated Portofoliu portofoliu, @RequestParam("tutoreEmail") String tutoreEmail, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.out.println("Validation errors: " + result.getAllErrors());
			return "portofoliu-create";
		}

		if (portofoliu.getCadruDidactic() == null) 
		{
			System.out.println("CadruDidactic ID is missing or null");
			model.addAttribute("cadreDidactice", cadruDidacticRepository.findAll());
			model.addAttribute("error", "Trebuie să selectați un cadru didactic.");
			return "portofoliu-create";
		}

		if (portofoliu.getStudent() == null)
		{
			System.out.println("CadruDidactic ID is missing or null");
			model.addAttribute("studenti", studentRepository.findAll());
			model.addAttribute("error", "Trebuie sa selectați un student.");
			return "portofoliu-create";
		}

		CadruDidactic cadru = cadruDidacticRepository.findById(portofoliu.getCadruDidactic().getId());
		if(cadru == null) 
		{
			throw new RuntimeException("Cadru didactic ID not found");
		}
		portofoliu.setCadruDidactic(cadru);

		Student student = studentRepository.findById(portofoliu.getStudent().getId());
		if(student == null)
		{
			throw new RuntimeException("Student ID not found");
		}
		portofoliu.setStudent(student);

		Tutore t = tutoreRepository.findByEmail(tutoreEmail);
		if(t == null){
			Tutore tutore = tutoreService.create(tutoreEmail);
			portofoliu.setTutore(tutore);
		}else{
			portofoliu.setTutore(t);
		}

		portofoliuRepository.save(portofoliu);

		return "redirect:/portofoliu-read";
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

	@GetMapping("/portofoliu-edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) 
	{
	    Portofoliu portofoliu = portofoliuRepository.findById(id);
	    model.addAttribute("portofoliu", portofoliu);

		model.addAttribute("cadreDidactice", cadruDidacticRepository.findAll());
	    return "portofoliu-update";
	}
	
	@PostMapping("/portofoliu-update/{id}")
	public String update(@PathVariable("id") int id, @Validated Portofoliu portofoliu, BindingResult result)
	{
	    if(result.hasErrors()) 
	    {
	        portofoliu.setId(id);
	        return "portofoliu-update";
	    }
	        
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

	@GetMapping("/portofoliu-generate-docx/{id}")
	@ResponseBody
	public ResponseEntity<byte[]> generatePortofoliuDocx(@PathVariable("id") int id) {
		Portofoliu portofoliu = portofoliuRepository.findById(id);

		if (portofoliu == null) {
			return ResponseEntity.notFound().build();
		}

		byte[] pdfBytes = WordGenerator.generatePortofoliuDocx(portofoliu);

        HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);

		return ResponseEntity.ok()
				.headers(headers)
				.body(pdfBytes);
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
