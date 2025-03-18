package ro.upt.ac.portofolii.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.upt.ac.portofolii.portofoliu.Portofoliu;
import ro.upt.ac.portofolii.portofoliu.PortofoliuRepository;

import java.io.File;
import java.util.List;

@Service
public class StudentService {
    private final PortofoliuRepository portofoliuRepository;
    @Autowired
    private StudentRepository studentRepository;

    public StudentService(PortofoliuRepository portofoliuRepository) {
        this.portofoliuRepository = portofoliuRepository;
    }

    public void deleteStudentsPortofolios(int id){
        List<Portofoliu> portofolios = portofoliuRepository.findAll();
        for (Portofoliu portofolio : portofolios) {
            if (portofolio.getStudent().getId() == id) {
                portofoliuRepository.delete(portofolio);
            }
        }
    }

    public void makeSign(Student s) {
        String baseDir = "studenti";

        File studentiDir = new File(baseDir);
        if (!studentiDir.exists()) {
            boolean created = studentiDir.mkdir();
            if (created) {
                System.out.println("Directorul 'studenti' a fost creat anterior.");
            }
        }

        String profDirPath = baseDir + "/student" + s.getId();
        File profDir = new File(profDirPath);
        if (!profDir.exists()) {
            boolean created = profDir.mkdir();
            if (created) {
                System.out.println("Directorul '" + profDirPath + "' a fost creat.");
            }
        }
        s.setSemnatura(profDirPath);
        studentRepository.save(s);
    }
}
