package cz.martinkostelecky.insuredpersonsregisterwebapp.repository;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.InsuredPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface InsuredPersonRepository extends JpaRepository<InsuredPerson, Long> {

    //method annotation containing sql query for execution
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM InsuredPerson p " +
            "WHERE p.email = ?1")
    Boolean existsByEmail(String email);
}
