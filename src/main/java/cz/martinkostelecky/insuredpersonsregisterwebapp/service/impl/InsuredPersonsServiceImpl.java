package cz.martinkostelecky.insuredpersonsregisterwebapp.service.impl;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.Insurance;
import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.InsuredPerson;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.EmailAlreadyTakenException;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.InsuranceNotFoundException;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.InsuredPersonNotFoundException;
import cz.martinkostelecky.insuredpersonsregisterwebapp.repository.InsuranceRepository;
import cz.martinkostelecky.insuredpersonsregisterwebapp.repository.InsuredPersonRepository;
import cz.martinkostelecky.insuredpersonsregisterwebapp.service.InsuredPersonsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation class of Insured person methods
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InsuredPersonsServiceImpl implements InsuredPersonsService {
    /**
     * Class attributes
     */
    private final InsuredPersonRepository insuredPersonRepository;
    private final InsuranceRepository insuranceRepository;

    /**
     * Finds all Insured persons in database
     *
     * @return List of Insured persons in database
     */
    @Override
    public List<InsuredPerson> getAllInsuredPerson() {
        return insuredPersonRepository.findAll();
    }

    /**
     * Save of Insured person
     *
     * @param insuredPerson
     */
    @Override
    public void saveInsuredPerson(InsuredPerson insuredPerson) throws EmailAlreadyTakenException {
        Boolean existsEmail = insuredPersonRepository.existsByEmail(insuredPerson.getEmail());
        if (existsEmail) {
            throw new EmailAlreadyTakenException("E-mail " + insuredPerson.getEmail() + " již patří jinému pojištěnému.");
        }
        insuredPersonRepository.save(insuredPerson);
        log.info("Insured person {} saved.", insuredPerson);
    }

    /**
     * Finds Insured person by id or throws InsuredPersonNotFoundException
     *
     * @param id id of Insured person
     * @return Insured person by id
     */
    @Override
    public InsuredPerson getInsuredPersonById(Long id) throws InsuredPersonNotFoundException {
        Optional<InsuredPerson> optionalInsuredPerson = insuredPersonRepository.findById(id);
        return optionalInsuredPerson.orElseThrow(() -> new InsuredPersonNotFoundException("Pojištěný nenalezen."));
    }

    /**
     * Update of Insured person
     *
     * @param insuredPerson
     * @return save of Insured person
     */
    @Override
    public void updateInsuredPerson(InsuredPerson insuredPerson) throws EmailAlreadyTakenException, InsuredPersonNotFoundException {
        Optional<InsuredPerson> optionalExistingInsuredPerson = insuredPersonRepository.findById(insuredPerson.getId());
        Boolean existsEmail = insuredPersonRepository.existsByEmail(insuredPerson.getEmail());
        //get existing Insured person and update it if there is any
        if (optionalExistingInsuredPerson.isPresent()) {
            InsuredPerson existingInsuredPerson = optionalExistingInsuredPerson.get();
            existingInsuredPerson.setId(insuredPerson.getId());
            existingInsuredPerson.setName(insuredPerson.getName());
            existingInsuredPerson.setStreet(insuredPerson.getStreet());
            existingInsuredPerson.setCity(insuredPerson.getCity());
            // check if updated person´s e-mail equals existing person´s e-mail and if the e-mail doesn´t
            // belong to another Insured person
            if (!existingInsuredPerson.getEmail().equals(insuredPerson.getEmail()) && existsEmail) {
                throw new EmailAlreadyTakenException("E-mail " + insuredPerson.getEmail() + " již patří jinému pojištěnému.");
            } else {
                existingInsuredPerson.setEmail(insuredPerson.getEmail());
            }
            existingInsuredPerson.setPhoneNumber(insuredPerson.getPhoneNumber());
            insuredPersonRepository.save(existingInsuredPerson);
            log.info("Insured person id: {} updated.", insuredPerson.getId());
        } else {
            // Handle the case where the InsuredPerson with the given ID is not found
            throw new InsuredPersonNotFoundException("Pojištěnec s ID: " + insuredPerson.getId() + " nenalezen.");
            //throw new EntityNotFoundException("Pojištěnec s ID: " + insuredPerson.getId() + " nenalezen.");
        }
    }

    /**
     * Delete Insured person by id
     *
     * @param id id of Insured person
     */
    @Override
    public void deleteInsuredPerson(Long id) {

        insuredPersonRepository.deleteById(id);
        log.info("Insured person id: {} deleted.", id);
    }

    /**
     * Finds all insurances in database
     */
    @Override
    public void getAllInsurance() {
        insuranceRepository.findAll();
    }

    /**
     * Save insurance of Insured person
     *
     * @param insurance
     * @param insuredPerson
     */
    @Override
    public void saveInsurance(Insurance insurance, InsuredPerson insuredPerson) {
        Insurance insuranceToSave = new Insurance();

        insuranceToSave.setId(insurance.getId());
        insuranceToSave.setType(insurance.getType());
        insuranceToSave.setAmount(insurance.getAmount());
        insuranceToSave.setSubjectOfInsurance(insurance.getSubjectOfInsurance());
        insuranceToSave.setValidFrom(insurance.getValidFrom());
        insuranceToSave.setValidTo(insurance.getValidTo());
        insuranceToSave.setInsuredPerson(insuredPerson);
        insuredPerson.getAllInsurance().add(insuranceToSave);

        insuranceRepository.save(insuranceToSave);
        log.info("Insurance id: {} of insured person id: {} saved.", insurance.getId(), insuredPerson.getId());
    }

    /**
     * Find insurance by id or throws InsuranceNotFoundException
     *
     * @param id of Insurance
     * @return Insurance by id
     */
    @Override
    public Insurance getInsuranceById(Long id) throws InsuranceNotFoundException {
        Optional<Insurance> optionalInsurance = insuranceRepository.findById(id);
        return optionalInsurance.orElseThrow(() -> new InsuranceNotFoundException("Pojištění nenalezeno."));
    }

    /**
     * Save updated Insurance
     *
     * @param insurance
     * @return save of Insurance
     */
    @Override
    public void updateInsurance(Insurance insurance) throws InsuranceNotFoundException {
        Optional<Insurance> optionalExistingInsurance = insuranceRepository.findById(insurance.getId());

        if (optionalExistingInsurance.isPresent()) {
            Insurance existingInsurance = optionalExistingInsurance.get();
            existingInsurance.setId(insurance.getId());
            existingInsurance.setType(insurance.getType());
            existingInsurance.setAmount(insurance.getAmount());
            existingInsurance.setSubjectOfInsurance(insurance.getSubjectOfInsurance());
            existingInsurance.setValidFrom(insurance.getValidFrom());
            existingInsurance.setValidTo(insurance.getValidTo());
            insuranceRepository.save(existingInsurance);
            log.info("Insurance id: {} updated.", insurance.getId());
        } else {
            // Handle the case where the InsuredPerson with the given ID is not found
            throw new InsuranceNotFoundException("Pojištění s ID: " + insurance.getId() + " nenalezeno.");
            //throw new EntityNotFoundException("Pojištění s ID: " + insurance.getId() + " nenalezeno.");
        }
    }

    /**
     * Delete of Insurance by id
     *
     * @param id id of Insurance
     */
    @Override
    public void deleteInsurance(Long id) {

        insuranceRepository.deleteById(id);
        log.info("Insurance id: {} deleted.", id);
    }
}

