package ro.upt.ac.portofolii.cadruDidactic;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.upt.ac.portofolii.security.Role;
import ro.upt.ac.portofolii.security.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class CadruDidactic extends User {

	private String nume;
	private String prenume;
	private String functie;
	private String specializare;
	private String telefon;
	private String semnatura;

	@Builder
	public CadruDidactic(String email, String password, Role role, String nume, String prenume, String functie, String specializare, String telefon) {
		super(email, password, role);
		this.nume = nume;
		this.prenume = prenume;
		this.functie = functie;
		this.specializare = specializare;
		this.telefon = telefon;
	}
}
