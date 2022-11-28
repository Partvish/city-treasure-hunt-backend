package hu.bme.aut.treasurehunt.service;

import hu.bme.aut.treasurehunt.domain.Quest;
import hu.bme.aut.treasurehunt.domain.Suggestion;
import hu.bme.aut.treasurehunt.domain.User;
import hu.bme.aut.treasurehunt.model.dtos.SuggestionDto;
import hu.bme.aut.treasurehunt.repository.SuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuggestionService {
    @Autowired
    private UserService userService;

    @Autowired
    private QuestService questService;

    @Autowired
    SuggestionRepository suggestionRepository;

    public Optional<Suggestion> makeSuggestion(SuggestionDto suggestionDto){
        Optional<User> user = userService.getUser(suggestionDto.userId);
        if(user.isEmpty())
            return Optional.empty();

        Optional<Quest> quest = questService.getQuest(suggestionDto.questId);
        if(quest.isEmpty())
            return Optional.empty();
        Suggestion suggestion = new Suggestion();
        suggestion.setDescription(suggestionDto.description);
        suggestion.setQuest(quest.get());
        suggestion.setUser(user.get());
        suggestion.setId(-1);
        return Optional.of(suggestionRepository.save(suggestion));
    }

    public List<Suggestion> getSuggestions(){
        return suggestionRepository.findAll();
    }

    public Optional<Suggestion> getSuggestion(Long id){
        return suggestionRepository.findById(id);
    }
}
