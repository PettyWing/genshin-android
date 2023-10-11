package com.youer.genshin.resp;

import android.text.TextUtils;
import com.youer.genshin.constants.AppendProp;
import com.youer.genshin.constants.Constants;
import com.youer.genshin.constants.EquipType;

public class RelicsDTO {
    private Long id;
    private EquipType equipType;
    private String groupType;
    private Long characterId;

    private Double score;
    private RelicsAttributes relicsAttributes;

    public EquipType getType() {
        return equipType;
    }

    public void setType(EquipType equipType) {
        this.equipType = equipType;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public RelicsAttributes getAttributes() {
        return relicsAttributes;
    }

    public void setAttributes(RelicsAttributes relicsAttributes) {
        this.relicsAttributes = relicsAttributes;
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
        return "";
        //return equipType.getDisplayName();
    }

    public String getCharacterName() {
        return Constants.LOC_INFO.get(String.valueOf(characterId)).getAsString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RelicsDTO() {
    }

    public RelicsDTO(EquipType equipType, String groupType, AppendProp appendProp, double mainValue) {
        this.equipType = equipType;
        this.groupType = groupType;
        this.relicsAttributes = new RelicsAttributes(appendProp, mainValue);
    }

    public boolean isEqual(RelicsDTO other) {
        if (!TextUtils.equals(equipType.getName(), other.equipType.getName())) {
            return false;
        }
        if (!TextUtils.equals(groupType, other.groupType)) {
            return false;
        }
        if (!relicsAttributes.isEqual(other.relicsAttributes)) {
            return false;
        }
        return true;
    }
}


