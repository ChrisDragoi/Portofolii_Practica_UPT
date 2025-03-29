package ro.upt.ac.portofolii.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidactic;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidacticRepository;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidacticService;
import ro.upt.ac.portofolii.security.Role;
import ro.upt.ac.portofolii.security.User;
import ro.upt.ac.portofolii.security.UserRepository;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

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
                User admin = new User("prodecan@upt.ro", passwordEncoder.encode("admin123"), Role.ADMIN);
                //User student = new User("student@upt.ro", passwordEncoder.encode("student123"), Role.STUDENT);
                //User tutor = new User("tutore@upt.ro", passwordEncoder.encode("tutor123"), Role.TUTOR);
                User professor1 = new User("doru.todinca@upt.ro", passwordEncoder.encode("profesor1"), Role.CADRU_DIDACTIC);
                User professor2 = new User("cosmin.cernazanu@upt.ro", passwordEncoder.encode("profesor2"), Role.CADRU_DIDACTIC);
                User professor3 = new User("sorin.nanu@upt.ro", passwordEncoder.encode("profesor3"), Role.CADRU_DIDACTIC);
                User professor4 = new User("iosif.szeidert@upt.ro", passwordEncoder.encode("profesor4"), Role.CADRU_DIDACTIC);
                User professor5 = new User("mihaela.crisan-vida@upt.ro", passwordEncoder.encode("profesor5"), Role.CADRU_DIDACTIC);

                userRepository.saveAll(List.of(admin, professor1, professor2, professor3, professor4, professor5));
                addProfessors();

                System.out.println("Utilizatorii au fost creați în baza de date!");
            } else {
                System.out.println("Utilizatorii există deja, nu se mai creează.");
            }
            String semnaturiPath = Paths.get("semnaturi").toRealPath().toString();
            System.out.println("Folderul 'semnaturi' este expus la /semnaturi/** din: " + semnaturiPath);
        };
    }
    private void addProfessors(){
        CadruDidactic cd1=new CadruDidactic();
        cd1.setNume("Todinca");
        cd1.setPrenume("Doru");
        cd1.setFunctie("conferentiar");
        cd1.setTelefon("0700123456");
        cd1.setEmail("doru.todinca@upt.ro");
        cd1.setSpecializare("cti-ro");
        //cd1.setSemnatura("Todinca");
        cadruDidacticService.makeSign(cadruDidacticRepository.save(cd1));

        CadruDidactic cd2=new CadruDidactic();
        cd2.setNume("Cernazanu");
        cd2.setPrenume("Cosmin");
        cd2.setFunctie("conferentiar");
        cd2.setTelefon("0700123456");
        cd2.setEmail("cosmin.cernazanu@upt.ro");
        cd2.setSpecializare("cti-en");
        //cd2.setSemnatura("Cernazanu");
        cadruDidacticService.makeSign(cadruDidacticRepository.save(cd2));

        CadruDidactic cd3=new CadruDidactic();
        cd3.setNume("Nanu");
        cd3.setPrenume("Sorin");
        cd3.setFunctie("sef de lucrari");
        cd3.setTelefon("0700123456");
        cd3.setEmail("sorin.nanu@upt.ro");
        cd3.setSpecializare("is");
        //cd3.setSemnatura("Nanu");
        cadruDidacticService.makeSign(cadruDidacticRepository.save(cd3));

        CadruDidactic cd4=new CadruDidactic();
        cd4.setNume("Szeidert");
        cd4.setPrenume("Iosif");
        cd4.setFunctie("conferentiar");
        cd4.setTelefon("0700123456");
        cd4.setEmail("iosif.szeidert@upt.ro");
        cd4.setSpecializare("info-zi");
        //cd4.setSemnatura("Szeidert");
        cadruDidacticService.makeSign(cadruDidacticRepository.save(cd4));

        CadruDidactic cd5=new CadruDidactic();
        cd5.setNume("Crisan-Vida");
        cd5.setPrenume("Mihaela");
        cd5.setFunctie("sef de lucrari");
        cd5.setTelefon("0700123456");
        cd5.setEmail("mihaela.crisan-vida@upt.ro");
        cd5.setSpecializare("info-id");
        //cd5.setSemnatura("Crisan-Vida");
        cadruDidacticService.makeSign(cadruDidacticRepository.save(cd5));
    }

}
