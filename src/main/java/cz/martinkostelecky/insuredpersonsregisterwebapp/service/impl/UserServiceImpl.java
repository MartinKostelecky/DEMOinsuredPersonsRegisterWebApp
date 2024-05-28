package cz.martinkostelecky.insuredpersonsregisterwebapp.service.impl;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.User;
import cz.martinkostelecky.insuredpersonsregisterwebapp.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String EXISTING_EMAIL = "admin@admin.cz";

    public Optional<User> findByEmail(String email) {
        //TODO find in database

        if (EXISTING_EMAIL.equalsIgnoreCase(email)) {

            //var can be used as local variable with explicit initialization, cannot be used as a global or instance variable
            //as well as with generics and in lambda expressions
            var user = new User();
            user.setId(1L);
            user.setEmail(email);
            user.setPassword("$2a$12$Le20BfbwVnLF1hDQgqRIV.Py1PWpBZjH04Kyye8t8UR2XuwYoBapC");
            user.setRole("ADMIN");

            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }
}
