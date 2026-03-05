package br.edu.ufape.roomie.repository;

import br.edu.ufape.roomie.model.Interest;
import br.edu.ufape.roomie.model.Property;
import br.edu.ufape.roomie.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
    boolean existsByStudentAndProperty(User student, Property property);
}