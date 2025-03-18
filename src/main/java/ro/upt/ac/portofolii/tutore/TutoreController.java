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
import ro.upt.ac.portofolii.admin.AdminService;
import ro.upt.ac.portofolii.security.Role;
import ro.upt.ac.portofolii.security.User;
import ro.upt.ac.portofolii.security.UserRepository;

import java.io.IOException;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

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
		User u = new User(tutore.getEmail(), passwordEncoder.encode("tutore"+tutore.getId()), Role.TUTORE);
		userRepository.save(u);
		tutoreService.addTutore(tutore);

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
		Tutore existingTutore = tutoreRepository.findById(id);

		if (tutore.getSemnatura() == null) {
			tutore.setSemnatura(existingTutore.getSemnatura());
		}
	        
	    tutoreRepository.save(tutore);
	    return "redirect:/tutore-read";
	}
	
	@GetMapping("/tutore-delete/{id}")
	public String delete(@PathVariable("id") int id) throws IOException {
	    Tutore tutore = tutoreRepository.findById(id);
	    //.orElseThrow(() -> new IllegalArgumentException("Invalid tutore Id:" + id));
		Optional<User> user=userRepository.findByEmail(tutore.getEmail());
		if(user.isEmpty()){
			throw new RuntimeException("User not found");
		}
		User u = user.get();
		adminService.deleteTutoreFolder(tutore);
		userRepository.delete(u);
	    tutoreRepository.delete(tutore);
	    return "redirect:/tutore-read";
	}

}
