package cz.martinkostelecky.insuredpersonsregisterwebapp.service.impl;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.Insurance;
import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.InsuredPerson;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.EmailAlreadyTakenException;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.InsuranceNotFoundException;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.InsuredPersonNotFoundException;
import cz.martinkostelecky.insuredpersonsregisterwebapp.repository.InsuranceRepository;
import cz.martinkostelecky.insuredpersonsregisterwebapp.repository.InsuredPersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

//using @ExtendWith annotation allow avoid tearDown method and autoCloseable instance
@ExtendWith(MockitoExtension.class)
class InsuredPersonsServiceImplTest {

    //not @Autowired because we know our repositories were tested and work
    @Mock
    private InsuredPersonRepository insuredPersonRepository;
    //private AutoCloseable autoCloseable;
    @Mock
    private InsuranceRepository insuranceRepository;

    private InsuredPersonsServiceImpl insuredPersonsServiceTest;

    //before each test we get new insuredPersonsService
    @BeforeEach
    void setUp() {
        //autoCloseable = MockitoAnnotations.openMocks(this);
        insuredPersonsServiceTest = new InsuredPersonsServiceImpl(insuredPersonRepository, insuranceRepository);
    }

    /*@AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }*/

    @Test
    void canGetAllInsuredPersons() {
        //when
        insuredPersonsServiceTest.getAllInsuredPerson();
        //then
        verify(insuredPersonRepository).findAll();
    }

    @Test
    void canSaveInsuredPerson() throws EmailAlreadyTakenException {
        //given
        InsuredPerson insuredPerson = new InsuredPerson(
                1L,
                "Jan Novák",
                "Nová 1",
                "Praha",
                "jan@novak.cz",
                "000000000"
        );

        //when
        insuredPersonsServiceTest.saveInsuredPerson(insuredPerson);

        //then
        ArgumentCaptor<InsuredPerson> insuredPersonArgumentCaptor = ArgumentCaptor.forClass(InsuredPerson.class);
        //verify if repository saves argument captured by ArgumentCaptor
        verify(insuredPersonRepository).save(insuredPersonArgumentCaptor.capture());

        InsuredPerson capturedInsuredPerson = insuredPersonArgumentCaptor.getValue();

        assertThat(capturedInsuredPerson).isEqualTo(insuredPerson);
    }

    @Test
    void willThrowExceptionEmailTaken() {
        //given
        InsuredPerson insuredPerson = new InsuredPerson(
                1L,
                "Jan Novák",
                "Nová 1",
                "Praha",
                "jan@novak.cz",
                "000000000"
        );

        when(insuredPersonRepository.existsByEmail("jan@novak.cz")).thenReturn(true);

        EmailAlreadyTakenException exception = assertThrows(EmailAlreadyTakenException.class,
                () -> insuredPersonsServiceTest.saveInsuredPerson(insuredPerson));

        assertEquals("E-mail " + insuredPerson.getEmail() + " již patří jinému pojištěnému.", exception.getMessage());

    }

    @Test
    void canGetInsuredPersonById() throws InsuredPersonNotFoundException {
        //given
        Long id = 1L;
        InsuredPerson expectedInsuredPerson = new InsuredPerson(
                1L,
                "Jan Novák",
                "Nová 1",
                "Praha",
                "jan@novak.cz",
                "000000000"
        );
        //when(...): sets up an expectation for a method call on the mock object
        //if exists an entry in database by id then return optional type (used to represent a value
        //that may or may not be present) of InsuredPerson
        when(insuredPersonRepository.findById(id)).thenReturn(Optional.of(expectedInsuredPerson));
        //when
        //get actual insured person from repo by id
        InsuredPerson actualInsuredPerson = insuredPersonsServiceTest.getInsuredPersonById(id);

        //then
        //check if actual Insured person doesn´t point to null and check if equals to expected insured person
        assertThat(actualInsuredPerson).isNotNull().isEqualTo(expectedInsuredPerson);
    }

