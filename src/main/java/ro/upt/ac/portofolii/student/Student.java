package ro.upt.ac.portofolii.student;

import java.sql.Date;
import jakarta.persistence.Entity;
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
public class Student extends User {
	
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
	private String telefon;
	private String semnatura;
	
	@Builder
	public Student(String email, String password, Role role) {
		super(email, password, role);
	}

}
