package cz.martinkostelecky.insuredpersonsregisterwebapp.service.impl;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.Insurance;
import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.InsuredPerson;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.ApiRequestException;
import cz.martinkostelecky.insuredpersonsregisterwebapp.repository.InsuranceRepository;
import cz.martinkostelecky.insuredpersonsregisterwebapp.repository.InsuredPersonRepository;
import cz.martinkostelecky.insuredpersonsregisterwebapp.service.InsuredPersonsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation class of Insured person methods
 */
@Service
public class InsuredPersonsServiceImpl implements InsuredPersonsService {
    /**
     * Class attributes
     */
    private InsuredPersonRepository insuredPersonRepository;
    private InsuranceRepository insuranceRepository;
    /**
     * Constructor
     * @param insuredPersonRepository
     */
    public InsuredPersonsServiceImpl(InsuredPersonRepository insuredPersonRepository, InsuranceRepository insuranceRepository) {
        this.insuredPersonRepository = insuredPersonRepository;
        this.insuranceRepository = insuranceRepository;
    }
    /**
     * Finds all Insured persons in database
     * @return List of Insured persons in database
     */
    @Override
    public List<InsuredPerson> getAllInsuredPerson() {
        return insuredPersonRepository.findAll();
    }
    /**
     * Save of Insured person
     * @param insuredPerson
     * @return save of Insured person
     */
    @Override
    public InsuredPerson saveInsuredPerson(InsuredPerson insuredPerson) {
        Boolean existsEmail = insuredPersonRepository.existsByEmail(insuredPerson.getEmail());
        if(existsEmail) {
            throw new ApiRequestException("E-mail " + insuredPerson.getEmail() + " již patří jinému pojištěnému.");
        }
        return insuredPersonRepository.save(insuredPerson);
    }
    /**
     * Finds Insured person by id or return null if not found
     * @param id id of Insured person
     * @return Insured person by id
     */
    @Override
    public InsuredPerson getInsuredPersonById(Long id) {
        Optional<InsuredPerson> optionalInsuredPerson = insuredPersonRepository.findById(id);
        return optionalInsuredPerson.orElse(null);
    }
    /**
     * Update of Insured person
     * @param insuredPerson
     * @return save of Insured person
     */
    @Override
    public InsuredPerson updateInsuredPerson(InsuredPerson insuredPerson) {
        InsuredPerson existingInsuredPerson = insuredPersonRepository.findById(insuredPerson.getId()).get();
        existingInsuredPerson.setId(insuredPerson.getId());
        existingInsuredPerson.setName(insuredPerson.getName());
        existingInsuredPerson.setStreet(insuredPerson.getStreet());
        existingInsuredPerson.setCity(insuredPerson.getCity());
        existingInsuredPerson.setEmail(insuredPerson.getEmail());
        existingInsuredPerson.setPhoneNumber(insuredPerson.getPhoneNumber());
        return insuredPersonRepository.save(existingInsuredPerson);
    }
    /**
     * Delete Insured person by id
     * @param id id of Insured person
     */
    @Override
    public void deleteInsuredPerson(Long id) {
        insuredPersonRepository.deleteById(id);
    }
    /**
     * Finds all insurances in database
     * @return List of insurances
     */
    @Override
    public List<Insurance> getAllInsurance() {
        return insuranceRepository.findAll();
    }

    /**
     * Save insurance of Insured person
     * @param insurance
     * @param insuredPerson
     * @return saved insurance into database
     */
    @Override
    public Insurance saveInsurance(Insurance insurance, InsuredPerson insuredPerson) {
        Insurance insuranceToSave = new Insurance();
        List<Insurance> allInsurance = new ArrayList<>();

        insuranceToSave.setType(insurance.getType());
        insuranceToSave.setAmount(insurance.getAmount());
        insuranceToSave.setSubjectOfInsurance(insurance.getSubjectOfInsurance());
        insuranceToSave.setValidFrom(insurance.getValidFrom());
        insuranceToSave.setValidTo(insurance.getValidTo());
        insuranceToSave.setInsuredPerson(insuredPerson);
        allInsurance.add(insurance);
        insuredPerson.setAllInsurance(allInsurance);

        return insuranceRepository.save(insuranceToSave);
    }
    /**
     * Find insurance by id
     * @param id
     * @return Insurance by id
     */
    @Override
    public Insurance getInsuranceById(Long id) { return insuranceRepository.findById(id).get();
    }
    /**
     * Save updated Insurance
     * @param insurance
     * @return save of Insurance
     */
    @Override
    public Insurance updateInsurance(Insurance insurance) {
        Insurance existingInsurance = insuranceRepository.findById(insurance.getId()).get();
        existingInsurance.setId(insurance.getId());
        existingInsurance.setType(insurance.getType());
        existingInsurance.setAmount(insurance.getAmount());
        existingInsurance.setSubjectOfInsurance(insurance.getSubjectOfInsurance());
        existingInsurance.setValidFrom(insurance.getValidFrom());
        existingInsurance.setValidTo(insurance.getValidTo());
        return insuranceRepository.save(existingInsurance);
    }
    /**
     * Delete of Insurance by id
     * @param id id of Insurance
     */
    @Override
    public void deleteInsurance(Long id) {insuranceRepository.deleteById(id);  }
}

