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
    //	public void setSemnaturaTutorePractica(byte[] semnaturaTutorePractica) {
    //		this.semnaturaTutorePractica = semnaturaTutorePractica;
    //	}

    private Date dataSemnarii;

    private String orar;

    private String locatiiExtra;

    private String competenteNecesare;

    private String complementareInvatamantPractica;

    private String tematicaSiSarcini;

    private String competenteDobandite;
	//@Setter
    private String modDePregatire;

    private String activitatiPlanificate;

    private String observatii;

	private String semnaturaStudent;

//    public Date getDataSemnarii() {
//		return dataSemnarii;
//	}
//
//    public String getTematicaSiSarcini() {
//		return tematicaSiSarcini;
//	}
//
//	public String getCompetenteDobandite() {
//		return competenteDobandite;
//	}
//
//	public String getModDePregatire() {
//		return modDePregatire;
//	}
//
//	public String getActivitatiPlanificate() {
//		return activitatiPlanificate;
//	}
//
//	public String getObservatii() {
//		return observatii;
//	}
//
//	public Tutore getTutore()
//	{
//		return tutore;
//	}
//
//	public int getId()
//	{
//		return id;
//	}
//
//    public Integer getDurataPracticii() {
//		return durataPracticii;
//	}
//
//	public void setDurataPracticii(int durataPracticii) {
//		this.durataPracticii = durataPracticii;
//	}
//
//	public Student getStudent()
//	{
//		return student;
//	}
//
//    public void setDurataPracticii(Integer durataPracticii)
//	{
//		this.durataPracticii = durataPracticii;
//	}
//
//    public CadruDidactic getCadruDidactic()
//	{
//		return cadruDidactic;
//	}
//
//    public String getOrar()
//	{
//		return orar;
//	}
//
//	public String getLocatiiExtra()
//	{
//		return locatiiExtra;
//	}
//
//	public String getCompetenteNecesare()
//	{
//		return competenteNecesare;
//	}
//
//	public String getComplementareInvatamantPractica()
//	{
//		return complementareInvatamantPractica;
//	}
//
//	public String getLoculDesfasurarii()
//	{
//		return loculDesfasurarii;
//	}
//
//	public Date getDataInceput()
//	{
//		return dataInceput;
//	}
//
//	public Date getDataSfarsit()
//	{
//		return dataSfarsit;
//	}
}
