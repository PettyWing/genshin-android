package com.youer.genshin.resp;

public class RelicsDTO {
    private String equipTypeName;
    private String groupType;
    private Long characterId;

    private Double score;
    private RelicsAttributes attributes;

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public RelicsAttributes getAttributes() {
        return attributes;
    }

    public RelicsDTO setAttributes(RelicsAttributes attributes) {
        this.attributes = attributes;
        return this;
    }

    public Long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Long characterId) {
        this.characterId = characterId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getEquipTypeName() {
        return equipTypeName;
    }

    public RelicsDTO setEquipTypeName(String equipTypeName) {
        this.equipTypeName = equipTypeName;
        return this;
    }

}


