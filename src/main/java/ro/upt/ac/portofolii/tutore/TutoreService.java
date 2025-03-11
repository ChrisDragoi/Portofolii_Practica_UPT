package ro.upt.ac.portofolii.tutore;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.upt.ac.portofolii.security.Role;
import ro.upt.ac.portofolii.security.User;
import ro.upt.ac.portofolii.security.UserRepository;
import ro.upt.ac.portofolii.utils.PasswordGenerator;

@Service
public class TutoreService {
    private final TutoreRepository tutoreRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TutoreService(TutoreRepository tutoreRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.tutoreRepository = tutoreRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Tutore create(String tutoreEmail) {
        Tutore tutore = new Tutore();
        tutore.setEmail(tutoreEmail);
        User userTutore = new User(tutoreEmail, passwordEncoder.encode(new PasswordGenerator().generateRandomPassword()), Role.TUTORE);
        userRepository.save(userTutore);
        return tutoreRepository.save(tutore);
    }
}
