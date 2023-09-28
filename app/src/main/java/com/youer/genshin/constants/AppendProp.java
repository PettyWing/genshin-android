package com.youer.genshin.constants;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

/**
 * 圣遗物主属性
 */
public enum AppendProp {

    FIRE_DAMAGE("fireDamage", "FIGHT_PROP_FIRE_ADD_HURT", "火元素伤害加成", true),
    WATER_DAMAGE("waterDamage", "FIGHT_PROP_WATER_ADD_HURT", "水元素伤害加成", true),
    ROCK_DAMAGE("rockDamage", "FIGHT_PROP_ROCK_ADD_HURT", "岩元素伤害加成", true),
    GRASS_DAMAGE("grassDamage", "FIGHT_PROP_GRASS_ADD_HURT", "草元素伤害加成", true),
    WIND_DAMAGE("windDamage", "FIGHT_PROP_WIND_ADD_HURT", "风元素伤害加成", true),
    THUNDERBOLT_DAMAGE("thunderboltDamage", "FIGHT_PROP_ELEC_ADD_HURT", "雷元素伤害加成", true),
    ICE_DAMAGE("iceDamage", "FIGHT_PROP_ICE_ADD_HURT", "冰元素伤害加成", true),
    MAX_HEALTH("maxHealth", "FIGHT_PROP_HP_PERCENT", "生命值百分比", true),
    MIN_HEALTH("minHealth", "FIGHT_PROP_HP", "生命值", false),
    MAX_ATTACK("maxAttack", "FIGHT_PROP_ATTACK_PERCENT", "攻击力百分比", true),
    MIN_ATTACK("minAttack", "FIGHT_PROP_ATTACK", "攻击力", false),
    MAX_DEFENSE("maxDefense", "FIGHT_PROP_DEFENSE_PERCENT", "防御力百分比", true),
    MIN_DEFENSE("minDefense", "FIGHT_PROP_DEFENSE", "防御力", false),
    CRITICAL_STRIKE_RATE("criticalStrikeRate", "FIGHT_PROP_CRITICAL", "暴击率", true),
    CRITICAL_STRIKE_DAMAGE("criticalStrikeDamage", "FIGHT_PROP_CRITICAL_HURT", "暴击伤害", false),
    PROFICIENTS("proficients", "FIGHT_PROP_ELEMENT_MASTERY", "元素精通", false),
    CHARGING_RATE("chargingRate", "FIGHT_PROP_CHARGE_EFFICIENCY", "充能效率", true),
    HEAL_ADD("healAdd", "FIGHT_PROP_HEAL_ADD", "治疗加成", true);

    private String apiName;
    private String name;
    private String displayName;
    private boolean isPercent;

    AppendProp(String apiName, String name, String displayName, boolean isPercent) {
        this.apiName = apiName;
        this.name = name;
        this.displayName = displayName;
        this.isPercent = isPercent;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getApiName() {
        return apiName;
    }

    public boolean isPercent() {
        return isPercent;
    }

    public static AppendProp getType(String apiName) {
        if (TextUtils.isEmpty(apiName)) {
            return null;
        }
        for (AppendProp type : AppendProp.values()) {
            if (TextUtils.equals(apiName, type.getApiName())) {
                return type;
            }
        }
        return null;
    }

    public static List<AppendProp> shoesProps = new ArrayList<AppendProp>(){
        {
            add(MAX_HEALTH);
            add(MIN_HEALTH);
            add(MAX_ATTACK);
            add(MIN_ATTACK);
            add(MAX_DEFENSE);
            add(MIN_DEFENSE);
            add(PROFICIENTS);
            add(CHARGING_RATE);
        }
    };

    public static List<AppendProp> ringProps = new ArrayList<AppendProp>(){
        {
            add(MAX_HEALTH);
            add(MIN_HEALTH);
            add(MAX_ATTACK);
            add(MIN_ATTACK);
            add(MAX_DEFENSE);
            add(MIN_DEFENSE);
            add(PROFICIENTS);
            add(FIRE_DAMAGE);
            add(WATER_DAMAGE);
            add(ROCK_DAMAGE);
            add(GRASS_DAMAGE);
            add(WIND_DAMAGE);
            add(THUNDERBOLT_DAMAGE);
            add(ICE_DAMAGE);
        }
    };

    public static List<AppendProp> dressProps = new ArrayList<AppendProp>(){
        {
            add(CRITICAL_STRIKE_RATE);
            add(CRITICAL_STRIKE_DAMAGE);
            add(HEAL_ADD);
            add(MAX_HEALTH);
            add(MIN_HEALTH);
            add(MAX_ATTACK);
            add(MIN_ATTACK);
            add(MAX_DEFENSE);
            add(MIN_DEFENSE);
            add(PROFICIENTS);
        }
    };
}
