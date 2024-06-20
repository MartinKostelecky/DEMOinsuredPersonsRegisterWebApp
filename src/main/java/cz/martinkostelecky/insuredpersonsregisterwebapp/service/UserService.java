package cz.martinkostelecky.insuredpersonsregisterwebapp.service;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.User;
import cz.martinkostelecky.insuredpersonsregisterwebapp.exception.EmailAlreadyTakenException;

public interface UserService {

    void saveUser(User user) throws EmailAlreadyTakenException;
}
