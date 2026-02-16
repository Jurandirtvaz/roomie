package br.edu.ufape.roomie.repository;

import br.edu.ufape.roomie.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("SELECT p FROM Property p " +
            "JOIN p.address a " +
            "WHERE (:city IS NULL OR a.city = :city) " +
            "AND (:state IS NULL OR a.state = :state) " +
            "AND (:district IS NULL OR a.district = :district) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:availableVacancies IS NULL OR p.availableVacancies >= :availableVacancies) ")
    List<Property> findWithFilters(
        @Param("city") String city,
        @Param("state") String state,
        @Param("district") String district,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        @Param("availableVacancies") Integer availableVacancies
    );
}
