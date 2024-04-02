package cz.martinkostelecky.insuredpersonsregisterwebapp.controller;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.Insurance;
import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.InsuredPerson;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.EmailAlreadyTakenException;
import cz.martinkostelecky.insuredpersonsregisterwebapp.service.InsuredPersonsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Insured person controller class
 */
@Controller
public class InsuredPersonsController {
    /**
     * Class attributes
     */
    @Autowired
    private InsuredPersonsService insuredPersonsService;

    /**
     * Constructor
     *
     * @param insuredPersonsService
     */
    public InsuredPersonsController(InsuredPersonsService insuredPersonsService) {
        super();
        this.insuredPersonsService = insuredPersonsService;
    }

    /**
     * handler method to handle list of pojistenci and return model and view
     *
     * @param model
     * @return insuredpersons page
     */
    @GetMapping("/insuredpersons")
    public String listInsuredPerson(Model model) {
        model.addAttribute("insuredPersons", insuredPersonsService.getAllInsuredPerson());
        return "insuredpersons";
    }

    /**
     * handler method to create new Insured person in database
     *
     * @param model
     * @return create_insuredperson page
     */
    @GetMapping("/insuredpersons/new")
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
    @PostMapping("/insuredpersons")
    public String saveInsuredPerson(@Valid @ModelAttribute("insuredPerson") InsuredPerson insuredPerson, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create_insuredperson";
        }
        insuredPersonsService.saveInsuredPerson(insuredPerson);
        return "redirect:/insuredpersons";
    }

    /**
     * Editing of Insured person
     *
     * @param id    id of Insured person
     * @param model displays data of Insured person in view
     * @return edit form of Pojistenec
     */
    @GetMapping("/insuredpersons/edit/{id}")
    public String editInsuredPerson(@PathVariable Long id, Model model) {
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
    @PostMapping("/insuredpersons/{id}")
    public String updateInsuredPerson(@PathVariable Long id,
                                      @ModelAttribute("insuredPerson") InsuredPerson insuredPerson) {
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
    @GetMapping("/insuredpersons/{id}")
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
    @GetMapping("/insuredpersons/detail/{id}")
    public String detailInsuredPerson(Model model, InsuredPerson insuredPerson) {
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
    @GetMapping("/insuredpersons/new/{id}")
    public String createInsuranceForm(@PathVariable Long id, Model model) {
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
    @PostMapping("/insuredpersons/detail/{id}")
    public String saveInsurance(@PathVariable Long id, Model model,
                                @ModelAttribute("insuredPerson") InsuredPerson insuredPerson,
                                @Valid @ModelAttribute("individualInsurance") Insurance insurance,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("insuredPerson", insuredPersonsService.getInsuredPersonById(id));
            return "create_insurance";
        }
        insuredPersonsService.saveInsurance(insurance, insuredPerson);
        return "redirect:/insuredpersons/detail/{id}";
    }

    /**
     * Delete of Insurance
     *
     * @param idPerson
     * @param idInsurance
     * @return detail of Insured person
     */
    @GetMapping("/insuredpersons/detail/{idPerson}/insurance/{idInsurance}")
    public String deleteInsurance(@PathVariable Long idPerson,
                                  @PathVariable Long idInsurance) {
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
    @GetMapping("/insuredpersons/detail/{idPerson}/edit/{idInsurance}")
    public String editInsurance(@PathVariable Long idPerson, @PathVariable Long idInsurance, Model model) {
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
    @PostMapping("/insuredpersons/detail/{idPerson}/insurance/{idInsurance}")
    public String updateInsurance(@PathVariable Long idInsurance, @PathVariable Long idPerson,
                                  @ModelAttribute("individualInsurance") Insurance insurance) {
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
    @GetMapping("/about")
    public String showAboutPage() {
        return "about";
    }

    @GetMapping("/error")
    public String errorPage() {
        //TODO nezobrazuje error stránku
        throw new EmailAlreadyTakenException("Tento email už patří někomu jinému.");
    }
}

