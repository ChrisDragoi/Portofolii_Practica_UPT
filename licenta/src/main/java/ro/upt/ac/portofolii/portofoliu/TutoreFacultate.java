package ro.upt.ac.portofolii.portofoliu;

import jakarta.persistence.Embeddable;

@Embeddable
public class TutoreFacultate
{
	private String nume_t;
	private String prenume_t;
	private String functie_t;
	private String telefon;
	private String email;

	private String drepturi;
	
	public TutoreFacultate()
	{
	}

	public String getDrepturi() {
		return drepturi;
	}

	public void setDrepturi(String drepturi) {
		this.drepturi = drepturi;
	}

	public String getNume_t()
	{
		return nume_t;
	}

	public void setNume_t(String nume)
	{
		this.nume_t = nume;
	}

	public String getPrenume_t()
	{
		return prenume_t;
	}

	public void setPrenume_t(String prenume)
	{
		this.prenume_t = prenume;
	}

	public String getFunctie_t()
	{
		return functie_t;
	}

	public void setFunctie_t(String functie)
	{
		this.functie_t = functie;
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
	
	public String toString()
	{
		return nume_t +" "+ prenume_t;
	}
}
