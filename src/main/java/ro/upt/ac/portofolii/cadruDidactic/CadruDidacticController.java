package ro.upt.ac.portofolii.cadruDidactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import java.sql.Date;
import java.time.LocalDate;

@Controller
public class CadruDidacticController
{
	@Autowired
	private CadruDidacticRepository cadruDidacticRepository;
	@Autowired
	private AdminService adminService;
	@Autowired
	private CadruDidacticService cadruDidacticService;
	@Autowired
	private PortofoliuRepository portofoliuRepository;

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
	    
	    model.addAttribute("cadruDidactic", cadruDidactic);
	    return "cadruDidactic-update";
	}

	@PostMapping("/cadruDidactic-update/{id}")
	public String update(@PathVariable("id") int id,
						 @Validated CadruDidactic formData,
						 BindingResult result,
						 Authentication auth) {

		if (result.hasErrors()) {
			formData.setId(id);
			return "cadruDidactic-update" + id;
		}

		CadruDidactic existing = cadruDidacticRepository.findById(id);
		if (existing == null) {
			return "redirect:cadruDidactic-update" + id;
		}

		existing.setNume(formData.getNume());
		existing.setPrenume(formData.getPrenume());
		existing.setTelefon(formData.getTelefon());
		existing.setEmail(formData.getEmail());
		existing.setFunctie(formData.getFunctie());
		existing.setSpecializare(formData.getSpecializare());

		cadruDidacticRepository.save(existing);

		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			return "redirect:/";
		}

		return "redirect:/cadru/" + id + "/index";
	}


	@GetMapping("/cadruDidactic-delete/{id}")
	public String delete(@PathVariable("id") int id, Authentication auth) throws IOException {
	    CadruDidactic cadruDidactic = cadruDidacticRepository.findById(id);

		adminService.deleteProfFolder(cadruDidactic);
		cadruDidacticService.removeProfFromPortofolios(id);
	    cadruDidacticRepository.delete(cadruDidactic);
		if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) return "redirect:/";
	    return "redirect:/cadru/" + id + "/index";
	}

	@PostMapping(value = "/cadruDidactic/{id}/upload-signature", consumes = {"multipart/form-data"})
	public String uploadSignature(@PathVariable int id,
								  @RequestParam("signature") MultipartFile file,
								  Authentication auth,
								  RedirectAttributes redirectAttributes) {
		try {
			CadruDidactic cadruDidactic = cadruDidacticRepository.findById(id);
			if(cadruDidactic.getSemnatura() == null) {
				if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) return "redirect:/";
				return "redirect:/cadru/" + id + "/index";
			}
            String baseDir = cadruDidactic.getSemnatura();
            Path profDir = Paths.get(baseDir);
			if (!Files.exists(profDir)) {
				redirectAttributes.addFlashAttribute("error", "Folderul cadrului didactic nu există!");
				if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) return "redirect:/";
				return "redirect:/cadru/" + id + "/index";
			}

			Path filePath = profDir.resolve("signature.png");
			Files.write(filePath, file.getBytes());
			System.out.println("Semnătura a fost salvată la: " + filePath.toAbsolutePath());

			redirectAttributes.addFlashAttribute("success", "Semnătura a fost actualizată cu succes!");
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error", "Eroare la salvarea semnăturii!");
		}
		if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) return "redirect:/";
		return "redirect:/cadru/" + id + "/index";
	}

	@PostMapping("cadruDidactic/sign/portofoliu/{id}")
	public String signCadru(@PathVariable int id,
							@RequestParam(value = "cid", required = false) Integer cid,
							RedirectAttributes redirectAttributes) {
		Portofoliu portofoliu = portofoliuRepository.findById(id);
		if (portofoliu == null) {
			redirectAttributes.addFlashAttribute("error", "Portofoliul nu a fost găsit.");
			return redirectTo(cid);
		}

		String signaturePath = portofoliu.getCadruDidactic().getSemnatura() + "/signature.png";
		File signatureFile = new File(signaturePath);

		if (!signatureFile.exists()) {
			redirectAttributes.addFlashAttribute("signCadruError", id);
			return redirectTo(cid);
		}
		portofoliu.setDataSemnarii(Date.valueOf(LocalDate.now()));
		portofoliu.setSemnaturaCadruDidactic(true);

		portofoliuRepository.save(portofoliu);
		redirectAttributes.addFlashAttribute("success", "Semnătura cadrului didactic a fost înregistrată.");
		return redirectTo(cid);
	}

	private String redirectTo(Integer cid) {
		if (cid != null) {
			return "redirect:/cadru-portofoliu-read/" + cid;
		}
		return "redirect:/portofoliu-read";
	}

	@GetMapping("cadru/{cid}/portofoliu-view/{pid}")
	public String viewPortofoliu(@PathVariable("cid") int cid, @PathVariable("pid") int pid, Model model) {
		Portofoliu portofoliu = portofoliuRepository.findById(pid);

		if (portofoliu == null) {
			model.addAttribute("errorMessage", "Portofoliul nu a fost găsit.");
			return "redirect:/cadru-portofoliu-read/" + cid;
		}

		CadruDidactic prof = portofoliu.getCadruDidactic();

		model.addAttribute("portofoliuId", pid);
		model.addAttribute("cadru", prof);

		return "cadru-vizualizare-portofoliu";
	}

}
