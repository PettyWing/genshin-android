package com.youer.genshin.utils;

import java.text.DecimalFormat;

import com.youer.genshin.constants.AppendProp;

/**
 * @author youer
 * @date 9/26/23
 */
public class CommonUtils {

    /**
     * 展示圣遗物的属性
     *
     * @param key
     * @param value
     * @return
     */
    public static String displayRelicsValue(String key, double value) {
        if (AppendProp.getType(key).isPercent()) {
            return value + "%";
        }
        return format(value);
    }

    public static String format(double value){
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(value);
    }

    public static String convertUpCase(String camelCase) {
        StringBuilder result = new StringBuilder();

        for (char c : camelCase.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append("_").append(Character.toUpperCase(c));
            } else {
                result.append(Character.toUpperCase(c));
            }
        }

        return result.toString();
    }

    public static String convertCamelCase(String uppercase) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNextChar = false;

        for (char c : uppercase.toCharArray()) {
            if (c == '_') {
                capitalizeNextChar = true;
            } else {
                if (capitalizeNextChar) {
                    result.append(Character.toUpperCase(c));
                    capitalizeNextChar = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }

        return result.toString();
    }
} 