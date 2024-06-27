package cz.martinkostelecky.insuredpersonsregisterwebapp.repository;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.Role;
import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepositoryTest;

    @Test
    void shouldCheckIfUserExistsByEmail() {
        //given
        User user = getTestUserDetails();

        userRepositoryTest.save(user);
        //when
        boolean expected = userRepositoryTest.existsByEmail(user.getEmail());

        //then
        assertTrue(expected);

    }

    @Test
    void shouldCheckIfUserCanBeFoundByEmail() {
        //given
        User user = getTestUserDetails();

        userRepositoryTest.save(user);

        //when
        Optional<User> foundUser = userRepositoryTest.findByEmail(user.getEmail());

        //then
        assertEquals(user.getEmail(), foundUser.get().getEmail());
    }

    public User getTestUserDetails() {
        String email = "test@user.cz";

        User user = new User(
                1L,
                "user",
                email,
                "password",
                "password",
                Role.USER);

        return user;
    }
}