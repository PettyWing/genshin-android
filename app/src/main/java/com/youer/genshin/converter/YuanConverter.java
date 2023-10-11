package com.youer.genshin.converter;

import com.youer.genshin.constants.AppendProp;
import com.youer.genshin.constants.Constants;
import com.youer.genshin.constants.EquipType;
import com.youer.genshin.entity.Relics;
import com.youer.genshin.resp.EnKaDO.AvatarInfoListDTO.EquipListDTO.FlatDTO;
import com.youer.genshin.resp.RelicsAttributes;
import com.youer.genshin.resp.RelicsDTO;

public class YuanConverter {
    public static RelicsDTO convert(FlatDTO flatDTO) {
        if (flatDTO == null) {
            return null;
        }
        RelicsDTO relicsDTO = new RelicsDTO();
        relicsDTO.setType(EquipType.getType(flatDTO.getEquipType()));
        relicsDTO.setGroupType(Constants.LOC_INFO.get(flatDTO.getSetNameTextMapHash()).getAsString());
        RelicsAttributes relicsAttributes = new RelicsAttributes();
        relicsAttributes.setAppendProp(AppendProp.getType(flatDTO.getReliquaryMainstat().getMainPropId()));
        relicsAttributes.setMainValue(flatDTO.getReliquaryMainstat().getStatValue());
        flatDTO.getReliquarySubstats().forEach(reliquarySubstatsDTO -> {
            AppendProp prop = AppendProp.getType(reliquarySubstatsDTO.getAppendPropId());
            if (prop == null) {
                return;
            }
            double statValue = reliquarySubstatsDTO.getStatValue();
            // 只有主属性存在的属性伤害和治疗加成就不放在这儿处理了
            switch (prop) {
                case MAX_HEALTH:
                    relicsAttributes.setMaxHealth(statValue);
                    break;
                case MIN_HEALTH:
                    relicsAttributes.setMinHealth(statValue);
                    break;
                case MAX_ATTACK:
                    relicsAttributes.setMaxAttack(statValue);
                    break;
                case MIN_ATTACK:
                    relicsAttributes.setMinAttack(statValue);
                    break;
                case MAX_DEFENSE:
                    relicsAttributes.setMaxDefense(statValue);
                    break;
                case MIN_DEFENSE:
                    relicsAttributes.setMinDefense(statValue);
                    break;
                case CRITICAL_STRIKE_RATE:
                    relicsAttributes.setCriticalStrikeRate(statValue);
                    break;
                case CRITICAL_STRIKE_DAMAGE:
                    relicsAttributes.setCriticalStrikeDamage(statValue);
                    break;
                case PROFICIENTS:
                    relicsAttributes.setProficients(statValue);
                    break;
                case CHARGING_RATE:
                    relicsAttributes.setChargingRate(statValue);
                    break;
                default:
                    break;
            }
        });
        relicsDTO.setAttributes(relicsAttributes);
        return relicsDTO;
    }

    public static Relics convert(Long uid, RelicsDTO relicsDTO) {
        if (relicsDTO == null) {
            return null;
        }
        Relics relics = new Relics();
        relics.setUid(uid);
        relics.setCharacterId(relicsDTO.getCharacterId());
        relics.setType(relicsDTO.getType().getName());
        relics.setGroupType(relicsDTO.getGroupType());
        relics.setMainType(relicsDTO.getAttributes().getAppendProp().getKey());
        relics.setMainValue(relicsDTO.getAttributes().getMainValue());
        relics.setMaxHealth(relicsDTO.getAttributes().getMaxHealth());
        relics.setMinHealth(relicsDTO.getAttributes().getMinHealth());
        relics.setMaxAttack(relicsDTO.getAttributes().getMaxAttack());
        relics.setMinAttack(relicsDTO.getAttributes().getMinAttack());
        relics.setMaxDefense(relicsDTO.getAttributes().getMaxDefense());
        relics.setMinDefense(relicsDTO.getAttributes().getMinDefense());
        relics.setCriticalStrikeRate(relicsDTO.getAttributes().getCriticalStrikeRate());
        relics.setCriticalStrikeDamage(relicsDTO.getAttributes().getCriticalStrikeDamage());
        relics.setProficients(relicsDTO.getAttributes().getProficients());
        relics.setChargingRate(relicsDTO.getAttributes().getChargingRate());
        return relics;
    }

    public static RelicsDTO convert(Relics relics) {
        if (relics == null) {
            return null;
        }
        RelicsDTO relicsDTO = new RelicsDTO();
        relicsDTO.setId(relics.getId());
        relicsDTO.setType(EquipType.getType(relics.getType()));
        relicsDTO.setGroupType(relics.getGroupType());
        relicsDTO.setCharacterId(relics.getCharacterId());
        RelicsAttributes attributes = new RelicsAttributes();
        attributes.setAppendProp(AppendProp.getType(relics.getMainType()));
        attributes.setMainValue(toDouble(relics.getMainValue()));
        attributes.setMaxHealth(toDouble(relics.getMaxHealth()));
        attributes.setMinHealth(toDouble(relics.getMinHealth()));
        attributes.setMaxAttack(toDouble(relics.getMaxAttack()));
        attributes.setMinAttack(toDouble(relics.getMinAttack()));
        attributes.setMaxDefense(toDouble(relics.getMaxDefense()));
        attributes.setMinDefense(toDouble(relics.getMinDefense()));
        attributes.setCriticalStrikeRate(toDouble(relics.getCriticalStrikeRate()));
        attributes.setCriticalStrikeDamage(toDouble(relics.getCriticalStrikeDamage()));
        attributes.setProficients(toDouble(relics.getProficients()));
        attributes.setChargingRate(toDouble(relics.getChargingRate()));
        relicsDTO.setAttributes(attributes);
        return relicsDTO;
    }

    public static Double toDouble(Double value){
        if(value == null || value == 0){
            return null;
        }
        return value;
    }
}
