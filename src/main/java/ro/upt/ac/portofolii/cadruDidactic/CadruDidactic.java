package ro.upt.ac.portofolii.cadruDidactic;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CadruDidactic
{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String nume;
	private String prenume;

	private String functie;
	private String specializare;

	private String telefon;
	private String email;

	private String semnatura;

	public CadruDidactic() 
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

	public String getNume()
	{
		return nume;
	}

	public void setNume(String nume)
	{
		this.nume = nume;
	}

	public String getPrenume()
	{
		return prenume;
	}

	public void setPrenume(String prenume)
	{
		this.prenume = prenume;
	}

	public String getFunctie()
	{
		return functie;
	}

	public void setFunctie(String functie)
	{
		this.functie = functie;
	}

	public String getTelefon()
	{
		return telefon;
	}

	public void setTelefon(String telefon)
	{
		this.telefon = telefon;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getSpecializare()
	{
		return specializare;
	}

	public void setSpecializare(String specializare)
	{
		this.specializare = specializare;
	}

	public String getSemnatura() {
		return semnatura;
	}

	public void setSemnatura(String semnatura) {
		this.semnatura = semnatura;
	}
}
