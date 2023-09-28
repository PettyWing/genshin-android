package com.youer.genshin.req

class CalculateReq {
    var uid: Long? = null
    var characters: List<CharacterInfo>? = null

    class CharacterInfo {
        var characterId: String? = null
        var equipTypes: EquipTypesDTO? = null
        var groupType: String? = null

    }

    class EquipTypesDTO {
        var equipShoes: List<String>? = null
        var equipRing: List<String>? = null
        var equipDress: List<String>? = null

    }
}