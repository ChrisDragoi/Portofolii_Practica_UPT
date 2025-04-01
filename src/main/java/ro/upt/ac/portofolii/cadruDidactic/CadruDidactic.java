package ro.upt.ac.portofolii.cadruDidactic;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class CadruDidactic extends User {

	private String nume;
	private String prenume;

	private String functie;
	private String specializare;

	private String telefon;
	//private String email;

	private String semnatura;

	@Builder
	public CadruDidactic(String email, String password, Role role){
		super(email, password, role);
	}

}
