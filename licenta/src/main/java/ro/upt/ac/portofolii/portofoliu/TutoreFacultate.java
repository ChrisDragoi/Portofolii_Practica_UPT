package ro.upt.ac.portofolii.portofoliu;

import jakarta.persistence.Embeddable;

@Embeddable
public class TutoreFacultate
{
	private String nume_f;
	private String prenume_f;
	private String functie_f;
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

	public String getNume_f()
	{
		return nume_f;
	}

	public void setNume_f(String nume)
	{
		this.nume_f = nume;
	}

	public String getPrenume_f()
	{
		return prenume_f;
	}

	public void setPrenume_f(String prenume)
	{
		this.prenume_f = prenume;
	}

	public String getFunctie_f()
	{
		return functie_f;
	}

	public void setFunctie_f(String functie)
	{
		this.functie_f = functie;
	}
	
	public String toString()
	{
		return nume_f +" "+ prenume_f;
	}
}
