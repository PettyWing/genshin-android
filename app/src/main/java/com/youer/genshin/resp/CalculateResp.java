package com.youer.genshin.resp;

import java.util.List;

public class CalculateResp {
    private List<CharacterResp> characters;

    public List<CharacterResp> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharacterResp> characters) {
        this.characters = characters;
    }

    public static class CharacterResp{
        private double score;
        private String characterId;
        private String characterName;
        private List<RelicsDTO> relicsDTOS;

        public String getCharacterId() {
            return characterId;
        }

        public void setCharacterId(String characterId) {
            this.characterId = characterId;
        }

        public String getCharacterName() {
            return characterName;
        }

        public void setCharacterName(String characterName) {
            this.characterName = characterName;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public List<RelicsDTO> getRelicsDTOS() {
            return relicsDTOS;
        }

        public void setRelicsDTOS(List<RelicsDTO> relicsDTOS) {
            this.relicsDTOS = relicsDTOS;
        }
    }
}
