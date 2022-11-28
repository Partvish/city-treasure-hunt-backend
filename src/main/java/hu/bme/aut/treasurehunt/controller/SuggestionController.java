package hu.bme.aut.treasurehunt.controller;

import hu.bme.aut.treasurehunt.domain.Suggestion;
import hu.bme.aut.treasurehunt.model.constants.UserRole;
import hu.bme.aut.treasurehunt.model.dtos.SuggestionDto;
import hu.bme.aut.treasurehunt.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/suggestion")
public class SuggestionController {

    @Autowired
    private SuggestionService suggestionService;

    @GetMapping("/all")
    @Secured(UserRole.Admin)
    public ResponseEntity<List<Suggestion>> getAll(){
        return ResponseEntity.ok(suggestionService.getSuggestions());
    }

    @GetMapping("{id}")
    @Secured(UserRole.Admin)
    public ResponseEntity<Suggestion> getById(@PathVariable Long id){
        Optional<Suggestion> suggestion = suggestionService.getSuggestion(id);
        if(suggestion.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(suggestion.get());
    }

    @PostMapping
    @Secured({UserRole.Admin, UserRole.Normal})
    public ResponseEntity<?> create(@RequestBody SuggestionDto suggestionDto){
        Optional<Suggestion> suggestion = suggestionService.makeSuggestion(suggestionDto);
        if(suggestion.isPresent()){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.badRequest().build();
        }

    }
}
