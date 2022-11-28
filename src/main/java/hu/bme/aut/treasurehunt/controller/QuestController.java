package hu.bme.aut.treasurehunt.controller;

import hu.bme.aut.treasurehunt.domain.Quest;
import hu.bme.aut.treasurehunt.domain.User;
import hu.bme.aut.treasurehunt.model.constants.UserRole;
import hu.bme.aut.treasurehunt.model.dtos.QuestDto;
import hu.bme.aut.treasurehunt.model.dtos.QuestDtoWithoutAnswer;
import hu.bme.aut.treasurehunt.model.dtos.QuestListItem;
import hu.bme.aut.treasurehunt.service.AuthService;
import hu.bme.aut.treasurehunt.service.QuestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quest")
public class QuestController {

    @Autowired
    private QuestService questService;

    @Autowired
    private AuthService authService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/all")
    @Secured({UserRole.Admin, UserRole.Normal})
    public ResponseEntity<List<?>> getAll(Principal principal){
        List<Quest> quests = questService.getQuests();
        if(authService.getRole(principal).equals(UserRole.Admin))
            return ResponseEntity.ok(quests);
        return ResponseEntity.ok(quests.stream().map(QuestListItem::new).toList());
    }

    @GetMapping("{id}")
    @Secured({UserRole.Admin, UserRole.Normal})
    public ResponseEntity<?> getById(Principal principal, @PathVariable Long id){
        Optional<Quest> quest = questService.getQuest(id);
        if(quest.isEmpty())
            return ResponseEntity.notFound().build();
        if(authService.getRole(principal).equals(UserRole.Admin))
            return ResponseEntity.ok(quest.get());
        return ResponseEntity.ok(new QuestDtoWithoutAnswer(quest.get()));
    }

    @PostMapping
    @Secured(UserRole.Admin)
    public ResponseEntity<Quest> create(@RequestBody QuestDto questDto){
        Optional<Quest> quest = questService.createQuest(questDto);
        if(quest.isPresent()){
            return ResponseEntity.ok(quest.get());
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    @Secured(UserRole.Admin)
    public ResponseEntity<Quest> update(@RequestBody QuestDto quest){
        Optional<Quest> q = questService.modifyQuest(quest);
        if(q.isPresent())
            return ResponseEntity.ok(q.get());
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    @Secured(UserRole.Admin)
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(questService.deleteQuest(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
