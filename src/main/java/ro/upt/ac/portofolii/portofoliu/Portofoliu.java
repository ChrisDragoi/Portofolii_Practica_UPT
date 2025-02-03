package ro.upt.ac.portofolii.portofoliu;

import java.sql.Date;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidactic;
import ro.upt.ac.portofolii.student.Student;
import ro.upt.ac.portofolii.tutore.Tutore;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Portofoliu
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;


    @ManyToOne
	private Student student;

    @ManyToOne
	private Tutore tutore;

    @ManyToOne
	private CadruDidactic cadruDidactic;

	private String loculDesfasurarii;
	
	private Integer durataPracticii;
	
	private Date dataInceput;
	
	private Date dataSfarsit;

    private Date dataSemnarii;

    private String orar;

    private String locatiiExtra;

    private String competenteNecesare;

    private String complementareInvatamantPractica;

    private String tematicaSiSarcini;

    private String competenteDobandite;

    private String modDePregatire;

    private String activitatiPlanificate;

    private String observatii;

	private String semnaturaStudent;

	private String semnaturaTutore;

	private String semnaturaCadruDidactic;
}
