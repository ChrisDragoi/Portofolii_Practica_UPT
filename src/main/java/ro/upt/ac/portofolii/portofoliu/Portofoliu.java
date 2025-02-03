package ro.upt.ac.portofolii.portofoliu;

import java.sql.Date;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidactic;
import ro.upt.ac.portofolii.student.Student;
import ro.upt.ac.portofolii.tutore.Tutore;

/**
 * 
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Portofoliu {

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Tutore getTutore() {
		return tutore;
	}

	public void setTutore(Tutore tutore) {
		this.tutore = tutore;
	}

	public CadruDidactic getCadruDidactic() {
		return cadruDidactic;
	}

	public void setCadruDidactic(CadruDidactic cadruDidactic) {
		this.cadruDidactic = cadruDidactic;
	}

	public String getLoculDesfasurarii() {
		return loculDesfasurarii;
	}

	public void setLoculDesfasurarii(String loculDesfasurarii) {
		this.loculDesfasurarii = loculDesfasurarii;
	}

	public Integer getDurataPracticii() {
		return durataPracticii;
	}

	public void setDurataPracticii(Integer durataPracticii) {
		this.durataPracticii = durataPracticii;
	}

	public Date getDataInceput() {
		return dataInceput;
	}

	public void setDataInceput(Date dataInceput) {
		this.dataInceput = dataInceput;
	}

	public Date getDataSfarsit() {
		return dataSfarsit;
	}

	public void setDataSfarsit(Date dataSfarsit) {
		this.dataSfarsit = dataSfarsit;
	}

	public Date getDataSemnarii() {
		return dataSemnarii;
	}

	public void setDataSemnarii(Date dataSemnarii) {
		this.dataSemnarii = dataSemnarii;
	}

	public String getOrar() {
		return orar;
	}

	public void setOrar(String orar) {
		this.orar = orar;
	}

	public String getLocatiiExtra() {
		return locatiiExtra;
	}

	public void setLocatiiExtra(String locatiiExtra) {
		this.locatiiExtra = locatiiExtra;
	}

	public String getCompetenteNecesare() {
		return competenteNecesare;
	}

	public void setCompetenteNecesare(String competenteNecesare) {
		this.competenteNecesare = competenteNecesare;
	}

	public String getComplementareInvatamantPractica() {
		return complementareInvatamantPractica;
	}

	public void setComplementareInvatamantPractica(String complementareInvatamantPractica) {
		this.complementareInvatamantPractica = complementareInvatamantPractica;
	}

	public String getTematicaSiSarcini() {
		return tematicaSiSarcini;
	}

	public void setTematicaSiSarcini(String tematicaSiSarcini) {
		this.tematicaSiSarcini = tematicaSiSarcini;
	}

	public String getCompetenteDobandite() {
		return competenteDobandite;
	}

	public void setCompetenteDobandite(String competenteDobandite) {
		this.competenteDobandite = competenteDobandite;
	}

	public String getModDePregatire() {
		return modDePregatire;
	}

	public void setModDePregatire(String modDePregatire) {
		this.modDePregatire = modDePregatire;
	}

	public String getActivitatiPlanificate() {
		return activitatiPlanificate;
	}

	public void setActivitatiPlanificate(String activitatiPlanificate) {
		this.activitatiPlanificate = activitatiPlanificate;
	}

	public String getObservatii() {
		return observatii;
	}

	public void setObservatii(String observatii) {
		this.observatii = observatii;
	}

	public String getSemnaturaStudent() {
		return semnaturaStudent;
	}

	public void setSemnaturaStudent(String semnaturaStudent) {
		this.semnaturaStudent = semnaturaStudent;
	}

	public String getSemnaturaTutore() {
		return semnaturaTutore;
	}

	public void setSemnaturaTutore(String semnaturaTutore) {
		this.semnaturaTutore = semnaturaTutore;
	}

	public String getSemnaturaCadruDidactic() {
		return semnaturaCadruDidactic;
	}

	public void setSemnaturaCadruDidactic(String semnaturaCadruDidactic) {
		this.semnaturaCadruDidactic = semnaturaCadruDidactic;
	}
}
