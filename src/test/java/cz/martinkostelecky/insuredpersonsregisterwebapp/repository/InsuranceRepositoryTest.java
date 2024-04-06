package cz.martinkostelecky.insuredpersonsregisterwebapp.repository;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.Insurance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InsuranceRepositoryTest {

    @Autowired
    private InsuranceRepository insuranceRepositoryTest;

    @AfterEach
    void tearDown() {
        insuranceRepositoryTest.deleteAll();
    }

    @Test
    public void itShouldCheckIfInsuranceExists() {
    //given
        Insurance insurance = new Insurance(
                1L,
                "Pojištění zdraví",
                1000000,
                "Životní pojištění",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 31)
                );
        insuranceRepositoryTest.save(insurance);
    //when
        boolean expected = insuranceRepositoryTest.existsByType("Pojištění zdraví");
    //then
        assertThat(expected).isTrue();

    }
    @Test
    public void itShouldCheckWhenInsuranceDoesntExist() {
        //given
        Insurance insurance = new Insurance(
                1L,
                "Pojištění zdraví",
                1000000,
                "Životní pojištění",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 31)
        );
        insuranceRepositoryTest.save(insurance);
        //when
        boolean expected = insuranceRepositoryTest.existsByType("Pojištění majetku");
        //then
        assertThat(expected).isFalse();
    }
}