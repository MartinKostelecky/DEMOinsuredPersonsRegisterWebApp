package cz.martinkostelecky.insuredpersonsregisterwebapp.service;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.Insurance;
import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.InsuredPerson;

import java.util.List;
public interface InsuredPersonsService {
    List<InsuredPerson> getAllInsuredPerson();
    InsuredPerson saveInsuredPerson(InsuredPerson insuredPerson);
    InsuredPerson getInsuredPersonById(Long id);
    InsuredPerson updateInsuredPerson(InsuredPerson insuredPerson);
    void deleteInsuredPerson(Long id);
    List<Insurance> getAllInsurance();
    Insurance saveInsurance(Insurance insurance, InsuredPerson insuredPerson);
    Insurance getInsuranceById(Long id);
    Insurance updateInsurance(Insurance insurance);
    void deleteInsurance(Long id);
}