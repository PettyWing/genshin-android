package com.youer.genshin.resp

class RelicsAttributes {
    var appendPropName: String? = null
        private set
    var mainType: String? = null
    var mainValue: Double? = null
    var maxHealth: Double? = null
    var minHealth: Double? = null
    var maxAttack: Double? = null
    var minAttack: Double? = null
    var maxDefense: Double? = null
    var minDefense: Double? = null
    var criticalStrikeRate: Double? = null
    var criticalStrikeDamage: Double? = null
    var proficients: Double? = null
    var chargingRate: Double? = null
    var appendProp: String? = null
        private set

    fun setAppendProp(appendProp: String?): RelicsAttributes {
        this.appendProp = appendProp
        return this
    }

    fun setAppendPropName(appendPropName: String?): RelicsAttributes {
        this.appendPropName = appendPropName
        return this
    }

}