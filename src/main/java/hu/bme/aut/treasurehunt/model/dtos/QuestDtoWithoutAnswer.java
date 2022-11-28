package hu.bme.aut.treasurehunt.model.dtos;

import hu.bme.aut.treasurehunt.domain.Quest;

public class QuestDtoWithoutAnswer {
    public Long id;
    public int point;
    public String name;
    public String description;
    public String options;
    public float longitude;
    public float latitude;

    public QuestDtoWithoutAnswer(Long id, int point, String name, String description, String options, float longitude, float latitude) {
        this.id = id;
        this.point = point;
        this.name = name;
        this.description = description;
        this.options = options;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public QuestDtoWithoutAnswer(QuestDto questDto){
        this.id = questDto.id;
        this.point = questDto.point;
        this.name = questDto.name;
        this.description = questDto.description;
        this.options = questDto.options;
        this.longitude = questDto.longitude;
        this.latitude = questDto.latitude;
    }

    public QuestDtoWithoutAnswer(Quest quest){
        this.id = quest.getId();
        this.point = quest.getPoint();
        this.name = quest.getName();
        this.description = quest.getDescription();
        this.options = quest.getOptions();
        this.longitude = quest.getLongitude();
        this.latitude = quest.getLatitude();
    }
}
