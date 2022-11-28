package hu.bme.aut.treasurehunt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.bme.aut.treasurehunt.model.dtos.QuestDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "quests")
public class Quest {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private int point;

    @NotBlank
    private String description;
    @NotBlank
    private String options;
    @NotBlank
    private String answer;

    private String name;

    @NotNull
    private float longitude;

    @NotNull
    private float latitude;

    @OneToMany(mappedBy = "quest")
    private List<Suggestion> suggestions;

    @OneToMany(mappedBy = "quest")
    private List<UserQuest> userQuests;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public List<UserQuest> getUserQuests() {
        return userQuests;
    }

    public void setUserQuests(List<UserQuest> userQuests) {
        this.userQuests = userQuests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Quest() {
    }

    @JsonIgnore
    public Quest(QuestDto questDto){
        id = questDto.id;
        point = questDto.point;
        name = questDto.name;
        description = questDto.description;
        options = questDto.options;
        answer = questDto.answer;
        longitude = questDto.longitude;
        latitude = questDto.latitude;
    }
}
