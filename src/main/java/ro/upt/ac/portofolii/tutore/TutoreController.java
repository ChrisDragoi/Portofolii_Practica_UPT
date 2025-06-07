package ro.upt.ac.portofolii.tutore;

import org.springframework.beans.factory.annotation.Autowired;
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
import ro.upt.ac.portofolii.portofoliu.Portofoliu;
import ro.upt.ac.portofolii.portofoliu.PortofoliuRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class TutoreController
{
	@Autowired
	private TutoreRepository tutoreRepository;
    @Autowired
    private PortofoliuRepository portofoliuRepository;
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

		Tutore existingStudent = tutoreRepository.findById(id);

		tutore.setPassword(existingStudent.getPassword());
		tutore.setSemnatura(existingStudent.getSemnatura());
		tutore.setRole(existingStudent.getRole());
		
	    tutoreRepository.save(tutore);
	    return "redirect:/tutore/" + tutore.getId() + "/index";
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
				return "redirect:/tutore/" + id + "/index";
			}

			Path filePath = tutoreDir.resolve("signature.png");
			Files.write(filePath, file.getBytes());
			System.out.println("Semnătura a fost salvată la: " + filePath.toAbsolutePath());

			redirectAttributes.addFlashAttribute("success", "Semnătura a fost actualizată cu succes!");
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error", "Eroare la salvarea semnăturii!");
		}

		return "redirect:/tutore/" + id + "/index";
	}

	@PostMapping("/tutore/{tid}/sign/portofoliu/{id}")
	public String signTutore(@PathVariable int id, @PathVariable int tid, RedirectAttributes redirectAttributes) {
		Portofoliu portofoliu = portofoliuRepository.findById(id);
		if (portofoliu == null) {
			redirectAttributes.addFlashAttribute("error", "Portofoliul nu a fost găsit.");
			return "redirect:/tutore-portofoliu-read/" + tid;
		}

		String signaturePath = portofoliu.getTutore().getSemnatura() + "/signature.png";
		File signatureFile = new File(signaturePath);

		if (!signatureFile.exists()) {
			redirectAttributes.addFlashAttribute("signTutoreError", id);
			return "redirect:/tutore-portofoliu-read/" + tid;
		}

		portofoliu.setSemnaturaTutore(true);
		portofoliuRepository.save(portofoliu);
		redirectAttributes.addFlashAttribute("success", "Semnătura tutorelui a fost înregistrată.");
		return "redirect:/tutore-portofoliu-read/" + tid;
	}

	@GetMapping("tutore/{tid}/portofoliu-view/{pid}")
	public String viewPortofoliu(@PathVariable("tid") int tid, @PathVariable("pid") int pid, Model model) {
		Portofoliu portofoliu = portofoliuRepository.findById(pid);

		if (portofoliu == null) {
			model.addAttribute("errorMessage", "Portofoliul nu a fost găsit.");
			return "redirect:/cadru-portofoliu-read/" + tid;
		}

		Tutore tutore = portofoliu.getTutore();

		model.addAttribute("portofoliuId", pid);
		model.addAttribute("tutore", tutore);

		return "tutore-vizualizare-portofoliu";
	}
}
