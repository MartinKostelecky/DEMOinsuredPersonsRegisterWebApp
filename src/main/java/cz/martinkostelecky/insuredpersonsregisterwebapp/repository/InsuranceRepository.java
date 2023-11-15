package cz.martinkostelecky.insuredpersonsregisterwebapp.repository;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    @Query("" + "SELECT CASE WHEN COUNT(i) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Insurance i " +
            "WHERE i.type = ?1")

    Boolean existsByType(String type);

}
