package hu.bme.aut.treasurehunt.model.dtos;

public class SuggestionDto {
    public Long id;
    public Long userId;
    public Long questId;
    public String description;

    public SuggestionDto(Long id, Long userId, Long questId, String description) {
        this.id = id;
        this.userId = userId;
        this.questId = questId;
        this.description = description;
    }
}
