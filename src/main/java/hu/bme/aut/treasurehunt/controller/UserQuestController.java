package hu.bme.aut.treasurehunt.controller;

import hu.bme.aut.treasurehunt.domain.UserQuest;
import hu.bme.aut.treasurehunt.model.constants.UserRole;
import hu.bme.aut.treasurehunt.model.dtos.AcceptUserQuestDto;
import hu.bme.aut.treasurehunt.model.dtos.AnswerUserQuestDto;
import hu.bme.aut.treasurehunt.model.dtos.FinishUserQuestDto;
import hu.bme.aut.treasurehunt.model.dtos.StartedUserQuestDto;
import hu.bme.aut.treasurehunt.service.UserQuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/userquest")
public class UserQuestController {

    @Autowired
    private UserQuestService userQuestService;

    @PostMapping("/accept")
    @Secured(UserRole.Normal)
    public ResponseEntity<?> acceptUserQuest(Principal principal, @RequestBody AcceptUserQuestDto acceptUserQuestDto){
        Optional<UserQuest> userQuest = userQuestService.acceptUserQuest(principal, acceptUserQuestDto);
        if(userQuest.isEmpty())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/abandon/{quest_id}")
    @Secured(UserRole.Normal)
    public ResponseEntity<?> abandonUserQuest(Principal principal, @PathVariable Long quest_id){
        if(userQuestService.abandonUserQuest(principal, quest_id))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/finish/{quest_id}")
    @Secured(UserRole.Normal)
    public ResponseEntity<FinishUserQuestDto> finishUserQuest(Principal principal, @PathVariable Long quest_id, @RequestBody AnswerUserQuestDto answerUserQuestDto){
        if(userQuestService.finishUserQuest(principal, quest_id, answerUserQuestDto)){
            return ResponseEntity.ok(new FinishUserQuestDto("right"));
        }
        return ResponseEntity.ok(new FinishUserQuestDto("wrong"));
    }

    @GetMapping("/{quest_id}")
    @Secured(UserRole.Normal)
    public ResponseEntity<?> getUserQuest(Principal principal, @PathVariable Long quest_id){
        Optional<UserQuest> userQuest = userQuestService.getUserQuest(principal, quest_id);
        if(userQuest.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new StartedUserQuestDto(
                userQuest.get().getQuest().getOptions(),
                userQuest.get().getQuest().getDescription())
        );
    }

    @GetMapping("/state/{quest_id}")
    @Secured(UserRole.Normal)
    public ResponseEntity<?> getUserQuestState(Principal principal, @PathVariable Long quest_id){
        Optional<UserQuest> userQuest = userQuestService.getUserQuest(principal, quest_id);
        if(userQuest.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(String.valueOf(userQuest.get().getState()));
    }

}
