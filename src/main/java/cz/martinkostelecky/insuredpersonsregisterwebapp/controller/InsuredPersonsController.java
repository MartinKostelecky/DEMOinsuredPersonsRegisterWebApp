package cz.martinkostelecky.insuredpersonsregisterwebapp.controller;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.Insurance;
import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.InsuredPerson;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.EmailAlreadyTakenException;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.InsuranceNotFoundException;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.InsuredPersonNotFoundException;
import cz.martinkostelecky.insuredpersonsregisterwebapp.service.InsuredPersonsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Insured person controller class
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class InsuredPersonsController {

    /**
     * Class attributes
     */
    private final InsuredPersonsService insuredPersonsService;

    /**
     * handler method to handle list of pojistenci and return model and view
     *
     * @param model list of insured persons
     * @return insuredpersons page
     */
    @RequestMapping(value = "/insuredpersons", method = GET)
    public String listInsuredPerson(Model model) {
        model.addAttribute("insuredPersons", insuredPersonsService.getAllInsuredPerson());
        return "insuredpersons";
    }

    /**
     * handler method to create new Insured person in database
     *
     * @param model insured person entity
     * @return create_insuredperson page
     */
    @RequestMapping(value = "/insuredpersons/new", method = GET)
    public String createInsuredPersonForm(Model model) {
        //creates new object of Insured person to hold data from form
        InsuredPerson insuredPerson = new InsuredPerson();
        model.addAttribute("insuredPerson", insuredPerson);
        return "create_insuredperson";
    }

    /**
     * handler method to save Insured person
     *
     * @param insuredPerson
     * @return redirects to insuredpersons page
     */
    @RequestMapping(value = "/insuredpersons", method = POST)
    public String saveInsuredPerson(@Valid @ModelAttribute("insuredPerson") InsuredPerson insuredPerson, BindingResult bindingResult) throws EmailAlreadyTakenException {
        if (bindingResult.hasErrors()) {
            return "create_insuredperson";
        }
        insuredPersonsService.saveInsuredPerson(insuredPerson);
        log.info("Insurance list of insured person id: " + insuredPerson.getId() + " created.");
        return "redirect:/insuredpersons";
    }

    /**
     * Editing of Insured person
     *
     * @param id    id of Insured person
     * @param model displays data of Insured person in view
     * @return edit form of Pojistenec
     */
    @RequestMapping(value = "/insuredpersons/edit/{id}", method = GET)
    public String editInsuredPerson(@PathVariable Long id, Model model) throws InsuredPersonNotFoundException {
        model.addAttribute("insuredPerson", insuredPersonsService.getInsuredPersonById(id));
        return "edit_insuredperson";
    }

    /**
     * Update of existing Insured person
     *
     * @param id            id of Insured person
     * @param insuredPerson object Insured person of model
     * @return redirects to the list of Insured persons
     */
    @RequestMapping(value = "/insuredpersons/{id}", method = POST)
    public String updateInsuredPerson(@PathVariable Long id,
                                      @ModelAttribute("insuredPerson") InsuredPerson insuredPerson) throws InsuredPersonNotFoundException, EmailAlreadyTakenException {
        //get Insured person from database by id
        insuredPerson.setId(id);
        //save updated Insured person object
        insuredPersonsService.updateInsuredPerson(insuredPerson);
        return "redirect:/insuredpersons";
    }

    /**
     * Delete Insured person from database
     *
     * @param id id of Insured person
     * @return redirects to the list of Insured persons
     */
    @RequestMapping(value = "/insuredpersons/{id}", method = GET)
    public String deleteInsuredPerson(@PathVariable Long id) {
        insuredPersonsService.deleteInsuredPerson(id);
        return "redirect:/insuredpersons";
    }

    /**
     * Displays page with InsuredPerson data and insurances
     *
     * @param model displays data of Pojistenec in view
     * @return page with details of Pojistenec
     */
    @RequestMapping(value = "/insuredpersons/detail/{id}", method = GET)
    public String detailInsuredPerson(Model model, InsuredPerson insuredPerson) throws InsuredPersonNotFoundException {
        model.addAttribute("insuredPerson", insuredPersonsService.getInsuredPersonById(insuredPerson.getId()));
        if (insuredPersonsService.getInsuredPersonById(insuredPerson.getId()).getAllInsurance() != null) {
            model.addAttribute("allInsurance", insuredPersonsService.getInsuredPersonById(insuredPerson.getId())
                    .getAllInsurance());
            return "detail_insuredperson";
        }
        return "detail_insuredperson";
    }

    /**
     * handler method to create new Insurance in database
     *
     * @param id    id of insured person
     * @param model
     * @return create_pojisteni page
     */
    @RequestMapping(value = "/insuredpersons/new/{id}", method = GET)
    public String createInsuranceForm(@PathVariable Long id, Model model) throws InsuredPersonNotFoundException {
        //creates new object of Insurance to hold data from form
        Insurance insurance = new Insurance();
        model.addAttribute("individualInsurance", insurance);
        model.addAttribute("insuredPerson", insuredPersonsService.getInsuredPersonById(id));
        return "create_insurance";
    }

    /**
     * handler method to save Insurance
     *
     * @param insuredPerson
     * @param insurance
     * @return detail of Insured person with saved Insurances
     */
    @RequestMapping(value = "/insuredpersons/detail/{id}", method = POST)
    public String saveInsurance(@PathVariable Long id, Model model,
                                @ModelAttribute("insuredPerson") InsuredPerson insuredPerson,
                                @Valid @ModelAttribute("individualInsurance") Insurance insurance,
                                BindingResult bindingResult) throws InsuredPersonNotFoundException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("insuredPerson", insuredPersonsService.getInsuredPersonById(id));
            return "create_insurance";
        }
        insuredPersonsService.saveInsurance(insurance, insuredPerson);
        log.info("Insurance saved.");
        //??? log.info("Insured person id: " + insuredPerson.getId() + " Insurance list size: " + insuredPerson.getAllInsurance().size());
        return "redirect:/insuredpersons/detail/{id}";
    }

    /**
     * Delete of Insurance
     *
     * @param idPerson
     * @param idInsurance
     * @return detail of Insured person
     */
    @RequestMapping(value = "/insuredpersons/detail/{idPerson}/insurance/{idInsurance}", method = GET)
    public String deleteInsurance(@PathVariable Long idPerson,
                                  @PathVariable Long idInsurance) throws InsuredPersonNotFoundException {
        insuredPersonsService.getInsuredPersonById(idPerson);
        insuredPersonsService.deleteInsurance(idInsurance);
        return "redirect:/insuredpersons/detail/{idPerson}";
    }

    /**
     * create Insurance edit form
     *
     * @param idPerson
     * @param idInsurance
     * @param model
     * @return edit Insurance form template
     */
    @RequestMapping(value = "/insuredpersons/detail/{idPerson}/edit/{idInsurance}", method = GET)
    public String editInsurance(@PathVariable Long idPerson, @PathVariable Long idInsurance, Model model) throws InsuredPersonNotFoundException, InsuranceNotFoundException {
        model.addAttribute("insuredPerson", insuredPersonsService.getInsuredPersonById(idPerson));
        model.addAttribute("individualInsurance", insuredPersonsService.getInsuranceById(idInsurance));
        return "edit_insurance";
    }

    /**
     * Update of existing Insurance
     *
     * @param idInsurance
     * @param idPerson
     * @param insurance
     * @return detail of Insured person with insurances
     */
    @RequestMapping(value = "/insuredpersons/detail/{idPerson}/insurance/{idInsurance}", method = POST)
    public String updateInsurance(@PathVariable Long idInsurance, @PathVariable Long idPerson,
                                  @ModelAttribute("individualInsurance") Insurance insurance) throws InsuredPersonNotFoundException, InsuranceNotFoundException {
        insuredPersonsService.getInsuredPersonById(idPerson);
        //get Insurance from database by id
        insurance.setId(idInsurance);
        //save updated object of Insurance
        insuredPersonsService.updateInsurance(insurance);
        return "redirect:/insuredpersons/detail/{idPerson}";
    }

    /**
     * Redirection to about page
     *
     * @return about page template
     */
    @RequestMapping(value = "/about", method = GET)
    public String showAboutPage() {
        return "about";
    }

}

