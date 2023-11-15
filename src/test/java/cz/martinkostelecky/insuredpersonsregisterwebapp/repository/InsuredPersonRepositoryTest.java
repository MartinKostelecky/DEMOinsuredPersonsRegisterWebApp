package cz.martinkostelecky.insuredpersonsregisterwebapp.repository;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.InsuredPerson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class InsuredPersonRepositoryTest {

    @Autowired
    private InsuredPersonRepository insuredPersonRepositoryTest;

    @AfterEach
    void tearDown() {
        insuredPersonRepositoryTest.deleteAll();
    }

    @Test
    public void itShouldCheckWhenInsuredPersonEmailExists() {
        String email = "jan@novak.cz";
        //given
        InsuredPerson insuredPerson = new InsuredPerson(
                1L,
                "Jan Novák",
                "Nová 1",
                "Praha",
                email,
                "000000000");

        insuredPersonRepositoryTest.save(insuredPerson);
        //when
        boolean expected = insuredPersonRepositoryTest.existsByEmail(email);
        //then
        assertThat(expected).isTrue();
    }

    @Test
    public void itShouldCheckWhenInsuredPersonEmailDoesntExist() {
        //given
        String email = "jan@novak.cz";

        //when
        boolean expected = insuredPersonRepositoryTest.existsByEmail(email);
        //then
        assertThat(expected).isFalse();
    }

}