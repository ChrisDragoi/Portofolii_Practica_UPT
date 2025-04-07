package ro.upt.ac.portofolii.cadruDidactic;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.upt.ac.portofolii.portofoliu.Portofoliu;
import ro.upt.ac.portofolii.portofoliu.PortofoliuRepository;

import java.io.File;
import java.util.List;

@Service
public class CadruDidacticService {

    private final CadruDidacticRepository cadruDidacticRepository;
    private final PasswordEncoder passwordEncoder;
    private final PortofoliuRepository portofoliuRepository;

    public CadruDidacticService(CadruDidacticRepository cadruDidacticRepository, PasswordEncoder passwordEncoder, PortofoliuRepository portofoliuRepository) {
        this.cadruDidacticRepository = cadruDidacticRepository;
        this.passwordEncoder = passwordEncoder;
        this.portofoliuRepository = portofoliuRepository;
    }

    public void makeSign(CadruDidactic cadruDidactic) {
        String baseDir = "semnaturi/cadreDidactice/cadruDidactic" + cadruDidactic.getId();
        File cadruDidacticDir = new File(baseDir);

        if (!cadruDidacticDir.exists()) {
            boolean created = cadruDidacticDir.mkdirs();
            if (created) {
                System.out.println("Directorul 'cadreDidactice/cadruDidactic" + cadruDidactic.getId() + "' a fost creat.");
            } else {
                System.out.println("Eroare la crearea directorului 'cadreDidactice/cadruDidactic" + cadruDidactic.getId() + "'.");
            }
        }

        cadruDidactic.setSemnatura(baseDir);
        cadruDidactic.setPassword(passwordEncoder.encode("profesor"+cadruDidactic.getId()));
        cadruDidacticRepository.save(cadruDidactic);
    }

    public void removeProfFromPortofolios(int id) {
        List<Portofoliu> portofolii = portofoliuRepository.findAll();
        for (Portofoliu portofoliu : portofolii) {
            CadruDidactic cadruDidactic = portofoliu.getCadruDidactic();
            if (cadruDidactic != null && cadruDidactic.getId() == id) {
                portofoliu.setCadruDidactic(null);
                portofoliuRepository.save(portofoliu);
            }
        }
    }
}
