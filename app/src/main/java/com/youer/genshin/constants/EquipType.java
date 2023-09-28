package com.youer.genshin.constants;

import android.text.TextUtils;

/**
 * 圣遗物位置
 */
public enum EquipType {
    BRACER("EQUIP_BRACER", "equipBracer","生之花"),
    NECKLACE( "EQUIP_NECKLACE","equipNecklace", "死之羽"),
    SHOES( "EQUIP_SHOES", "equipShoes", "时之沙"),
    RING( "EQUIP_RING", "equipRing","空之杯"),
    DRESS( "EQUIP_DRESS", "equipDress","理之冠");

    // enka对应的名字
    private String name;
    private String lowName;
    private String displayName;

    EquipType(String name,String lowName, String displayName) {
        this.name = name;
        this.lowName = lowName;
        this.displayName = displayName;
    }

    public String getLowName() {
        return lowName;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static EquipType getType(String name){
        if(TextUtils.isEmpty(name)){
            return null;
        }
        for(EquipType equipType : EquipType.values()){
            if(TextUtils.equals(name, equipType.getName())){
                return equipType;
            }
        }
        return null;
    }
}
