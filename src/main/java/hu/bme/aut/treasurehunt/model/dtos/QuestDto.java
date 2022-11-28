package hu.bme.aut.treasurehunt.model.dtos;

public class QuestDto {
    public Long id;
    public int point;
    public String name;
    public String description;
    public String options;
    public String answer;
    public float longitude;
    public float latitude;

    public QuestDto(Long id, int point, String name, String description, String options, String answer, float longitude, float latitude) {
        this.id = id;
        this.point = point;
        this.name = name;
        this.description = description;
        this.options = options;
        this.answer = answer;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}

