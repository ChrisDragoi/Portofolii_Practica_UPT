package ro.upt.ac.portofolii.student;

import org.springframework.stereotype.Service;
import ro.upt.ac.portofolii.portofoliu.Portofoliu;
import ro.upt.ac.portofolii.portofoliu.PortofoliuRepository;

import java.io.File;
import java.util.List;

@Service
public class StudentService {
    private final PortofoliuRepository portofoliuRepository;
    private final StudentRepository studentRepository;

    public StudentService(PortofoliuRepository portofoliuRepository, StudentRepository studentRepository) {
        this.portofoliuRepository = portofoliuRepository;
        this.studentRepository = studentRepository;
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
        String baseDir = "src/main/resources/static/studenti/student" + s.getId();
        File studentiDir = new File(baseDir);

        if (!studentiDir.exists()) {
            boolean created = studentiDir.mkdirs();
            if (created) {
                System.out.println("Directorul 'studenti/student" + s.getId() + "' a fost creat.");
            } else {
                System.out.println("Eroare la crearea directorului 'studenti/student" + s.getId() + "'.");
            }
        }

        String relativePath = "studenti/student" + s.getId();
        s.setSemnatura(relativePath);

        studentRepository.save(s);
    }

}
