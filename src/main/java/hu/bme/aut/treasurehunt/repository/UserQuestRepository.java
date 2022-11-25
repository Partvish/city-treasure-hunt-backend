package hu.bme.aut.treasurehunt.repository;

import hu.bme.aut.treasurehunt.domain.Quest;
import hu.bme.aut.treasurehunt.domain.User;
import hu.bme.aut.treasurehunt.domain.UserQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserQuestRepository extends JpaRepository<UserQuest, Long> {
    public Optional<UserQuest> findByQuestAndUser(Quest quest, User user);
}
