package ro.upt.ac.portofolii.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.upt.ac.portofolii.security.Role;
import ro.upt.ac.portofolii.security.User;
import ro.upt.ac.portofolii.security.UserRepository;

import java.util.List;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initUsers() {
        return args -> {
            if (userRepository.count() == 0) { // Dacă nu există utilizatori, îi adăugăm
                User admin = new User("prodecan@upt.ro", passwordEncoder.encode("admin123"), Role.ADMIN);
                User student = new User("student@upt.ro", passwordEncoder.encode("student123"), Role.STUDENT);
                User tutor = new User("tutore@upt.ro", passwordEncoder.encode("tutor123"), Role.TUTOR);
                User professor = new User("cadru@upt.ro", passwordEncoder.encode("profesor123"), Role.CADRU_DIDACTIC);

                userRepository.saveAll(List.of(admin, student, tutor, professor));

                System.out.println("Utilizatorii au fost creați în baza de date!");
            } else {
                System.out.println("Utilizatorii există deja, nu se mai creează.");
            }
        };
    }
}
