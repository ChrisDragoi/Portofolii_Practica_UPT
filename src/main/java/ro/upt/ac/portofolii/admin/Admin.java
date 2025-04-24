package ro.upt.ac.portofolii.admin;

import jakarta.persistence.Entity;
import lombok.*;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidactic;
import ro.upt.ac.portofolii.security.Role;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Admin extends CadruDidactic {

    @Builder(builderMethodName = "adminBuilder")
    public Admin(String email, String password, String nume, String prenume, String functie, String specializare, String telefon) {
        super(email, password, Role.ADMIN, nume, prenume, functie, specializare, telefon);
        super.setSemnatura("semnaturi/cadreDidactice/admin");
    }

}
