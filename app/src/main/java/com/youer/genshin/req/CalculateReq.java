package com.youer.genshin.req;

import java.util.List;

public class CalculateReq {

    private Long uid;
    List<CharacterInfo> characters;

    public Long getUid() {return uid;}

    public void setUid(Long uid) {this.uid = uid;}

    public List<CharacterInfo> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharacterInfo> characters) {
        this.characters = characters;
    }

    public static class CharacterInfo {
        private String characterId;
        private EquipTypesDTO equipTypes;
        private String groupType;

        public String getCharacterId() {return characterId;}

        public void setCharacterId(String characterId) {this.characterId = characterId;}

        public EquipTypesDTO getEquipTypes() {return equipTypes;}

        public void setEquipTypes(EquipTypesDTO equipTypes) {this.equipTypes = equipTypes;}

        public String getGroupType() {return groupType;}

        public void setGroupType(String groupType) {this.groupType = groupType;}
    }

    public static class EquipTypesDTO {
        private List<String> equipShoes;
        private List<String> equipRing;
        private List<String> equipDress;

        public List<String> getEquipShoes() {
            return equipShoes;
        }

        public void setEquipShoes(List<String> equipShoes) {
            this.equipShoes = equipShoes;
        }

        public List<String> getEquipRing() {
            return equipRing;
        }

        public void setEquipRing(List<String> equipRing) {
            this.equipRing = equipRing;
        }

        public List<String> getEquipDress() {
            return equipDress;
        }

        public void setEquipDress(List<String> equipDress) {
            this.equipDress = equipDress;
        }
    }
}
