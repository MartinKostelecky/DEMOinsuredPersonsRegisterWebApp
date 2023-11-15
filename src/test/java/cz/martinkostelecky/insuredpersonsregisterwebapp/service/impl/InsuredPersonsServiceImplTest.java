package cz.martinkostelecky.insuredpersonsregisterwebapp.service.impl;

import cz.martinkostelecky.insuredpersonsregisterwebapp.repository.InsuranceRepository;
import cz.martinkostelecky.insuredpersonsregisterwebapp.repository.InsuredPersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

//using this annotation we don´t need tearDown method and autoCloseable instance
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
        insuredPersonsServiceTest = new InsuredPersonsServiceImpl(insuredPersonRepository,insuranceRepository);
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
    @Disabled
    void saveInsuredPerson() {
    }

    @Test
    @Disabled
    void getInsuredPersonById() {
    }

    @Test
    @Disabled
    void updateInsuredPerson() {
    }

    @Test
    @Disabled
    void deleteInsuredPerson() {
    }

    @Test
    @Disabled
    void getAllInsurance() {
    }

    @Test
    @Disabled
    void saveInsurance() {
    }

    @Test
    @Disabled
    void getInsuranceById() {
    }

    @Test
    @Disabled
    void updateInsurance() {
    }

    @Test
    @Disabled
    void deleteInsurance() {
    }



}