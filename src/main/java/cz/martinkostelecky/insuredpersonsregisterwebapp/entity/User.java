package cz.martinkostelecky.insuredpersonsregisterwebapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Zadejte jméno a příjmení oddělené mezerou.")
    @NotNull(message = "Zadejte jméno a příjmení oddělené mezerou.")
    @Column
    private String name;

    //works as username
    @NotNull(message="Zadejte e-mail.")
    @NotBlank(message = "Zadejte e-mail.")
    @Email(message = "Nesprávně zadaný e-mail.")
    @Column
    private String email;

    @JsonIgnore
    @NotNull(message="Heslo musí být vyplněno.")
    @NotBlank(message = "Heslo musí být vyplněno.")
    private String password;

    @JsonIgnore
    @Transient
    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    private Role role;

}
