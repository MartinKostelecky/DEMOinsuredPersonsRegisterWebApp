package cz.martinkostelecky.insuredpersonsregisterwebapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = EmailAlreadyTakenException.class)
    public String handleBadRequestException(EmailAlreadyTakenException e, Model model) {

        model.addAttribute("status", HttpStatus.BAD_REQUEST);
        model.addAttribute("message", e.getMessage());

        return "error";
    }

    @ExceptionHandler(value = InsuredPersonNotFoundException.class)
    public String handleInsuredPersonNotFoundException(InsuredPersonNotFoundException e, Model model) {

        model.addAttribute("status", HttpStatus.NOT_FOUND);
        model.addAttribute("message", e.getMessage());

        return "error";
    }

    @ExceptionHandler(value = InsuranceNotFoundException.class)
    public String handleInsuranceNotFoundException(InsuranceNotFoundException e, Model model) {

        model.addAttribute("status", HttpStatus.NOT_FOUND);
        model.addAttribute("message", e.getMessage());

        return "error";
    }

}
