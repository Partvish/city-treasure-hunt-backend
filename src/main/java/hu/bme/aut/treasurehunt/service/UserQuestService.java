package hu.bme.aut.treasurehunt.service;

import hu.bme.aut.treasurehunt.domain.Quest;
import hu.bme.aut.treasurehunt.domain.User;
import hu.bme.aut.treasurehunt.domain.UserQuest;
import hu.bme.aut.treasurehunt.model.dtos.AcceptUserQuestDto;
import hu.bme.aut.treasurehunt.model.dtos.AnswerUserQuestDto;
import hu.bme.aut.treasurehunt.repository.UserQuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class UserQuestService {
    @Autowired
    private UserService userService;

    @Autowired
    private QuestService questService;

    @Autowired
    private UserQuestRepository userQuestRepository;


    public Optional<UserQuest> acceptUserQuest(Principal principal, AcceptUserQuestDto acceptUserQuestDto) {
        Optional<User> u = userService.getUser(principal);
        Optional<Quest> q = questService.getQuest(acceptUserQuestDto.quest_id);
        if(u.isEmpty())
            return Optional.empty();
        if(q.isEmpty())
            return Optional.empty();
        Quest quest = q.get();
       /* if(Math.abs(quest.getLatitude() - acceptUserQuestDto.latitude) > 2 || Math.abs(quest.getLongitude() - acceptUserQuestDto.longitude) > 2)
            return Optional.empty();*//*
        if(u.get().getUserQuests().stream().anyMatch(e-> e.getQuest().getId().equals(acceptUserQuestDto.quest_id)))
            return Optional.empty();*/
        Optional<UserQuest> uq = userQuestRepository.findByQuestAndUser(quest, u.get());
        if(uq.isPresent())
            return Optional.empty();

        UserQuest userQuest = new UserQuest();
        userQuest.setQuest(quest);
        userQuest.setUser(u.get());
        userQuest.setState(UserQuest.STARTED);

        return Optional.of(userQuestRepository.save(userQuest));
    }

    public boolean finishUserQuest(Principal principal, Long id, AnswerUserQuestDto aq) {
        Optional<UserQuest> uQ = this.getUserQuest(principal, id);
        if(uQ.isEmpty())
            return false;
        UserQuest userQuest = uQ.get();
        if(userQuest.getState() != UserQuest.STARTED)
            return false;
        if(userQuest.getQuest().getAnswer().equals(aq.answer)){
            userQuest.setState(UserQuest.FINISHED);
            userService.addPoint(userQuest.getUser(), userQuest.getQuest().getPoint());
            return true;
        }
        return false;
    }

    public boolean abandonUserQuest(Principal principal, Long id) {
        Optional<UserQuest> userQuest = this.getUserQuest(principal, id);
        if(userQuest.isEmpty())
            return false;
        userQuest.get().setState(UserQuest.ABANDONED);
        userQuestRepository.save(userQuest.get());
        return true;
    }

    public Optional<UserQuest> getUserQuest(Principal principal, Long id){
        Optional<User> u = userService.getUser(principal);
        Optional<Quest> q = questService.getQuest(id);
        if(q.isEmpty())
            return Optional.empty();
        if(u.isEmpty())
            return Optional.empty();
        return userQuestRepository.findByQuestAndUser(q.get(), u.get());
    }
}
