package cz.martinkostelecky.insuredpersonsregisterwebapp.repository;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}

