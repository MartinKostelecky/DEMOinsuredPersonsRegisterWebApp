package cz.martinkostelecky.insuredpersonsregisterwebapp.service.impl;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.Role;
import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.User;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.EmailAlreadyTakenException;
import cz.martinkostelecky.insuredpersonsregisterwebapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {

        userService = new UserServiceImpl(userRepository, new BCryptPasswordEncoder());
    }

    @Test
    void canSaveUser() throws EmailAlreadyTakenException {
        //given
        User user = new User(
                1L,
                "user",
                "test@user.cz",
                "password",
                "password",
                Role.USER);

        //when
        userService.saveUser(user);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        //then
        assertEquals(capturedUser.getEmail(), user.getEmail());
    }

}