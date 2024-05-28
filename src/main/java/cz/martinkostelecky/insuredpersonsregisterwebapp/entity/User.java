package cz.martinkostelecky.insuredpersonsregisterwebapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //works as username
    @Column
    private String email;

    @JsonIgnore
    private String password;

    @Column
    private String role;

}
