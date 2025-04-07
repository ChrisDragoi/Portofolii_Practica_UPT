package ro.upt.ac.portofolii.cadruDidactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.upt.ac.portofolii.admin.AdminService;
import ro.upt.ac.portofolii.security.Role;
import ro.upt.ac.portofolii.security.User;
import ro.upt.ac.portofolii.security.UserRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class CadruDidacticController
{
	@Autowired
	private CadruDidacticRepository cadruDidacticRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AdminService adminService;
	@Autowired
	private CadruDidacticService cadruDidacticService;
	@Autowired
	private PasswordEncoder passwordEncoder;

    @GetMapping("/cadruDidactic-create")
	public String create( Model model)
	{
		model.addAttribute("cadruDidactic", new CadruDidactic());
		return "cadruDidactic-create";
	}

	@PostMapping("/cadruDidactic-create-save")
	public String createSave(@Validated CadruDidactic cadruDidactic, BindingResult result)
	{
		if(result.hasErrors())
		{
			return "cadruDidactic-create";
		}
		cadruDidacticService.makeSign(cadruDidacticRepository.save(cadruDidactic));

		return "redirect:/cadruDidactic-read";
	}

	@GetMapping("/cadruDidactic-read")
	public String read(Model model) 
	{
	    model.addAttribute("cadreDidactice", cadruDidacticRepository.findAll());
	    return "cadruDidactic-read";
	}
	
	@GetMapping("/cadruDidactic-edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) 
	{
	    CadruDidactic cadruDidactic = cadruDidacticRepository.findById(id);
	    //.orElseThrow(() -> new IllegalArgumentException("Invalid cadruDidactic Id:" + id));
	    
	    model.addAttribute("cadruDidactic", cadruDidactic);
	    return "cadruDidactic-update";
	}
	
	@PostMapping("/cadruDidactic-update/{id}")
	public String update(@PathVariable("id") int id, @Validated CadruDidactic cadruDidactic, BindingResult result)
	{
	    if(result.hasErrors()) 
	    {
	        cadruDidactic.setId(id);
	        return "cadruDidactic-update";
	    }
		CadruDidactic existingTutore = cadruDidacticRepository.findById(id);

		if (cadruDidactic.getSemnatura() == null) {
			cadruDidactic.setSemnatura(existingTutore.getSemnatura());
		}
	        
	    cadruDidacticRepository.save(cadruDidactic);
	    return "redirect:/cadruDidactic-read";
	}
	
	@GetMapping("/cadruDidactic-delete/{id}")
	public String delete(@PathVariable("id") int id) throws IOException {
	    CadruDidactic cadruDidactic = cadruDidacticRepository.findById(id);

		adminService.deleteProfFolder(cadruDidactic);
		cadruDidacticService.removeProfFromPortofolios(id);
	    cadruDidacticRepository.delete(cadruDidactic);
	    return "redirect:/cadruDidactic-read";
	}

	@PostMapping(value = "/cadruDidactic/{id}/upload-signature", consumes = {"multipart/form-data"})
	public String uploadSignature(@PathVariable int id,
								  @RequestParam("signature") MultipartFile file,
								  RedirectAttributes redirectAttributes) {
		try {
            String baseDir = "semnaturi/cadreDidactice";
            Path profDir = Paths.get(baseDir, "cadruDidactic" + id);
			if (!Files.exists(profDir)) {
				redirectAttributes.addFlashAttribute("error", "Folderul cadrului didactic nu există!");
				return "redirect:/cadruDidactic-read";
			}

			Path filePath = profDir.resolve("signature.png");
			Files.write(filePath, file.getBytes());
			System.out.println("Semnătura a fost salvată la: " + filePath.toAbsolutePath());

			redirectAttributes.addFlashAttribute("success", "Semnătura a fost actualizată cu succes!");
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error", "Eroare la salvarea semnăturii!");
		}

		return "redirect:/cadruDidactic-read";
	}
}
