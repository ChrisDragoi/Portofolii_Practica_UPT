package ro.upt.ac.portofolii.student;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student 
{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String nume;
	private String prenume;
	private String cnp;
	private Date dataNasterii;
	private String loculNasterii;
	private String cetatenie;
	private String serieCi;
	private String numarCi;
	private String adresa;
	private String anUniversitar;
	private String facultate;
	private String specializare;
	private int anDeStudiu;
	private String email;
	private String telefon;
	private String semnatura;
	
	//private String password;
	
	public Student()
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

	public String getCnp()
	{
		return cnp;
	}

	public void setCnp(String cnp)
	{
		this.cnp = cnp;
	}

	public Date getDataNasterii()
	{
		return dataNasterii;
	}

	public void setDataNasterii(Date dataNasterii)
	{
		this.dataNasterii = dataNasterii;
	}

	public String getLoculNasterii()
	{
		return loculNasterii;
	}

	public void setLoculNasterii(String loculNasterii)
	{
		this.loculNasterii = loculNasterii;
	}

	public String getCetatenie()
	{
		return cetatenie;
	}

	public void setCetatenie(String cetatenie)
	{
		this.cetatenie = cetatenie;
	}


	public String getSerieCi()
	{
		return serieCi;
	}

	public void setSerieCi(String serieCi)
	{
		this.serieCi = serieCi;
	}

	public String getNumarCi()
	{
		return numarCi;
	}

	public void setNumarCi(String numarCi)
	{
		this.numarCi = numarCi;
	}

	public String getAdresa()
	{
		return adresa;
	}

	public void setAdresa(String adresa)
	{
		this.adresa = adresa;
	}

	public String getAnUniversitar()
	{
		return anUniversitar;
	}

	public void setAnUniversitar(String anUniversitar)
	{
		this.anUniversitar = anUniversitar;
	}

	public String getFacultate()
	{
		return facultate;
	}

	public void setFacultate(String facultate)
	{
		this.facultate = facultate;
	}

	public String getSpecializare()
	{
		return specializare;
	}

	public void setSpecializare(String specializare)
	{
		this.specializare = specializare;
	}

	public int getAnDeStudiu()
	{
		return anDeStudiu;
	}

	public void setAnDeStudiu(int anDeStudiu)
	{
		this.anDeStudiu = anDeStudiu;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getTelefon()
	{
		return telefon;
	}

	public void setTelefon(String telefon)
	{
		this.telefon = telefon;
	}

	public String getSemnatura() {
		return semnatura;
	}

	public void setSemnatura(String semnatura) {
		this.semnatura = semnatura;
	}

	//	public String getPassword() {
//		return password;
//	}
//
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	public boolean isEnabled() {
//		return true;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
}
