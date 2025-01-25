package ro.upt.ac.portofolii.portofoliu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidactic;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidacticRepository;
import ro.upt.ac.portofolii.student.StudentRepository;
import ro.upt.ac.portofolii.tutore.Tutore;
import ro.upt.ac.portofolii.tutore.TutoreRepository;

@Controller
public class PortofoliuController
{
	@Autowired
	PortofoliuRepository portofoliuRepository;

	@Autowired
	CadruDidacticRepository cadruDidacticRepository;

    @Autowired
    private StudentRepository studentRepository;
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
		model.addAttribute("portofoliu", new Portofoliu());
		model.addAttribute("cadreDidactice", cadruDidacticRepository.findAll());
		return "portofoliu-create";
	}

	@PostMapping("/portofoliu-create-save")
	public String createSave(@Validated Portofoliu portofoliu, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.out.println("Validation errors: " + result.getAllErrors());
			return "portofoliu-create";
		}

		if (portofoliu.getCadruDidactic() == null) {
			System.out.println("CadruDidactic ID is missing or null");
			model.addAttribute("cadreDidactice", cadruDidacticRepository.findAll());
			model.addAttribute("error", "Trebuie să selectați un cadru didactic.");
			return "portofoliu-create";
		}

		// Asociem cadrul didactic la portofoliu
		CadruDidactic cadru = cadruDidacticRepository.findById(portofoliu.getCadruDidactic().getId());
		if(cadru == null) {
			throw new RuntimeException("Cadru didactic ID not found");
		}
		portofoliu.setCadruDidactic(cadru);

		//Tutore tutore = tutoreRepository.findByEmail(portofoliu.getTutore().getEmail());
		portofoliu.setTutore(tutoreRepository.findById(3));

		portofoliu.setStudent(studentRepository.findById(1));

		// Salvăm portofoliul
		portofoliuRepository.save(portofoliu);

		// Redirecționăm către pagina de afișare a portofoliilor
		return "redirect:/portofoliu-read";
	}

	@GetMapping("/portofoliu-read")
	public String read(Model model) 
	{
	    model.addAttribute("portofolii", portofoliuRepository.findAll());
	    return "portofoliu-read";
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
	public String update(@PathVariable("id") int id, @Validated Portofoliu portofoliu, BindingResult result, Model model)
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
	public String delete(@PathVariable("id") int id, Model model) 
	{
	    Portofoliu portofoliu = portofoliuRepository.findById(id);
	    //.orElseThrow(() -> new IllegalArgumentException("Invalid portofoliu Id:" + id));
	    
	    portofoliuRepository.delete(portofoliu);
	    return "redirect:/portofoliu-read";
	}	
}
