package ro.upt.ac.portofolii.portofoliu;

import java.sql.Date;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidactic;
import ro.upt.ac.portofolii.student.Student;
import ro.upt.ac.portofolii.tutore.Tutore;

@Entity
@NoArgsConstructor
public class Portofoliu
{
	@Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Setter
    @ManyToOne
	private Student student;
	@Setter
    @ManyToOne
	private Tutore tutore;
	@Setter
    @ManyToOne
	private CadruDidactic cadruDidactic;

	private String loculDesfasurarii;
	private Integer durataPracticii;
	private Date dataInceput;
	private Date dataSfarsit;
    //	public void setSemnaturaTutorePractica(byte[] semnaturaTutorePractica) {
    //		this.semnaturaTutorePractica = semnaturaTutorePractica;
    //	}
    @Setter
    private Date dataSemnarii;
	@Setter
    private String orar;
	@Setter
    private String locatiiExtra;
	@Setter
    private String competenteNecesare;
	@Setter
    private String complementareInvatamantPractica;
	@Setter
    private String tematicaSiSarcini;
	@Setter
    private String competenteDobandite;
	@Setter
    private String modDePregatire;
	@Setter
    private String activitatiPlanificate;
	@Setter
    private String observatii;

	//private byte[] semnaturaTutoreFacultate;
	//private byte[] semnaturaTutorePractica;
	//private byte[] semnaturaStudent;

    public Date getDataSemnarii() {
		return dataSemnarii;
	}

//	public void setSemnaturaTutoreFacultate(byte[] semnaturaTutore) {
//		this.semnaturaTutoreFacultate = semnaturaTutore;
//	}

//	public void setSemnaturaStudent(byte[] semnaturaStudent) {
//		this.semnaturaStudent = semnaturaStudent;
//	}

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

//	public byte[] getSemnaturaTutoreFacultate() {
//		return semnaturaTutoreFacultate;
//	}
//
//	public byte[] getSemnaturaTutorePractica() {
//		return semnaturaTutorePractica;
//	}
//
//	public byte[] getSemnaturaStudent() {
//		return semnaturaStudent;
//	}

	public Tutore getTutore()
	{
		return tutore;
	}

	public int getId()
	{
		return id;
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

    public void setDurataPracticii(Integer durataPracticii)
	{
		this.durataPracticii = durataPracticii;
	}

    public CadruDidactic getCadruDidactic()
	{
		return cadruDidactic;
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
