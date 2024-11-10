package ro.upt.ac.portofolii;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ro.upt.ac.portofolii.portofoliu.Portofoliu;
import ro.upt.ac.portofolii.portofoliu.PortofoliuRepository;
import ro.upt.ac.portofolii.portofoliu.TutoreFacultate;
import ro.upt.ac.portofolii.student.Student;
import ro.upt.ac.portofolii.student.StudentRepository;

@EnableWebMvc
@SpringBootApplication
public class Application
{
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner loadDataStudenti(StudentRepository repository)
	{
	    return (args) -> {
			log.info("starting initialization...");

			Student s1=new Student();
			s1.setPassword("test");
	        s1.setNume("Dragoi");
	        s1.setPrenume("Christian");
	        s1.setCnp("1900505010203");
	        s1.setDataNasterii(Date.valueOf("2002-09-13"));
	        s1.setLoculNasterii("Caransebes");
	        s1.setCetatenie("romana");
	        s1.setSerieCi("KS");
	        s1.setNumarCi("112233");
	        s1.setAdresa("Strada Mures 115, Timisoara");
	        s1.setAnUniversitar("2024-2025");
	        s1.setFacultate("AC");
	        s1.setSpecializare("cti-ro");
	        s1.setAnDeStudiu(4);
	        s1.setEmail("christian.dragoi@student.upt.ro");
	        s1.setTelefon("0730504327");
	        repository.save(s1);

			log.info("ending initialization...");
	    };
	}
	@Autowired
	StudentRepository studentRepository;

//	@Bean
//	public CommandLineRunner loadDataPortofolii(PortofoliuRepository repository)
//	{
//	    return (args) -> {
//			log.info("starting initialization...");
//
//			Student stud1=studentRepository.findAll().get(0);
//			TutoreFacultate t1=new TutoreFacultate();
//			t1.setNume_f("Moldovan");
//			t1.setPrenume_f("Ioan");
//			t1.setFunctie_f("manager");
//			Portofoliu c1=new Portofoliu();
//			c1.setStudent(stud1);
//			c1.setLoculDesfasurarii("Cladirea A");
//			c1.setDataInceput(Date.valueOf("2024-07-01"));
//			c1.setDataSfarsit(Date.valueOf("2024-09-01"));
//			c1.setTutore(t1);
//
//	        repository.save(c1);
//
//			log.info("ending initialization...");
//	    };
//	}
}
