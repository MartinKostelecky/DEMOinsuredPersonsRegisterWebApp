package cz.martinkostelecky.insuredpersonsregisterwebapp.service.impl;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.Role;
import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.User;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.EmailAlreadyTakenException;
import cz.martinkostelecky.insuredpersonsregisterwebapp.repository.UserRepository;
import cz.martinkostelecky.insuredpersonsregisterwebapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user) throws EmailAlreadyTakenException {

        Boolean existsEmail = userRepository.existsByEmail(user.getEmail());
        if (existsEmail) {
            throw new EmailAlreadyTakenException("E-mail " + user.getEmail() + " již patří jinému uživateli.");
        }
        var newUser = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .confirmPassword(passwordEncoder.encode(user.getConfirmPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(newUser);
        log.info("User id: " + newUser.getId() + " created.");
    }
}

