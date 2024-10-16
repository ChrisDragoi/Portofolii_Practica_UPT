package ro.upt.ac.portofolii.portofoliu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PortofoliuController
{
	@Autowired
	PortofoliuRepository portofoliuRepository;

	@GetMapping("/")
	public String root()
	{
		return "index";
	}
	
	@GetMapping("/conventie-create")
	public String create(Portofoliu portofoliu, Model model)
	{
		model.addAttribute("conventie", new Portofoliu());
		return "conventie-create";
	}

	@PostMapping("/conventie-create-save")
	public String createSave(@Validated Portofoliu portofoliu, BindingResult result, Model model)
	{
		if(result.hasErrors())
		{
			return "conventie-create";
		}
		
		portofoliuRepository.save(portofoliu);
		return "redirect:/conventie-read";
	}
	
	@GetMapping("/conventie-read")
	public String read(Model model) 
	{
	    model.addAttribute("conventii", portofoliuRepository.findAll());
	    return "conventie-read";
	}
	
	@GetMapping("/conventie-edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) 
	{
	    Portofoliu portofoliu = portofoliuRepository.findById(id);
	    //.orElseThrow(() -> new IllegalArgumentException("Invalid conventie Id:" + id));
	    	    
	    model.addAttribute("conventie", portofoliu);
	    return "conventie-update";
	}
	
	@PostMapping("/conventie-update/{id}")
	public String update(@PathVariable("id") int id, @Validated Portofoliu portofoliu, BindingResult result, Model model)
	{
	    if(result.hasErrors()) 
	    {
	        portofoliu.setId(id);
	        return "conventie-update";
	    }
	        
	    portofoliuRepository.save(portofoliu);
	    return "redirect:/conventie-read";
	}
	
	@GetMapping("/conventie-delete/{id}")
	public String delete(@PathVariable("id") int id, Model model) 
	{
	    Portofoliu portofoliu = portofoliuRepository.findById(id);
	    //.orElseThrow(() -> new IllegalArgumentException("Invalid conventie Id:" + id));
	    
	    portofoliuRepository.delete(portofoliu);
	    return "redirect:/conventie-read";
	}	
}
