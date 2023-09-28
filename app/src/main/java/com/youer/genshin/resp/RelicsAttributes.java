package com.youer.genshin.resp;

public class RelicsAttributes {
    private String appendPropName;
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
    private String appendProp;

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public String getAppendProp() {
        return appendProp;
    }

    public RelicsAttributes setAppendProp(String appendProp) {
        this.appendProp = appendProp;
        return this;
    }

    public String getAppendPropName() {
        return appendPropName;
    }

    public RelicsAttributes setAppendPropName(String appendPropName) {
        this.appendPropName = appendPropName;
        return this;
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

}