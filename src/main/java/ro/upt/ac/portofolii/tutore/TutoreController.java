package ro.upt.ac.portofolii.tutore;

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
import ro.upt.ac.portofolii.security.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class TutoreController
{
	@Autowired
	private TutoreRepository tutoreRepository;
    @Autowired
    private UserRepository userRepository;
	@Autowired
	private AdminService adminService;
    @Autowired
    private TutoreService tutoreService;

	@GetMapping("/tutore-create")
	public String create(Model model)
	{
		model.addAttribute("tutore", new Tutore());
		return "tutore-create";
	}

	@PostMapping("/tutore-create-save")
	public String createSave(@Validated Tutore tutore, BindingResult result)
	{
		if(result.hasErrors())
		{
			return "tutore-create";
		}

		tutoreService.makeSign(tutoreRepository.save(tutore));

		return "redirect:/tutore-read";
	}

	@GetMapping("/tutore-read")
	public String read(Model model)
	{
	    model.addAttribute("tutori", tutoreRepository.findAll());
	    return "tutore-read";
	}

	@GetMapping("/tutore-edit/{id}")
	public String edit(@PathVariable("id") int id, Model model)
	{
	    Tutore tutore = tutoreRepository.findById(id);
	    //.orElseThrow(() -> new IllegalArgumentException("Invalid tutore Id:" + id));

	    model.addAttribute("tutore", tutore);
	    return "tutore-update";
	}

	@PostMapping("/tutore-update/{id}")
	public String update(@PathVariable("id") int id, @Validated Tutore tutore, BindingResult result)
	{
	    if(result.hasErrors())
	    {
	        tutore.setId(id);
	        return "tutore-update";
	    }
	    tutoreRepository.save(tutore);
	    return "redirect:/tutore-read";
	}

	@GetMapping("/tutore-delete/{id}")
	public String delete(@PathVariable("id") int id) throws IOException {
	    Tutore tutore = tutoreRepository.findById(id);
	    //.orElseThrow(() -> new IllegalArgumentException("Invalid tutore Id:" + id));
		adminService.deleteTutoreFolder(tutore);
		tutoreService.removeTutoreFromPortofolios(id);
	    tutoreRepository.delete(tutore);
	    return "redirect:/tutore-read";
	}

	@PostMapping(value = "/tutore/{id}/upload-signature", consumes = {"multipart/form-data"})
	public String uploadSignature(@PathVariable int id,
								  @RequestParam("signature") MultipartFile file,
								  RedirectAttributes redirectAttributes) {
		try {
			String baseDir = "semnaturi/tutori";
			Path tutoreDir = Paths.get(baseDir, "tutore" + id);
			if (!Files.exists(tutoreDir)) {
				redirectAttributes.addFlashAttribute("error", "Folderul tutorelui nu există!");
				return "redirect:/tutore-read";
			}

			Path filePath = tutoreDir.resolve("signature.png");
			Files.write(filePath, file.getBytes());
			System.out.println("Semnătura a fost salvată la: " + filePath.toAbsolutePath());

			redirectAttributes.addFlashAttribute("success", "Semnătura a fost actualizată cu succes!");
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error", "Eroare la salvarea semnăturii!");
		}

		return "redirect:/tutore-read";
	}

}
