package ro.upt.ac.portofolii.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.upt.ac.portofolii.admin.Admin;
import ro.upt.ac.portofolii.admin.AdminService;
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
    public CommandLineRunner initUsers(AdminService adminService) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = Admin.adminBuilder()
                        .email("prodecan@upt.ro")
                        .password(passwordEncoder.encode("admin123"))
                        .nume("Chirila")
                        .prenume("Ciprian")
                        .functie("Prodecan")
                        .specializare("cti-ro")
                        .telefon("0700123456")
                        .build();
                //userRepository.save(admin);
                adminService.makeAdminSign((Admin) userRepository.save(admin));

                CadruDidactic professor1 = CadruDidactic.builder()
                        .email("doru.todinca@upt.ro")
                        .role(Role.CADRU_DIDACTIC)
                        .nume("Todinca")
                        .prenume("Doru")
                        .functie("conferentiar")
                        .specializare("cti-ro")
                        .telefon("0700123456")
                        .build();
                cadruDidacticService.makeSign(cadruDidacticRepository.save(professor1));

                CadruDidactic professor2 = CadruDidactic.builder()
                        .email("cosmin.cernazanu@upt.ro")
                        .role(Role.CADRU_DIDACTIC)
                        .nume("Cernazanu")
                        .prenume("Cosmin")
                        .functie("conferentiar")
                        .specializare("cti-en")
                        .telefon("0700123456")
                        .build();
                cadruDidacticService.makeSign(cadruDidacticRepository.save(professor2));

                CadruDidactic professor3 = CadruDidactic.builder()
                        .email("sorin.nanu@upt.ro")
                        .role(Role.CADRU_DIDACTIC)
                        .nume("Nanu")
                        .prenume("Sorin")
                        .functie("sef de lucrari")
                        .specializare("is")
                        .telefon("0700123456")
                        .build();
                cadruDidacticService.makeSign(cadruDidacticRepository.save(professor3));

                CadruDidactic professor4 = CadruDidactic.builder()
                        .email("iosif.szeidert@upt.ro")
                        .role(Role.CADRU_DIDACTIC)
                        .nume("Szeidert")
                        .prenume("Iosif")
                        .functie("conferentiar")
                        .specializare("info-zi")
                        .telefon("0700123456")
                        .build();
                cadruDidacticService.makeSign(cadruDidacticRepository.save(professor4));

                CadruDidactic professor5 = CadruDidactic.builder()
                        .email("mihaela.crisan-vida@upt.ro")
                        .role(Role.CADRU_DIDACTIC)
                        .nume("Crisan-Vida")
                        .prenume("Mihaela")
                        .functie("sef de lucrari")
                        .specializare("info-id")
                        .telefon("0700123456")
                        .build();
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
