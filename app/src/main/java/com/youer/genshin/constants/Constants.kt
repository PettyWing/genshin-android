package com.youer.genshin.constants

import com.google.gson.JsonObject

/**
 * @author youer
 * @date 9/15/23
 */
object Constants {
    const val KEY_UID = "uid"
    const val FILE_CHARACTER = "character.json"
    const val FILE_RELICS = "relics.json"
    @JvmField
    var CHARACTERS: JsonObject? = null
    @JvmField
    var RELICS: JsonObject? = null
}