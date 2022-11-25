package hu.bme.aut.treasurehunt.service;

import hu.bme.aut.treasurehunt.domain.User;
import hu.bme.aut.treasurehunt.model.dtos.UserScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderboardService {
    @Autowired
    private UserService userService;

    public List<UserScore> getUserScores(){
        List<User> users =  userService.getUsers();
        return users.stream().map(e-> new UserScore(e.getName(), e.getPoints())).toList();
    }
}
