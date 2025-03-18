package ro.upt.ac.portofolii.cadruDidactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class CadruDidacticService {
    @Autowired
    private CadruDidacticRepository cadruDidacticRepository;
    public void makeSign(CadruDidactic cadruDidactic) {
        String baseDir = "cadreDidactice";

        File cadreDidacticeDir = new File(baseDir);
        if (!cadreDidacticeDir.exists()) {
            boolean created = cadreDidacticeDir.mkdir();
            if (created) {
                System.out.println("Directorul 'cadreDidactice' a fost creat anterior.");
            }
        }

        String profDirPath = baseDir + "/prof" + cadruDidactic.getId();
        File profDir = new File(profDirPath);
        if (!profDir.exists()) {
            boolean created = profDir.mkdir();
            if (created) {
                System.out.println("Directorul '" + profDirPath + "' a fost creat.");
            }
        }
        cadruDidactic.setSemnatura(profDirPath);
        cadruDidacticRepository.save(cadruDidactic);
    }
}
