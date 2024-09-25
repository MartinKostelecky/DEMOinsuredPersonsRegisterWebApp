package cz.martinkostelecky.insuredpersonsregisterwebapp.service;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.Insurance;
import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.InsuredPerson;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.EmailAlreadyTakenException;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.InsuranceNotFoundException;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.InsuredPersonNotFoundException;

import java.util.List;
public interface InsuredPersonsService {
    List<InsuredPerson> getAllInsuredPerson();
    void saveInsuredPerson(InsuredPerson insuredPerson) throws EmailAlreadyTakenException;
    InsuredPerson getInsuredPersonById(Long id) throws InsuredPersonNotFoundException;
    void updateInsuredPerson(InsuredPerson insuredPerson) throws EmailAlreadyTakenException, InsuredPersonNotFoundException;
    void deleteInsuredPerson(Long id);
    void getAllInsurance();
    void saveInsurance(Insurance insurance, InsuredPerson insuredPerson);
    Insurance getInsuranceById(Long id) throws InsuranceNotFoundException;
    void updateInsurance(Insurance insurance) throws InsuranceNotFoundException;
    void deleteInsurance(Long id);
}