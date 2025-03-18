package ro.upt.ac.portofolii.tutore;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.upt.ac.portofolii.security.Role;
import ro.upt.ac.portofolii.security.User;
import ro.upt.ac.portofolii.security.UserRepository;
import ro.upt.ac.portofolii.utils.PasswordGenerator;

import java.io.File;

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

        return makeSign(tutoreRepository.save(tutore));
    }
    public void addTutore(Tutore tutore) {
        makeSign(tutoreRepository.save(tutore));
    }
    public Tutore makeSign(Tutore tutore) {
        String baseDir = "tutori";

        File tutoriDir = new File(baseDir);
        if (!tutoriDir.exists()) {
            boolean created = tutoriDir.mkdir();
            if (created) {
                System.out.println("Directorul 'tutori' a fost creat anterior.");
            }
        }

        String tutoreDirPath = baseDir + "/tutore" + tutore.getId();
        File profDir = new File(tutoreDirPath);
        if (!profDir.exists()) {
            boolean created = profDir.mkdir();
            if (created) {
                System.out.println("Directorul '" + tutoreDirPath + "' a fost creat.");
            }
        }
        tutore.setSemnatura(tutoreDirPath);
        return tutoreRepository.save(tutore);
    }
}
