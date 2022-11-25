package hu.bme.aut.treasurehunt.controller;

import hu.bme.aut.treasurehunt.model.dtos.UserScore;
import hu.bme.aut.treasurehunt.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/scores")
public class LeaderboardController {
    @Autowired
    private LeaderboardService leaderboardService;

    @GetMapping
    public ResponseEntity<List<UserScore>> getUserScores(){
        return ResponseEntity.ok(leaderboardService.getUserScores());
    }
}
