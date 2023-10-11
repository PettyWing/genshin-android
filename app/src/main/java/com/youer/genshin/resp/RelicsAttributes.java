package com.youer.genshin.resp;

import java.util.Objects;

import android.text.TextUtils;
import com.youer.genshin.constants.AppendProp;

public class RelicsAttributes {
    private AppendProp appendProp;
    private String mainType;
    private Double mainValue;
    private Double maxHealth;
    private Double minHealth;
    private Double maxAttack;
    private Double minAttack;
    private Double maxDefense;
    private Double minDefense;
    private Double criticalStrikeRate;
    private Double criticalStrikeDamage;
    private Double proficients;
    private Double chargingRate;

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public AppendProp getAppendProp() {
        return appendProp;
    }
    public String getAppendPropName() {
        return appendProp.getDisplayName();
    }
    public void setAppendProp(AppendProp appendProp) {
        this.appendProp = appendProp;
    }

    public Double getMainValue() {
        return mainValue;
    }

    public void setMainValue(Double mainValue) {
        this.mainValue = mainValue;
    }

    public Double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(Double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public Double getMinHealth() {
        return minHealth;
    }

    public void setMinHealth(Double minHealth) {
        this.minHealth = minHealth;
    }

    public Double getMaxAttack() {
        return maxAttack;
    }

    public void setMaxAttack(Double maxAttack) {
        this.maxAttack = maxAttack;
    }

    public Double getMinAttack() {
        return minAttack;
    }

    public void setMinAttack(Double minAttack) {
        this.minAttack = minAttack;
    }

    public Double getMaxDefense() {
        return maxDefense;
    }

    public void setMaxDefense(Double maxDefense) {
        this.maxDefense = maxDefense;
    }

    public Double getMinDefense() {
        return minDefense;
    }

    public void setMinDefense(Double minDefense) {
        this.minDefense = minDefense;
    }

    public Double getCriticalStrikeRate() {
        return criticalStrikeRate;
    }

    public void setCriticalStrikeRate(Double criticalStrikeRate) {
        this.criticalStrikeRate = criticalStrikeRate;
    }

    public Double getCriticalStrikeDamage() {
        return criticalStrikeDamage;
    }

    public void setCriticalStrikeDamage(Double criticalStrikeDamage) {
        this.criticalStrikeDamage = criticalStrikeDamage;
    }

    public Double getProficients() {
        return proficients;
    }

    public void setProficients(Double proficients) {
        this.proficients = proficients;
    }

    public Double getChargingRate() {
        return chargingRate;
    }

    public void setChargingRate(Double chargingRate) {
        this.chargingRate = chargingRate;
    }

    public RelicsAttributes() {
    }

    public RelicsAttributes(AppendProp appendProp, Double mainValue) {
        this.appendProp = appendProp;
        this.mainValue = mainValue;
    }

    /**
     * 构建圣遗物的收益率
     */
    public RelicsAttributes(Double maxHealth, Double maxAttack, Double maxDefense,
        Double criticalStrikeRate, Double criticalStrikeDamage, Double proficients, Double chargingRate) {
        this.maxHealth = maxHealth;
        this.maxAttack = maxAttack;
        this.maxDefense = maxDefense;
        this.criticalStrikeRate = criticalStrikeRate;
        this.criticalStrikeDamage = criticalStrikeDamage;
        this.proficients = proficients;
        this.chargingRate = chargingRate;
    }

    public boolean isEqual(RelicsAttributes other) {
        if (!TextUtils.equals(appendProp.getKey(), other.appendProp.getKey())) {
            return false;
        }
        if (!Objects.equals(mainValue, other.mainValue)) {
            return false;
        }
        if (!Objects.equals(maxHealth, other.maxHealth)) {
            return false;
        }
        if (!Objects.equals(minHealth, other.minHealth)) {
            return false;
        }
        if (!Objects.equals(maxAttack, other.maxAttack)) {
            return false;
        }
        if (!Objects.equals(minAttack, other.minAttack)) {
            return false;
        }
        if (!Objects.equals(maxDefense, other.maxDefense)) {
            return false;
        }
        if (!Objects.equals(minDefense, other.minDefense)) {
            return false;
        }
        if (!Objects.equals(criticalStrikeRate, other.criticalStrikeRate)) {
            return false;
        }
        if (!Objects.equals(criticalStrikeDamage, other.criticalStrikeDamage)) {
            return false;
        }
        if (!Objects.equals(proficients, other.proficients)) {
            return false;
        }
        if (!Objects.equals(chargingRate, other.chargingRate)) {
            return false;
        }
        return true;
    }
}