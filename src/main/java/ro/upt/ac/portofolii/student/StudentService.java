package ro.upt.ac.portofolii.student;

import org.springframework.stereotype.Service;
import ro.upt.ac.portofolii.portofoliu.Portofoliu;
import ro.upt.ac.portofolii.portofoliu.PortofoliuRepository;

import java.util.List;

@Service
public class StudentService {
    private final PortofoliuRepository portofoliuRepository;

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
}