    @Test
    void cantGetInsuredPersonById() throws InsuredPersonNotFoundException {
        //given
        Long id = 2L;
        when(insuredPersonRepository.findById(id)).thenReturn(Optional.empty());

        //when
        InsuredPersonNotFoundException exception = assertThrows(InsuredPersonNotFoundException.class,
                () -> insuredPersonsServiceTest.getInsuredPersonById(id));

        //then
        assertEquals("Pojištěný nenalezen.", exception.getMessage());
    }

    @Test
    void canUpdateInsuredPerson() throws InsuredPersonNotFoundException, EmailAlreadyTakenException {
        //given

        //instance of Insured person we want to update
        InsuredPerson toUpdateInsuredPerson = new InsuredPerson();
        toUpdateInsuredPerson.setId(1L);
        toUpdateInsuredPerson.setName("Adam Novák");
        toUpdateInsuredPerson.setStreet("Nová 3");
        toUpdateInsuredPerson.setCity("Brno");
        toUpdateInsuredPerson.setEmail("adam@novak.cz");
        toUpdateInsuredPerson.setPhoneNumber("111111111");

        //instance of existing Insured person
        InsuredPerson existingInsuredPerson = new InsuredPerson();
        existingInsuredPerson.setId(1L);
        existingInsuredPerson.setName("Jan Nový");
        existingInsuredPerson.setStreet("Nová 1");
        existingInsuredPerson.setCity("Praha");
        existingInsuredPerson.setEmail("jan@novy.cz");
        existingInsuredPerson.setPhoneNumber("000000000");

        when(insuredPersonRepository.findById(1L)).thenReturn(Optional.of(existingInsuredPerson));
        //save(argThat(...)): specifies the expected method call (save) with an argument that matches the provided argThat matcher.
        //The argThat matcher allows you to define a custom argument matcher.
        //thenAnswer(...): Specifies the behavior to be executed when the specified method is called. In this case,
        //it uses a lambda expression to return the argument passed to the save method (invocation.getArgument(0)).
        //This is a way to simulate the behavior of the save method by returning the InsuredPerson that was passed to it.
        //without actually persisting it to the database during the test.
        when(insuredPersonRepository.save(argThat(insuredPerson ->
                "Adam Novák".equals(insuredPerson.getName()) &&
                        "Nová 3".equals(insuredPerson.getStreet()) &&
                        "Brno".equals(insuredPerson.getCity()) &&
                        "adam@novak.cz".equals(insuredPerson.getEmail()) &&
                        "111111111".equals(insuredPerson.getPhoneNumber())
        ))).thenAnswer(invocation -> invocation.getArgument(0));

        //when
        insuredPersonsServiceTest.updateInsuredPerson(toUpdateInsuredPerson);

        //then
        verify(insuredPersonRepository, times(1)).save(argThat(insuredPerson ->
                "Adam Novák".equals(insuredPerson.getName()) &&
                        "Nová 3".equals(insuredPerson.getStreet()) &&
                        "Brno".equals(insuredPerson.getCity()) &&
                        "adam@novak.cz".equals(insuredPerson.getEmail()) &&
                        "111111111".equals(insuredPerson.getPhoneNumber())));

        InsuredPerson updatedInsuredPerson = insuredPersonRepository.findById(1L).get();
        assertThat(updatedInsuredPerson.getId()).isEqualTo(1L);
        assertThat(updatedInsuredPerson.getName()).isEqualTo("Adam Novák");
        assertThat(updatedInsuredPerson.getStreet()).isEqualTo("Nová 3");
        assertThat(updatedInsuredPerson.getCity()).isEqualTo("Brno");
        assertThat(updatedInsuredPerson.getEmail()).isEqualTo("adam@novak.cz");
        assertThat(updatedInsuredPerson.getPhoneNumber()).isEqualTo("111111111");
    }

    @Test
    void willThrowExceptionInsuredPersonNotFound() {
        //given
        InsuredPerson insuredPerson = new InsuredPerson(
                1L,
                "Jan Novák",
                "Nová 1",
                "Praha",
                "jan@novak.cz",
                "000000000"
        );
        when(insuredPersonRepository.findById(insuredPerson.getId())).thenReturn(Optional.empty());
        //when

        //then
        assertThatThrownBy(() -> insuredPersonsServiceTest.updateInsuredPerson(insuredPerson))
                .isInstanceOf(InsuredPersonNotFoundException.class)
                .hasMessageContaining("Pojištěnec s ID: " + insuredPerson.getId() + " nenalezen.");

        verify(insuredPersonRepository, never()).save(insuredPerson);
    }

