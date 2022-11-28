package hu.bme.aut.treasurehunt.service;

import hu.bme.aut.treasurehunt.domain.Quest;
import hu.bme.aut.treasurehunt.domain.User;
import hu.bme.aut.treasurehunt.model.constants.UserRole;
import hu.bme.aut.treasurehunt.model.dtos.QuestDto;
import hu.bme.aut.treasurehunt.repository.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestService {
    @Autowired
    private QuestRepository questRepository;

    public List<Quest> getQuests() {
        return questRepository.findAll();
    }

    public Optional<Quest> getQuest(Long id) {
        return questRepository.findById(id);
    }

    public Optional<Quest> modifyQuest(QuestDto questDto) {
        Optional<Quest> q = questRepository.findById(questDto.id);
        if(q.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(questRepository.save(new Quest(questDto)));
    }

    public boolean deleteQuest(Long id) {
        Optional<Quest> q = questRepository.findById(id);
        if(q.isEmpty()){
            return false;
        }
        questRepository.deleteById(id);
        return true;
    }

    public Optional<Quest> createQuest(QuestDto questDto) {
        questDto.id = null;
        Quest quest = new Quest(questDto);
        return Optional.of(questRepository.save(quest));
    }
}
