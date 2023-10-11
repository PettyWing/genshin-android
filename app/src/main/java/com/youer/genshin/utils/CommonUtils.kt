package com.youer.genshin.utils

import com.youer.genshin.constants.AppendProp
import java.text.DecimalFormat

/**
 * @author youer
 * @date 9/26/23
 */
object CommonUtils {
    /**
     * 展示圣遗物的属性
     *
     * @param key
     * @param value
     * @return
     */
    fun displayRelicsValue(key: String?, value: Double?): String {
        return if (AppendProp.getTypeByApiName(key)?.isPercent!!) {
            "$value%"
        } else format(value)
    }

    fun format(value: Double?): String {
        val decimalFormat = DecimalFormat("#.#")
        return decimalFormat.format(value)
    }

    fun convertUpCase(camelCase: String): String {
        val result = StringBuilder()
        for (c in camelCase.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append("_").append(Character.toUpperCase(c))
            } else {
                result.append(Character.toUpperCase(c))
            }
        }
        return result.toString()
    }

    fun convertCamelCase(uppercase: String?): String {
        if (uppercase == null) {
            return ""
        }
        val result = StringBuilder()
        var capitalizeNextChar = false
        for (c in uppercase.toCharArray()) {
            if (c == '_') {
                capitalizeNextChar = true
            } else {
                if (capitalizeNextChar) {
                    result.append(Character.toUpperCase(c))
                    capitalizeNextChar = false
                } else {
                    result.append(Character.toLowerCase(c))
                }
            }
        }
        return result.toString()
    }
}