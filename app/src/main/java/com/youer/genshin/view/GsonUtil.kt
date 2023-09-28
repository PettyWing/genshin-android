package com.youer.genshin.view

import android.text.TextUtils
import com.google.gson.JsonObject
import java.util.*

/**
 * @author youer
 * @date 9/27/23
 */
object GsonUtil {
    fun getValues(jsonObject: JsonObject?): List<String> {
        val values: MutableList<String> = ArrayList()
        if (jsonObject == null) {
            return values
        }
        for ((_, value) in jsonObject.entrySet()) {
            values.add(value.asString)
        }
        return values
    }

    fun getKey(jsonObject: JsonObject, value: String?): String? {
        if (TextUtils.isEmpty(value)) {
            return null
        }
        for ((key, value1) in jsonObject.entrySet()) {
            if (TextUtils.equals(value1.asString, value)) {
                return key
            }
        }
        return null
    }
}