    @Test
    void shouldDeleteInsuredPerson() {
        //given
        InsuredPerson insuredPerson = new InsuredPerson(
                1L,
                "Jan Novák",
                "Nová 1",
                "Praha",
                "jan@novak.cz",
                "000000000"
        );
        // Mock the behavior of the repository's deleteById method
        doNothing().when(insuredPersonRepository).deleteById(insuredPerson.getId());
        //when
        //then

        assertThatCode(() -> insuredPersonsServiceTest.deleteInsuredPerson(insuredPerson.getId()))
                .doesNotThrowAnyException();
        //verify if repository´s method deleteById was called with ID eq = equal to ID of Insured person,
        //times(1) = do it once
        verify(insuredPersonRepository, times(1)).deleteById(eq(insuredPerson.getId()));
    }

    @Test
    void canGetAllInsurance() {
        //when
        insuredPersonsServiceTest.getAllInsurance();
        //then
        verify(insuranceRepository).findAll();
    }

    @Test
    void canSaveInsurance() {
        //given
        Insurance insurance = new Insurance(
                1L,
                "Pojištění zdraví",
                1000000,
                "Životní pojištění",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 31)
        );

        InsuredPerson insuredPerson = new InsuredPerson(
                1L,
                "Jan Novák",
                "Nová 1",
                "Praha",
                "jan@novak.cz",
                "000000000"
        );

        //when
        insuredPersonsServiceTest.saveInsurance(insurance, insuredPerson);

        //then
        ArgumentCaptor<Insurance> insuranceArgumentCaptor = ArgumentCaptor.forClass(Insurance.class);
        //verify if repository saves argument captured by ArgumentCaptor
        verify(insuranceRepository).save(insuranceArgumentCaptor.capture());

        Insurance capturedInsurance = insuranceArgumentCaptor.getValue();

