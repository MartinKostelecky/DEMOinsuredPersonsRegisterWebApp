package cz.martinkostelecky.insuredpersonsregisterwebapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
/**
 * Class representing insurances
 */
@Entity
@Table(name="allInsurance")
public class Insurance {
    /**
     * Class attributes
     */
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="type")
    private String type;
    @Min(value = 100, message = "Pojistná částka musí být větší než 0.")
    @Column(name="amount")
    private int amount;
    @NotNull(message = "Zadejte předmět pojištění.")
    @NotBlank(message = "Zadejte předmět pojištění.")
    @Column(name="subjectOfInsurance")
    private String subjectOfInsurance;
    @NotNull(message = "Zadejte datum počátku pojištění.")
    @Column(name="validFrom")
    private LocalDate validFrom;
    @NotNull(message = "Zadejte datum konce pojištění.")
    @Column(name="validTo")
    private LocalDate validTo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="insuredperson_id")
    private InsuredPerson insuredPerson;
    /**
     * Default constructor
     */
    public Insurance() {
    }
    /**
     * Parametric constructor
     * @param id
     * @param type
     * @param amount
     * @param subjectOfInsurance
     * @param validFrom
     * @param validTo
     */
    public Insurance(Long id, String type, int amount, String subjectOfInsurance, LocalDate validFrom, LocalDate validTo) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.subjectOfInsurance = subjectOfInsurance;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    /**
     * Getters and setters
     */
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getSubjectOfInsurance() {
        return subjectOfInsurance;
    }
    public void setSubjectOfInsurance(String subjectOfInsurance) {
        this.subjectOfInsurance = subjectOfInsurance;
    }
    public LocalDate getValidFrom() {
        return validFrom;
    }
    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }
    public LocalDate getValidTo() {
        return validTo;
    }
    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }
    public InsuredPerson getInsuredPerson() {
        return insuredPerson;
    }
    public void setInsuredPerson(InsuredPerson insuredPerson) {
        this.insuredPerson = insuredPerson;
    }
    @Override
    public String toString() {
        return "Insurance{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", subjectOfInsurance='" + subjectOfInsurance + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                '}';
    }
}
