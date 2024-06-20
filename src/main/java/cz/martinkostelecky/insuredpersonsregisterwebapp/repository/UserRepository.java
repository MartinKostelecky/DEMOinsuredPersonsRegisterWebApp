package cz.martinkostelecky.insuredpersonsregisterwebapp.repository;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("" + "SELECT CASE WHEN COUNT(u) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM User u " +
            "WHERE u.email = ?1")

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}

