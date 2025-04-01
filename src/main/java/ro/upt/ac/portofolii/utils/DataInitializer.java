package ro.upt.ac.portofolii.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.upt.ac.portofolii.admin.Admin;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidactic;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidacticRepository;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidacticService;
import ro.upt.ac.portofolii.security.Role;
import ro.upt.ac.portofolii.security.User;
import ro.upt.ac.portofolii.security.UserRepository;
import java.nio.file.Paths;


@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final CadruDidacticRepository cadruDidacticRepository;
    private final PasswordEncoder passwordEncoder;
    private final CadruDidacticService cadruDidacticService;

    public DataInitializer(UserRepository userRepository, CadruDidacticRepository cadruDidacticRepository, PasswordEncoder passwordEncoder, CadruDidacticService cadruDidacticService) {
        this.userRepository = userRepository;
        this.cadruDidacticRepository = cadruDidacticRepository;
        this.passwordEncoder = passwordEncoder;
        this.cadruDidacticService = cadruDidacticService;
    }

    @Bean
    public CommandLineRunner initUsers() {
        return args -> {
            if (userRepository.count() == 0) { // Dacă nu există utilizatori, îi adăugăm
                User admin = Admin.builder().email("prodecan@upt.ro").password(passwordEncoder.encode("admin123")).role(Role.ADMIN).build();
                userRepository.save(admin);

                CadruDidactic professor1 = CadruDidactic.builder()
                        .email("doru.todinca@upt.ro")
                        .password(passwordEncoder.encode("profesor1"))
                        .role(Role.CADRU_DIDACTIC)
                        .build();
                professor1.setNume("Todinca");
                professor1.setPrenume("Doru");
                professor1.setFunctie("conferentiar");
                professor1.setTelefon("0700123456");
                professor1.setSpecializare("cti-ro");
                cadruDidacticService.makeSign(cadruDidacticRepository.save(professor1));

                CadruDidactic professor2 = CadruDidactic.builder()
                        .email("cosmin.cernazanu@upt.ro")
                        .password(passwordEncoder.encode("profesor2"))
                        .role(Role.CADRU_DIDACTIC)
                        .build();
                professor2.setNume("Cernazanu");
                professor2.setPrenume("Cosmin");
                professor2.setFunctie("conferentiar");
                professor2.setTelefon("0700123456");
                professor2.setSpecializare("cti-en");
                cadruDidacticService.makeSign(cadruDidacticRepository.save(professor2));

                CadruDidactic professor3 = CadruDidactic.builder()
                        .email("sorin.nanu@upt.ro")
                        .password(passwordEncoder.encode("profesor3"))
                        .role(Role.CADRU_DIDACTIC)
                        .build();
                professor3.setNume("Nanu");
                professor3.setPrenume("Sorin");
                professor3.setFunctie("sef de lucrari");
                professor3.setTelefon("0700123456");
                professor3.setSpecializare("is");
                cadruDidacticService.makeSign(cadruDidacticRepository.save(professor3));

                CadruDidactic professor4 = CadruDidactic.builder()
                        .email("iosif.szeidert@upt.ro")
                        .password(passwordEncoder.encode("profesor4"))
                        .role(Role.CADRU_DIDACTIC)
                        .build();
                professor4.setNume("Szeidert");
                professor4.setPrenume("Iosif");
                professor4.setFunctie("conferentiar");
                professor4.setTelefon("0700123456");
                professor4.setSpecializare("info-zi");
                cadruDidacticService.makeSign(cadruDidacticRepository.save(professor4));

                CadruDidactic professor5 = CadruDidactic.builder()
                        .email("mihaela.crisan-vida@upt.ro")
                        .password(passwordEncoder.encode("profesor5"))
                        .role(Role.CADRU_DIDACTIC)
                        .build();
                professor5.setNume("Crisan-Vida");
                professor5.setPrenume("Mihaela");
                professor5.setFunctie("sef de lucrari");
                professor5.setTelefon("0700123456");
                professor5.setSpecializare("info-id");
                cadruDidacticService.makeSign(cadruDidacticRepository.save(professor5));

                System.out.println("Utilizatorii au fost creați în baza de date!");
            } else {
                System.out.println("Utilizatorii există deja, nu se mai creează.");
            }
            String semnaturiPath = Paths.get("semnaturi").toRealPath().toString();
            System.out.println("Folderul 'semnaturi' este expus la /semnaturi/** din: " + semnaturiPath);
        };
    }

}
