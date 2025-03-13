package ro.upt.ac.portofolii.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    @Setter
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String mail, String password, Role role) {
        this.email = mail;
        this.role = role;
        this.password = password;
    }

}

