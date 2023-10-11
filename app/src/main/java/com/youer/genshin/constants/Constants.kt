package com.youer.genshin.constants

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.youer.genshin.resp.RelicsAttributes
import com.youer.genshin.utils.StorageUtil


/**
 * @author youer
 * @date 9/15/23
 */
object Constants {
    const val KEY_UID = "uid"
    const val FILE_CHARACTER = "character.json"
    const val FILE_RELICS = "relics.json"
    var CHARACTERS: JsonObject? = null
    var CHARACTERS_INFO: JsonObject? = StorageUtil.readJsonFromAssets("character.json")

    @JvmField
    var LOC_INFO: JsonObject? = StorageUtil.readJsonFromAssets("loc.json")
    var RELICS: JsonObject? = StorageUtil.readJsonFromAssets("relics.json")

    fun getCharacter(characterId: String?): RelicsAttributes? {
        val character = CHARACTERS_INFO?.get(characterId)?.asJsonObject
        return if (!character?.has("RelicsAttributes")!!) {
            null
        } else Gson().fromJson(Gson().toJson(character.getAsJsonObject("RelicsAttributes")), RelicsAttributes::class.java)
    }
}