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
	
	@GetMapping("/portofoliu-create")
	public String create(Portofoliu portofoliu, Model model)
	{
		model.addAttribute("portofoliu", new Portofoliu());
		return "portofoliu-create";
	}

	@PostMapping("/portofoliu-create-save")
	public String createSave(@Validated Portofoliu portofoliu, BindingResult result, Model model)
	{
		if(result.hasErrors())
		{
			return "portofoliu-create";
		}
		
		portofoliuRepository.save(portofoliu);
		return "redirect:/portofoliu-read";
	}
	
	@GetMapping("/portofoliou-read")
	public String read(Model model) 
	{
	    model.addAttribute("portofolii", portofoliuRepository.findAll());
	    return "portofoliu-read";
	}
	
	@GetMapping("/portofoliu-edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) 
	{
	    Portofoliu portofoliu = portofoliuRepository.findById(id);
	    //.orElseThrow(() -> new IllegalArgumentException("Invalid conventie Id:" + id));
	    	    
	    model.addAttribute("portofoliu", portofoliu);
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
