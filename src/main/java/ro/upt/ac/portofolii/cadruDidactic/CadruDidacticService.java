package ro.upt.ac.portofolii.cadruDidactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class CadruDidacticService {
    @Autowired
    private CadruDidacticRepository cadruDidacticRepository;
    public void makeSign(CadruDidactic s) {
        String baseDir = "src/main/resources/static/cadreDidactice/cadruDidactic" + s.getId();
        File cadruDidacticDir = new File(baseDir);

        if (!cadruDidacticDir.exists()) {
            boolean created = cadruDidacticDir.mkdirs();
            if (created) {
                System.out.println("Directorul 'cadreDidactice/cadruDidactic" + s.getId() + "' a fost creat.");
            } else {
                System.out.println("Eroare la crearea directorului 'cadreDidactice/cadruDidactic" + s.getId() + "'.");
            }
        }

        String relativePath = "cadreDidactice/cadruDidactic" + s.getId();
        s.setSemnatura(relativePath);

        cadruDidacticRepository.save(s);
    }
}
