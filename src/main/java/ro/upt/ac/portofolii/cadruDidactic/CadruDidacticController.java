package ro.upt.ac.portofolii.cadruDidactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ro.upt.ac.portofolii.admin.AdminService;
import ro.upt.ac.portofolii.security.Role;
import ro.upt.ac.portofolii.security.User;
import ro.upt.ac.portofolii.security.UserRepository;
import ro.upt.ac.portofolii.tutore.Tutore;

import java.io.File;
import java.io.IOException;
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
		User user=new User(cadruDidactic.getEmail(),"prof"+cadruDidactic.getId(), Role.CADRU_DIDACTIC);
		userRepository.save(user);
		makeSign(cadruDidacticRepository.save(cadruDidactic));

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
	    //.orElseThrow(() -> new IllegalArgumentException("Invalid cadruDidactic Id:" + id));

		Optional<User> user=userRepository.findByEmail(cadruDidactic.getEmail());
		if(user.isEmpty()){
			throw new RuntimeException("User not found");
		}
		User u = user.get();
		adminService.deleteProfFolder(cadruDidactic);
		userRepository.delete(u);
	    cadruDidacticRepository.delete(cadruDidactic);
	    return "redirect:/cadruDidactic-read";
	}

	private void makeSign(CadruDidactic cadruDidactic) {
		String baseDir = "cadreDidactice";

		File cadreDidacticeDir = new File(baseDir);
		if (!cadreDidacticeDir.exists()) {
			boolean created = cadreDidacticeDir.mkdir();
			if (created) {
				System.out.println("Directorul 'cadreDidactice' a fost creat anterior.");
			}
		}

		String profDirPath = baseDir + "/prof" + cadruDidactic.getId();
		File profDir = new File(profDirPath);
		if (!profDir.exists()) {
			boolean created = profDir.mkdir();
			if (created) {
				System.out.println("Directorul '" + profDirPath + "' a fost creat.");
			}
		}
		cadruDidactic.setSemnatura(profDirPath);
		cadruDidacticRepository.save(cadruDidactic);
	}
}
