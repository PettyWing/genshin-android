package com.youer.genshin.resp

class RelicsDTO {
    var equipTypeName: String? = null
        private set
    var groupType: String? = null
    var characterId: Long? = null
    var score: Double? = null
    var attributes: RelicsAttributes? = null
        private set

    fun setAttributes(attributes: RelicsAttributes?): RelicsDTO {
        this.attributes = attributes
        return this
    }

    fun setEquipTypeName(equipTypeName: String?): RelicsDTO {
        this.equipTypeName = equipTypeName
        return this
    }
}