        assertEquals(capturedInsurance.getId(), insurance.getId());
        assertEquals(capturedInsurance.getType(), insurance.getType());
        assertEquals(capturedInsurance.getAmount(), insurance.getAmount());
        assertEquals(capturedInsurance.getSubjectOfInsurance(), insurance.getSubjectOfInsurance());
        assertEquals(capturedInsurance.getValidFrom(), insurance.getValidFrom());
        assertEquals(capturedInsurance.getValidTo(), insurance.getValidTo());
        assertEquals(capturedInsurance.getInsuredPerson(), insuredPerson);
        assertEquals(insuredPerson.getAllInsurance().get(0), capturedInsurance);
    }

    @Test
    void canGetInsuranceById() throws InsuranceNotFoundException {
        //given
        //test id value
        Long id = 1L;
        Insurance expectedInsurance = new Insurance(
                1L,
                "Pojištění zdraví",
                1000000,
                "Životní pojištění",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 31)
        );
        //when(...): sets up an expectation for a method call on the mock object
        //if exists an entry in database by id then return optional type (used to represent a value
        //that may or may not be present) of Insurance
        when(insuranceRepository.findById(id)).thenReturn(Optional.of(expectedInsurance));
        //when
        //get actual insurance from repo by id
        Insurance actualInsurance = insuredPersonsServiceTest.getInsuranceById(id);

        //then
        //check if actual insurance doesn´t point to null and check if equals to expected insurance
        assertThat(actualInsurance).isNotNull().isEqualTo(expectedInsurance);
    }

    @Test
    void canUpdateInsurance() throws InsuranceNotFoundException {
        //given

        //instance of Insurance we want to update
        Insurance toUpdateInsurance = new Insurance();
        toUpdateInsurance.setId(1L);
        toUpdateInsurance.setType("Pojištění majetku");
        toUpdateInsurance.setAmount(10000);
        toUpdateInsurance.setSubjectOfInsurance("Dům");
        toUpdateInsurance.setValidFrom(LocalDate.of(2024, 1, 1));
        toUpdateInsurance.setValidTo(LocalDate.of(2024, 1, 31));

        //instance of existing Insurance
        Insurance existingInsurance = new Insurance();
        existingInsurance.setId(1L);
        existingInsurance.setType("Pojištění zdraví");
        existingInsurance.setAmount(1000000);
        existingInsurance.setSubjectOfInsurance("Životní pojištění");
        existingInsurance.setValidFrom(LocalDate.of(2023, 12, 1));
        existingInsurance.setValidTo(LocalDate.of(2023, 12, 31));

        when(insuranceRepository.findById(1L)).thenReturn(Optional.of(existingInsurance));
        //save(argThat(...)): specifies the expected method call (save) with an argument that matches the provided argThat matcher.
        //The argThat matcher allows you to define a custom argument matcher.
        //thenAnswer(...): Specifies the behavior to be executed when the specified method is called. In this case,
        //it uses a lambda expression to return the argument passed to the save method (invocation.getArgument(0)).
        //This is a way to simulate the behavior of the save method by returning the Insurance that was passed to it.
        //without actually persisting it to the database during the test.
        when(insuranceRepository.save(argThat(insurance ->
                "Pojištění majetku".equals(insurance.getType()) &&
                        10000 == insurance.getAmount() &&
                        "Dům".equals(insurance.getSubjectOfInsurance()) &&
                        LocalDate.of(2024, 1, 1).equals(insurance.getValidFrom()) &&
                        LocalDate.of(2024, 1, 31).equals(insurance.getValidTo())
        ))).thenAnswer(invocation -> invocation.getArgument(0));

        //when
        insuredPersonsServiceTest.updateInsurance(toUpdateInsurance);

        //then
        verify(insuranceRepository, times(1)).save(argThat(insurance ->
                "Pojištění majetku".equals(insurance.getType()) &&
                        Integer.valueOf(10000).equals(insurance.getAmount()) &&
                        "Dům".equals(insurance.getSubjectOfInsurance()) &&
                        LocalDate.of(2024, 1, 1).equals(insurance.getValidFrom()) &&
                        LocalDate.of(2024, 1, 31).equals(insurance.getValidTo())
        ));

        Insurance updatedInsurance = insuranceRepository.findById(1L).get();
        assertThat(updatedInsurance.getId()).isEqualTo(1L);
        assertThat(updatedInsurance.getType()).isEqualTo("Pojištění majetku");
        assertThat(updatedInsurance.getAmount()).isEqualTo(10000);
        assertThat(updatedInsurance.getSubjectOfInsurance()).isEqualTo("Dům");
        assertThat(updatedInsurance.getValidFrom()).isEqualTo(LocalDate.of(2024, 1, 1));
        assertThat(updatedInsurance.getValidTo()).isEqualTo(LocalDate.of(2024, 1, 31));
    }

    @Test
    void willThrowExceptionInsuranceNotFound() {
        //given
        Insurance insurance = new Insurance(
                1L,
                "Pojištění zdraví",
                1000000,
                "Životní pojištění",
                LocalDate.of(2023, 12, 1),
                LocalDate.of(2023, 12, 31)
        );
        when(insuranceRepository.findById(insurance.getId())).thenReturn(Optional.empty());
        //when

        //then
        assertThatThrownBy(() -> insuredPersonsServiceTest.updateInsurance(insurance))
                .isInstanceOf(InsuranceNotFoundException.class)
                .hasMessageContaining("Pojištění s ID: " + insurance.getId() + " nenalezeno.");

        verify(insuranceRepository, never()).save(insurance);
    }

    @Test
    void deleteInsurance() {
        //given
        Insurance insurance = new Insurance(
                1L,
                "Pojištění zdraví",
                1000000,
                "Životní pojištění",
                LocalDate.of(2023, 12, 1),
                LocalDate.of(2023, 12, 31)
        );
        // Mock the behavior of the repository's deleteById method
        doNothing().when(insuranceRepository).deleteById(insurance.getId());
        //when
        //then

        assertThatCode(() -> insuredPersonsServiceTest.deleteInsurance(insurance.getId()))
                .doesNotThrowAnyException();
        //verify if repository´s method deleteById was called with ID eq = equal to ID of Insured person,
        //times(1) = do it once
        verify(insuranceRepository, times(1)).deleteById(eq(insurance.getId()));
    }
}