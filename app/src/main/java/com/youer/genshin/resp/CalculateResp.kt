package com.youer.genshin.resp

class CalculateResp {
    var characters: List<CharacterResp>? = null

    class CharacterResp {
        var score = 0.0
        var characterId: String? = null
        var characterName: String? = null
        var relicsDTOS: List<RelicsDTO>? = null

    }
}