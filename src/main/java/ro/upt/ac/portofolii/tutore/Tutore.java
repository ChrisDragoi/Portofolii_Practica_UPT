package ro.upt.ac.portofolii.tutore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tutore 
{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String nume;
	private String prenume;
	private String functie;
	private String telefon;
	private String email;
	private String semnatura;
}
