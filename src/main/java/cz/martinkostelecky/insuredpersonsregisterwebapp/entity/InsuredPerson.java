package cz.martinkostelecky.insuredpersonsregisterwebapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing Insured person
 */
@Entity
@Table(name="insuredPersons")
public class InsuredPerson {
    /**
     * Class attributes
     */
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Zadejte jméno a příjmení oddělené mezerou.")
    @NotNull(message = "Zadejte jméno a příjmení oddělené mezerou.")
    @Column(name = "name")
    private String name;
    @NotBlank(message = "Zadejte ulici a číslo popisné.")
    @NotNull(message = "Zadejte ulici a číslo popisné.")
    @Column(name = "street")
    private String street;
    @NotBlank(message = "Zadejte město.")
    @NotNull(message = "Zadejte město.")
    @Column(name = "city")
    private String city;
    @NotNull(message="Zadejte e-mail.")
    @NotBlank(message = "Zadejte e-mail.")
    @Email(message = "Nesprávně zadaný e-mail.")
    @Column(name = "email")
    private String email;
    @Pattern(regexp = "^\\d{9}$", message = "Zadejte telefonní číslo jako číselný údaj bez mezer a bez předvolby země.")
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(mappedBy = "insuredPerson", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Insurance> allInsurance;
    /**
     * Default constructor
     */
    public InsuredPerson() {
    }
    /**
     * Parametric constructor
     * @param id
     * @param name
     * @param street
     * @param city
     * @param email
     * @param phoneNumber
     */
    public InsuredPerson(Long id, String name, String street, String city, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    /**
     * Getters and setters
     */
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getStreet() {
        return street;
    }
    public String getCity() {
        return city;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public List<Insurance> getAllInsurance() {
        return allInsurance;
    }
    public void setAllInsurance(List<Insurance> allInsurance) {
        this.allInsurance = allInsurance;
    }
    @Override
    public String toString() {
        return "Insured person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}


