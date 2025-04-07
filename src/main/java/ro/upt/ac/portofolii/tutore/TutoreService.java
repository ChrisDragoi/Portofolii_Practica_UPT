package ro.upt.ac.portofolii.tutore;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.upt.ac.portofolii.portofoliu.Portofoliu;
import ro.upt.ac.portofolii.portofoliu.PortofoliuRepository;
import java.io.File;
import java.util.List;

@Service
public class TutoreService {
    private final TutoreRepository tutoreRepository;
    private final PasswordEncoder passwordEncoder;
    private final PortofoliuRepository portofoliuRepository;

    public TutoreService(TutoreRepository tutoreRepository, PasswordEncoder passwordEncoder, PortofoliuRepository portofoliuRepository) {
        this.tutoreRepository = tutoreRepository;
        this.passwordEncoder = passwordEncoder;
        this.portofoliuRepository = portofoliuRepository;
    }

    public Tutore create(String tutoreEmail) {
        Tutore tutore = new Tutore();
        tutore.setEmail(tutoreEmail);
        return makeSign(tutoreRepository.save(tutore));
    }

    public Tutore makeSign(Tutore tutore) {
        String baseDir = "semnaturi/tutori/tutore" + tutore.getId();
        File tutoriDir = new File(baseDir);

        if (!tutoriDir.exists()) {
            boolean created = tutoriDir.mkdirs();
            if (created) {
                System.out.println("Directorul 'tutori/tutore" + tutore.getId() + "' a fost creat.");
            } else {
                System.out.println("Eroare la crearea directorului 'tutori/tutore" + tutore.getId() + "'.");
            }
        }

        tutore.setSemnatura(baseDir);
        tutore.setPassword(passwordEncoder.encode("tutore"+tutore.getId()));

        return tutoreRepository.save(tutore);
    }

    public void removeTutoreFromPortofolios(int id) {
        List<Portofoliu> portofolii = portofoliuRepository.findAll();
        for (Portofoliu portofoliu : portofolii) {
            Tutore tutore = portofoliu.getTutore();
            if (tutore != null && tutore.getId() == id) {
                portofoliu.setTutore(null);
                portofoliuRepository.save(portofoliu);
            }
        }
    }
}
