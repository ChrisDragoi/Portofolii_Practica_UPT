package ro.upt.ac.portofolii.portofoliu;

import java.sql.Date;

import jakarta.persistence.*;
import ro.upt.ac.portofolii.student.Student;

@Entity
public class Portofoliu
{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	private Student student;
	
	private String loculDesfasurarii;
	private Integer durataInPlanulDeInvatamant;
	private Date dataInceput;
	private Date dataSfarsit;
	private String numeProiect;
	
	@Embedded
	private Tutore tutore;
	
	private int numarCredite;
	private String indemnizatii;
	private String avantaje;
	private String altePrecizari;
	private Date dataIntocmirii;
	
	public Portofoliu()
	{
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}

	public Student getStudent()
	{
		return student;
	}

	public void setStudent(Student student)
	{
		this.student = student;
	}

	public String getLoculDesfasurarii()
	{
		return loculDesfasurarii;
	}

	public void setLoculDesfasurarii(String loculDesfasurarii)
	{
		this.loculDesfasurarii = loculDesfasurarii;
	}

	public Integer getDurataInPlanulDeInvatamant()
	{
		return durataInPlanulDeInvatamant;
	}

	public void setDurataInPlanulDeInvatamant(Integer durataInPlanulDeInvatamant)
	{
		this.durataInPlanulDeInvatamant = durataInPlanulDeInvatamant;
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

	public String getNumeProiect()
	{
		return numeProiect;
	}

	public void setNumeProiect(String numeProiect)
	{
		this.numeProiect = numeProiect;
	}

	public Tutore getTutore()
	{
		return tutore;
	}

	public void setTutore(Tutore tutore)
	{
		this.tutore = tutore;
	}


	public int getNumarCredite()
	{
		return numarCredite;
	}

	public void setNumarCredite(int numarCredite)
	{
		this.numarCredite = numarCredite;
	}

	public String getIndemnizatii()
	{
		return indemnizatii;
	}

	public void setIndemnizatii(String indemnizatii)
	{
		this.indemnizatii = indemnizatii;
	}

	public String getAvantaje()
	{
		return avantaje;
	}

	public void setAvantaje(String avantaje)
	{
		this.avantaje = avantaje;
	}

	public String getAltePrecizari()
	{
		return altePrecizari;
	}

	public void setAltePrecizari(String altePrecizari)
	{
		this.altePrecizari = altePrecizari;
	}

	public Date getDataIntocmirii()
	{
		return dataIntocmirii;
	}

	public void setDataIntocmirii(Date dataIntocmirii)
	{
		this.dataIntocmirii = dataIntocmirii;
	}	
}
