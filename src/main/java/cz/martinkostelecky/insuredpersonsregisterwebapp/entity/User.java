package cz.martinkostelecky.insuredpersonsregisterwebapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
