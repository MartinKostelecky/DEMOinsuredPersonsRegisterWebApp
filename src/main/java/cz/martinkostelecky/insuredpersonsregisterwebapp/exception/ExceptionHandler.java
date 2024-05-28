package cz.martinkostelecky.insuredpersonsregisterwebapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = EmailAlreadyTakenException.class)
    public String handleBadRequestException(EmailAlreadyTakenException e, Model model) {

        model.addAttribute("status", HttpStatus.BAD_REQUEST);
        model.addAttribute("message", e.getMessage());

        return "error";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = InsuredPersonNotFoundException.class)
    public String handleInsuredPersonNotFoundException(InsuredPersonNotFoundException e, Model model) {

        model.addAttribute("status", HttpStatus.NOT_FOUND);
        model.addAttribute("message", e.getMessage());

        return "error";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = InsuranceNotFoundException.class)
    public String handleInsuranceNotFoundException(InsuranceNotFoundException e, Model model) {

        model.addAttribute("status", HttpStatus.NOT_FOUND);
        model.addAttribute("message", e.getMessage());

        return "error";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = WrongUsernameOrPasswordException.class)
    public String handleWrongUsernameOrPasswordException(WrongUsernameOrPasswordException e, Model model) {

        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
        model.addAttribute("message", e.getMessage());

        return "error";
    }

}
