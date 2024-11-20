package ro.upt.ac.portofolii.portofoliu;

import java.sql.Date;
import jakarta.persistence.*;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidactic;
import ro.upt.ac.portofolii.student.Student;
import ro.upt.ac.portofolii.tutore.Tutore;

@Entity
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
	
	private byte[] semnaturaTutoreFacultate;
	private byte[] semnaturaTutorePractica;
	private byte[] semnaturaStudent;
		
	public Portofoliu()
	{
	}

	public void setTematicaSiSarcini(String tematicaSiSarcini) {
		this.tematicaSiSarcini = tematicaSiSarcini;
	}

	public void setCompetenteDobandite(String competenteDobandite) {
		this.competenteDobandite = competenteDobandite;
	}

	public void setModDePregatire(String modDePregatire) {
		this.modDePregatire = modDePregatire;
	}

	public void setActivitatiPlanificate(String activitatiPlanificate) {
		this.activitatiPlanificate = activitatiPlanificate;
	}
	public Date getDataSemnarii() {
		return dataSemnarii;
	}
	public void setObservatii(String observatii) {
		this.observatii = observatii;
	}

	public void setSemnaturaTutoreFacultate(byte[] semnaturaTutore) {
		this.semnaturaTutoreFacultate = semnaturaTutore;
	}

	public void setSemnaturaTutorePractica(byte[] semnaturaTutorePractica) {
		this.semnaturaTutorePractica = semnaturaTutorePractica;
	}
	public void setDataSemnarii(Date dataCompletarii) {
		this.dataSemnarii = dataCompletarii;
	}
	public void setSemnaturaStudent(byte[] semnaturaStudent) {
		this.semnaturaStudent = semnaturaStudent;
	}

	public void setTutore(Tutore tutore) {
		this.tutore = tutore;
	}

	public String getTematicaSiSarcini() {
		return tematicaSiSarcini;
	}

	public String getCompetenteDobandite() {
		return competenteDobandite;
	}

	public String getModDePregatire() {
		return modDePregatire;
	}

	public String getActivitatiPlanificate() {
		return activitatiPlanificate;
	}

	public String getObservatii() {
		return observatii;
	}

	public byte[] getSemnaturaTutoreFacultate() {
		return semnaturaTutoreFacultate;
	}

	public byte[] getSemnaturaTutorePractica() {
		return semnaturaTutorePractica;
	}

	public byte[] getSemnaturaStudent() {
		return semnaturaStudent;
	}

	public Tutore getTutore() 
	{
		return tutore;
	}

	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}

	public Integer getDurataPracticii() {
		return durataPracticii;
	}

	public void setDurataPracticii(int durataPracticii) {
		this.durataPracticii = durataPracticii;
	}

	public Student getStudent()
	{
		return student;
	}

	public void setStudent(Student student)
	{
		this.student = student;
	}

	public void setDurataPracticii(Integer durataPracticii) 
	{
		this.durataPracticii = durataPracticii;
	}

	public void setOrar(String orar) 
	{
		this.orar = orar;
	}

	public void setLocatiiExtra(String locatiiExtra) 
	{
		this.locatiiExtra = locatiiExtra;
	}

	public void setCompetenteNecesare(String competenteNecesare) 
	{
		this.competenteNecesare = competenteNecesare;
	}

	public void setComplementareInvatamantPractica(String complementareInvatamantPractica) 
	{
		this.complementareInvatamantPractica = complementareInvatamantPractica;
	}
	
	public CadruDidactic getCadruDidactic() 
	{
		return cadruDidactic;
	}
	
	public void setCadruDidactic(CadruDidactic cadruDidactic) 
	{
		this.cadruDidactic = cadruDidactic;
	}
	
	public String getOrar() 
	{
		return orar;
	}

	public String getLocatiiExtra() 
	{
		return locatiiExtra;
	}

	public String getCompetenteNecesare() 
	{
		return competenteNecesare;
	}

	public String getComplementareInvatamantPractica() 
	{
		return complementareInvatamantPractica;
	}

	public String getLoculDesfasurarii()
	{
		return loculDesfasurarii;
	}

	public void setLoculDesfasurarii(String loculDesfasurarii)
	{
		this.loculDesfasurarii = loculDesfasurarii;
	}

	public Date getDataInceput()
	{
		return dataInceput;
	}

	public void setDataInceput(Date dataInceput)
	{
		this.dataInceput = dataInceput;
	}

	public Date getDataSfarsit()
	{
		return dataSfarsit;
	}

	public void setDataSfarsit(Date dataSfarsit)
	{
		this.dataSfarsit = dataSfarsit;
	}	
}
