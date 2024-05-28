package cz.martinkostelecky.insuredpersonsregisterwebapp.service;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);
}
