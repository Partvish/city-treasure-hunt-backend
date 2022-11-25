package hu.bme.aut.treasurehunt.model.dtos;

public class UserScore {
    public String userName;
    public int score;

    public UserScore(String userName, int score) {
        this.userName = userName;
        this.score = score;
    }
}
