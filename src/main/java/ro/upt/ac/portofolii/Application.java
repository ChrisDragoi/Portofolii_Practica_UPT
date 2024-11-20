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

import ro.upt.ac.portofolii.tutore.Tutore;
import ro.upt.ac.portofolii.tutore.TutoreRepository;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidactic;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidacticRepository;
import ro.upt.ac.portofolii.portofoliu.Portofoliu;
import ro.upt.ac.portofolii.portofoliu.PortofoliuRepository;
import ro.upt.ac.portofolii.student.Student;
import ro.upt.ac.portofolii.student.StudentRepository;

@EnableWebMvc
@SpringBootApplication
public class Application
{
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired
	StudentRepository studentRepository;
	@Autowired
	CadruDidacticRepository cadruDidacticRepository;
	@Autowired
	TutoreRepository tutoreRepository;

	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner loadDataStudenti1(StudentRepository repository)
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

	@Bean
	public CommandLineRunner loadDataStudenti2(StudentRepository repository)
	{
	    return (args) -> {	
			log.info("starting initialization...");
			
			Student s1=new Student();
	        s1.setNume("Bunea");
	        s1.setPrenume("Sergiu");
	        s1.setCnp("1900505010203");
	        s1.setDataNasterii(Date.valueOf("1990-05-05"));
	        s1.setLoculNasterii("Caransebes");
	        s1.setCetatenie("romana");
	        s1.setSerieCi("TZ");
	        s1.setNumarCi("112233");
	        s1.setAdresa("Calea Lipovei 10, Timisoara");
	        s1.setAnUniversitar("2023-2024");
	        s1.setFacultate("AC");
	        s1.setSpecializare("cti-en");
	        s1.setAnDeStudiu(3);
	        s1.setEmail("sergiu.bunea@student.upt.ro");
	        s1.setTelefon("0700112233");
	        repository.save(s1);
				
			log.info("ending initialization...");
	    };
	}

	@Bean
	public CommandLineRunner loadDataCadreDidactice(CadruDidacticRepository repository)
	{
	    return (args) -> {	
			log.info("starting initialization...");
			
			CadruDidactic cd1=new CadruDidactic();
	        cd1.setNume("Todinca");
	        cd1.setPrenume("Doru");
	        cd1.setFunctie("conferentiar");
	        cd1.setTelefon("0700123456");
	        cd1.setEmail("doru.todinca@upt.ro");
	        cd1.setSpecializare("cti-ro");
	        repository.save(cd1);
	        
			CadruDidactic cd2=new CadruDidactic();
	        cd2.setNume("Cernazanu");
	        cd2.setPrenume("Cosmin");
	        cd2.setFunctie("conferentiar");
	        cd2.setTelefon("0700123456");
	        cd2.setEmail("cosmin.cernazanu@upt.ro");
	        cd2.setSpecializare("cti-en");
	        repository.save(cd2);

			CadruDidactic cd3=new CadruDidactic();
	        cd3.setNume("Nanu");
	        cd3.setPrenume("Sorin");
	        cd3.setFunctie("sef de lucrari");
	        cd3.setTelefon("0700123456");
	        cd3.setEmail("sorin.nanu@upt.ro");
	        cd3.setSpecializare("is");
	        repository.save(cd3);

			CadruDidactic cd4=new CadruDidactic();
	        cd4.setNume("Szeidert");
	        cd4.setPrenume("Iosif");
	        cd4.setFunctie("conferentiar");
	        cd4.setTelefon("0700123456");
	        cd4.setEmail("iosif.szeidert@upt.ro");
	        cd4.setSpecializare("info-zi");
	        repository.save(cd4);

			CadruDidactic cd5=new CadruDidactic();
	        cd5.setNume("Crisan-Vida");
	        cd5.setPrenume("Mihaela");
	        cd5.setFunctie("sef de lucrari");
	        cd5.setTelefon("0700123456");
	        cd5.setEmail("mihaela.crisan-vida@upt.ro");
	        cd5.setSpecializare("info-id");
	        repository.save(cd5);

			log.info("ending initialization...");
	    };
	}

	@Bean
	public CommandLineRunner loadDataTutori(TutoreRepository repository)
	{
	    return (args) -> {	
			log.info("starting initialization...");
			
			Tutore t1=new Tutore();
	        t1.setNume("Ciocan");
	        t1.setPrenume("Florin Costin");
	        t1.setFunctie("PMP");
	        t1.setTelefon("0700123456");
	        t1.setEmail("florin.ciocan@nokia.com");
	        repository.save(t1);
				
			log.info("ending initialization...");
	    };
	}
	
	@Bean
	public CommandLineRunner loadDataPortofolii(PortofoliuRepository repository)
	{
	    return (args) -> {
			log.info("starting initialization...");

			Student s1=studentRepository.findAll().get(0);
			CadruDidactic cd1=cadruDidacticRepository.findAll().get(0);
			Tutore t1=tutoreRepository.findAll().get(0);
					
			Portofoliu p1=new Portofoliu();
			p1.setStudent(s1);
			p1.setLoculDesfasurarii("Cladirea A");
			p1.setDataInceput(Date.valueOf("2024-07-01"));
			p1.setDataSfarsit(Date.valueOf("2024-09-01"));
			p1.setTutore(t1);
			p1.setCadruDidactic(cd1);

	        repository.save(p1);

			log.info("ending initialization...");
	    };
	}
}
