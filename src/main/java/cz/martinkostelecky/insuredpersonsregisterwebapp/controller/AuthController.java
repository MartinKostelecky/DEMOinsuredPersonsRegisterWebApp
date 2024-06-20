package cz.martinkostelecky.insuredpersonsregisterwebapp.controller;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.User;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.EmailAlreadyTakenException;
import cz.martinkostelecky.insuredpersonsregisterwebapp.repository.UserRepository;
import cz.martinkostelecky.insuredpersonsregisterwebapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final UserService userService;
    private final UserRepository userRepository;

    /**
     * render login page
     * @return login template
     */
    @RequestMapping(value = "/login", method = GET)
    public String loginPage() {
        return "login";
    }

    /**
     * Handle authentication of User
     *
     * @param email
     * @param password
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/authenticate", method = POST)
    public String authenticate(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               HttpServletRequest httpServletRequest,
                               HttpServletResponse httpServletResponse,
                               RedirectAttributes redirectAttributes) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            redirectAttributes.addAttribute("error", "Chybný e-mail nebo heslo.");
            return "redirect:/login";
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                UsernamePasswordAuthenticationToken.unauthenticated(email, password);

        Authentication authentication = daoAuthenticationProvider.authenticate(authenticationToken);
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);
        new HttpSessionSecurityContextRepository().saveContext(context, httpServletRequest, httpServletResponse);

        redirectAttributes.addAttribute("success");
        return "redirect:/insuredpersons";
    }

    @RequestMapping(value = "/register", method = GET)
    public String showRegisterForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @RequestMapping(value = "/register", method = POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) throws EmailAlreadyTakenException {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/login";
    }
}

