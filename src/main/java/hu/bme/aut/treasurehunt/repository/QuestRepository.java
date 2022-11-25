package hu.bme.aut.treasurehunt.repository;

import hu.bme.aut.treasurehunt.domain.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {
}
