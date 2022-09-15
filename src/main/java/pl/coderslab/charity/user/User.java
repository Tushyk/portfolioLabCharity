package pl.coderslab.charity.user;

import lombok.*;
import pl.coderslab.charity.validations.PasswordMatches;
import pl.coderslab.charity.validations.ValidEmail;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Getter
@Setter
@Table( name = "users")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 60)
    @NotBlank(message = "pole nie moze byc puste")
    private String username;
    @NotBlank(message = "pole nie moze byc puste")
    private String password;
    @NotBlank(message = "pole nie moze byc puste")
    @Email
    @Column(nullable = false, unique = true, length = 60)
    private String email;
    private int enabled;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> roles;
}
