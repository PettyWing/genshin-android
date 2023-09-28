package com.youer.genshin.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author youer
 * @date 9/27/23
 */
public class GsonUtil {
    public static List<String> getValues(JsonObject jsonObject) {
        List<String> values = new ArrayList<>();
        if (jsonObject == null) {
            return values;
        }
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            values.add(entry.getValue().getAsString());
        }
        return values;
    }

    public static String getKey(JsonObject jsonObject, String value) {
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            if (TextUtils.equals(entry.getValue().getAsString(), value)) {
                return entry.getKey();
            }
        }
        return null;
    }
} 