package hu.bme.aut.treasurehunt.repository;

import hu.bme.aut.treasurehunt.domain.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
}
