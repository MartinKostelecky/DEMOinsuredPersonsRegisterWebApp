package cz.martinkostelecky.insuredpersonsregisterwebapp.controller;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.User;
import cz.martinkostelecky.insuredpersonsregisterwebapp.service.AuthService;
import cz.martinkostelecky.insuredpersonsregisterwebapp.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserServiceImpl userServiceImpl;

    //TODO javadoc
    //login form
    @RequestMapping(value = "/login", method = GET)
    public String loginPage() {
        return "login";
    }

    /**
     * Handle authentication of User
     * @param username
     * @param password
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/authenticate", method = POST)
    public String authenticate(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               RedirectAttributes redirectAttributes) {

        Optional<User> userOptional = userServiceImpl.findByEmail(username);

        if (userOptional.isEmpty()) {
            redirectAttributes.addAttribute("error", "Invalid username and password");
            return "redirect:/login";
        }

        authService.attemptLogin(username, password);

        redirectAttributes.addAttribute("success", "You have been logged in successfully");
        return "redirect:/insuredpersons";
    }
}

