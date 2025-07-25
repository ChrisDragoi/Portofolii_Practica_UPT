package ro.upt.ac.portofolii.security;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidactic;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidacticRepository;
import ro.upt.ac.portofolii.student.Student;
import ro.upt.ac.portofolii.student.StudentRepository;
import ro.upt.ac.portofolii.tutore.Tutore;
import ro.upt.ac.portofolii.tutore.TutoreRepository;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class LoginController {

    private final CadruDidacticRepository cadruDidacticRepository;
    private final StudentRepository studentRepository;
    private final TutoreRepository tutoreRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/user/change-password/{role}/{id}")
    public String showChangePasswordPage(@PathVariable("role") String role, @PathVariable("id") int id, Model model) {
        model.addAttribute("role", role);
        switch (role) {
            case "student":{
                Optional<Student> student = Optional.ofNullable(studentRepository.findById(id));

                if (student.isPresent()) {
                    model.addAttribute("user", student.get());
                    return "change-password";
                }
            break;
            }
            case "cadruDidactic":{
                    Optional<CadruDidactic> cadru = Optional.ofNullable(cadruDidacticRepository.findById(id));

                    if (cadru.isPresent()) {
                        model.addAttribute("user", cadru.get());
                        return "change-password";
                    }
            break;
            }
            case "tutore":{
                Optional<Tutore> tutore = Optional.ofNullable(tutoreRepository.findById(id));

                if (tutore.isPresent()) {
                    model.addAttribute("user", tutore.get());
                    return "change-password";
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid role");
        }

        return "redirect:/";
    }

    @PostMapping("/user/change-password/{role}/{id}")
    public String changePassword(
            @PathVariable("role") String role,
            @PathVariable("id") int id,
            @RequestParam("newPassword") String newPassword,
            RedirectAttributes redirectAttributes
    ) {
        Optional<User> user;

        switch (role) {
            case "student":{
                Student student = studentRepository.findById(id);
                user=userRepository.findByEmail(student.getEmail());
                break;
            }

            case "cadruDidactic":{
                CadruDidactic cadru = cadruDidacticRepository.findById(id);
                user=userRepository.findByEmail(cadru.getEmail());
                break;
            }

            case "tutore":{
                Tutore tutore = tutoreRepository.findById(id);
                user=userRepository.findByEmail(tutore.getEmail());
                break;
            }

            default:
                throw new IllegalArgumentException("Invalid role");
        }

        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }else {
            User u = user.get();
            u.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(u);
            redirectAttributes.addFlashAttribute("success", "Password updated successfully. Please login again.");
            return "redirect:/logout";
        }
    }

    @GetMapping("/student/{id}/index")
    @PreAuthorize("hasRole('STUDENT')")
    public String studentIndex(@PathVariable int id, Authentication auth, Model model) {
        String username = auth.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (user.getId() != id) {
            return "redirect:/access-denied";
        }

        Student student = studentRepository.findById(id);
        model.addAttribute("student", student);
        return "student-index";
    }

    @GetMapping("/cadru/{id}/index")
    @PreAuthorize("hasRole('CADRU_DIDACTIC')")
    public String cadruDidacticIndex(@PathVariable int id, Authentication auth, Model model) {
        String username = auth.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (user.getId() != id) {
            return "redirect:/access-denied";
        }

        CadruDidactic cadru = cadruDidacticRepository.findById(id);
        model.addAttribute("cadru", cadru);
        return "cadruDidactic-index";
    }

    @GetMapping("/tutore/{id}/index")
    @PreAuthorize("hasRole('TUTORE')")
    public String tutoreIndex(@PathVariable int id, Authentication auth, Model model) {
        String username = auth.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        if (user.getId() != id) {
            return "redirect:/access-denied";
        }

        Tutore tutore = tutoreRepository.findById(id);
        if(tutore.getNume() == null && tutore.getPrenume() == null)
            return "redirect:/tutore-edit/" + tutore.getId();
        else {
            model.addAttribute("tutore", tutore);
            return "tutore-index";
        }
    }

    @GetMapping("/access-denied")
    public String accessDenied(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("error", error);
        return "access-denied";
    }
}
