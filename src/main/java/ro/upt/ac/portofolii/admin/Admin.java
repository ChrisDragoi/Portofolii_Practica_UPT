package ro.upt.ac.portofolii.admin;

import jakarta.persistence.Entity;
import lombok.*;
import ro.upt.ac.portofolii.security.Role;
import ro.upt.ac.portofolii.security.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {
    private String semnatura;

    @Builder
    public Admin(String email, String password, Role role) {
        super(email, password, role);
    }

}
