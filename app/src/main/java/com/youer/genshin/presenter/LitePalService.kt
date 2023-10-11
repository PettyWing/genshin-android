package com.youer.genshin.presenter

import com.youer.genshin.converter.YuanConverter
import com.youer.genshin.entity.Relics
import com.youer.genshin.resp.RelicsDTO
import org.litepal.LitePal
import java.util.*
import java.util.stream.Collectors


/**
 * @author youer
 * @date 10/10/23
 */
object LitePalService {
    fun queryRelics(uid: Long?): List<RelicsDTO> {
        return LitePal.where("uid = ?", uid.toString()).find(Relics::class.java).stream().map(YuanConverter::convert).collect(Collectors.toList());
    }

    /**
     * 批量插入圣遗物
     *
     * @param uid
     * @param relicsDTOS
     */
    fun insertRelics(uid: Long?, relicsDTOS: List<RelicsDTO?>?): Boolean {

        return try {
            if (relicsDTOS == null || relicsDTOS.isEmpty()) {
                return true
            }
            val date = Date()
            val result = relicsDTOS.stream().map { relicsDTO: RelicsDTO? ->
                YuanConverter.convert(uid, relicsDTO).apply {
                    gmtCreate = date
                    gmtModified = date
                }
            }.collect(Collectors.toList())
            LitePal.saveAll(result)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 批量更新圣遗物
     *
     * @param uid
     * @param relicsDTOS
     */
    fun updateRelics(uid: Long?, relicsDTOS: List<RelicsDTO>?): Boolean {
        return try {
            if (relicsDTOS == null || relicsDTOS.isEmpty()) {
                return true
            }
            val date = Date()
            val relicsList = relicsDTOS.stream().map { relicsDTO: RelicsDTO ->
                val relics = YuanConverter.convert(uid, relicsDTO)
                relics.id = relicsDTO.id
                relics.gmtCreate = date
                relics.gmtModified = date
                relics
            }.forEach { relics: Relics? ->
                relics?.save()
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}