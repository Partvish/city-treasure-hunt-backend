package hu.bme.aut.treasurehunt.model.dtos;

import hu.bme.aut.treasurehunt.domain.Quest;

public class QuestListItem {
    public Long id;
    public String name;
    public int point;

    public QuestListItem(Long id, String name, int point) {
        this.id = id;
        this.name = name;
        this.point = point;
    }

    public QuestListItem(Quest quest) {
        this.id = quest.getId();
        this.name = quest.getName();
        this.point = quest.getPoint();
    }
}
