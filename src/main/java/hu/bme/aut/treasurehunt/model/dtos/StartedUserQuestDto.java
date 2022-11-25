package hu.bme.aut.treasurehunt.model.dtos;

public class StartedUserQuestDto {
    String options;
    String description;

    public StartedUserQuestDto(String options, String description) {
        this.options = options;
        this.description = description;
    }
}
