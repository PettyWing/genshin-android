package com.youer.genshin.presenter

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.youer.genshin.App
import com.youer.genshin.constants.AppendProp
import com.youer.genshin.constants.Constants
import com.youer.genshin.constants.EquipType
import com.youer.genshin.converter.YuanConverter
import com.youer.genshin.req.CalculateReq
import com.youer.genshin.resp.*
import com.youer.genshin.resp.EnKaDO.AvatarInfoListDTO
import com.youer.genshin.resp.EnKaDO.AvatarInfoListDTO.EquipListDTO
import com.youer.genshin.utils.SPUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.collections.ArrayList


/**
 * @author youer
 * @date 10/9/23
 */
class MainPresenter(var context: Context) {
    var useLocalData = true
    var gson = Gson()

    /**
     * 登录
     */
    fun login(uid: Long?, callback: ResultCallback<List<RelicsDTO>?>) {
        if (useLocalData) {
            callback.success(LitePalService.queryRelics(uid))
            SPUtil.writeString(context, Constants.KEY_UID, uid.toString() + "")
            return
        }
        App.apiService.login(uid)
                .enqueue(object : Callback<Result<LoginResp>> {
                    override fun onResponse(call: Call<Result<LoginResp>>, response: Response<Result<LoginResp>>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (result.isSuccess) {
                                SPUtil.writeString(context, Constants.KEY_UID, uid.toString() + "")
                                callback.success(result.result?.relics)
                            } else {
                                callback.fail(response.message())
                            }

                        }
                    }

                    override fun onFailure(call: Call<Result<LoginResp>>, t: Throwable) {
                        callback.fail(t.localizedMessage)
                    }
                })
    }

    /**
     * 多圣遗物导入
     */
    fun multiImport(uid: Long?, callback: ResultCallback<List<RelicsDTO>?>) {
        if (useLocalData) {
            // 从接口拉出来的目前面板中的圣遗物
            loadRelicsInfos(uid, object : ResultCallback<List<RelicsDTO>?> {
                override fun success(newDTOs: List<RelicsDTO>?) {
                    if (newDTOs == null || newDTOs.isEmpty()) {
                        return callback.fail("拉取圣遗物失败")
                    }
                    // 从数据库查出来的已有圣遗物
                    val lastDTOs: List<RelicsDTO> = LitePalService.queryRelics(uid)
                    // 新的圣遗物需要插入
                    val insertDTOs: MutableList<RelicsDTO> = ArrayList()
                    // 老的需要更新信息的圣遗物
                    val updateDTOs: MutableList<RelicsDTO> = ArrayList()
                    newDTOs.stream()
                            .forEach { newDTO ->
                                val op = lastDTOs.stream().filter { lastDTO: RelicsDTO -> lastDTO.isEqual(newDTO) }.findFirst()
                                if (!op.isPresent) {
                                    // 没有相同的圣遗物
                                    insertDTOs.add(newDTO)
                                } else if (newDTO.characterId != (op.get() as RelicsDTO).characterId) {
                                    newDTO.id = (op.get() as RelicsDTO).id
                                    // 任务id不同
                                    updateDTOs.add(newDTO)
                                }
                            }
                    var result: Boolean = LitePalService.insertRelics(uid, insertDTOs)
                    if (!result) {
                        return callback.fail("插入失败 uid:$uid,insertDTOs:$insertDTOs")
                    }
                    result = LitePalService.updateRelics(uid, updateDTOs)
                    if (!result) {
                        return callback.fail("更新失败 uid:$uid,updateDTOS:$insertDTOs")
                    }
                    callback.success(LitePalService.queryRelics(uid))
                }

                override fun fail(s: String) {
                }

            })

        } else {
            App.apiService.loadRelic(uid)
                    .enqueue(object : Callback<Result<List<RelicsDTO>>> {
                        override fun onResponse(call: Call<Result<List<RelicsDTO>>>, response: Response<Result<List<RelicsDTO>>>) {
                            if (response.isSuccessful) {
                                val result = response.body()
                                if (result.isSuccess) {
                                    callback.success(result.result)
                                } else {
                                    callback.fail(response.message())
                                }
                            }
                        }

                        override fun onFailure(call: Call<Result<List<RelicsDTO>>>, t: Throwable) {
                            callback.fail(t.localizedMessage)
                        }
                    })
        }
    }

    fun calculate(calculateReq: CalculateReq, callback: ResultCallback<List<CalculateResp.CharacterResp>?>) {
        if (useLocalData) {
            // 本地计算逻辑
            // 拿到所有当前uid的圣遗物
            var allRelics = LitePalService.queryRelics(calculateReq.uid)
            var characterResps = ArrayList<CalculateResp.CharacterResp>()
            for (characterInfo in calculateReq.characters) {
                var characterResp = CalculateResp.CharacterResp()
                var randomRelicsDTOs = mutableListOf<RelicsDTO?>()
                var targetRelicsDTOs = mutableListOf<RelicsDTO?>()
                val equipTypes = if (characterInfo.equipTypes != null) gson.fromJson(gson.toJson(characterInfo.equipTypes), JsonObject::class.java) else null
                for (equipType in EquipType.values()) {
                    // 过滤装备位置
                    var relics: List<RelicsDTO> = allRelics.stream().filter { relicsDTO -> relicsDTO.type === equipType }.collect(Collectors.toList())
                    if (equipTypes != null && equipTypes.has(equipType.lowName)) {
                        // 过滤装备主词条
                        val mainTypes: List<String> = gson.fromJson(equipTypes.get(equipType.lowName).asJsonArray, object : TypeToken<List<String>>() {}.type)
                        relics = relics.stream().filter { relicsDTO: RelicsDTO -> mainTypes.contains(relicsDTO.attributes.appendProp.key) }.collect(Collectors.toList())
                    }
                    val result = Optional.ofNullable(calculateRelicsScore(characterInfo.characterId, relics, characterInfo.groupType)).orElse(ArrayList())
                    randomRelicsDTOs.add(if (result.isEmpty()) null else result[0]!!)
                    targetRelicsDTOs.add(if (result.size > 1) result[1]!! else null)
                }
                var maxScore = 0.0
                val result = mutableListOf<RelicsDTO?>()
                for (i in 0 until randomRelicsDTOs.size) {
                    var score: Double = randomRelicsDTOs[i]?.score?.toDouble() ?: 0.0
                    val tmp = ArrayList(targetRelicsDTOs)
                    tmp.removeAt(i)
                    for (relicsDTO in tmp) {
                        score = score + if (relicsDTO == null) 0.0 else relicsDTO.score
                    }
                    if (score > maxScore) {
                        maxScore = score
                        result.clear()
                        result.addAll(tmp)
                        result.add(i, randomRelicsDTOs[i])
                    }
                }
                allRelics = allRelics.stream()
                        .filter { relicsDTO ->
                            result.stream()
                                    .filter(Objects::nonNull)
                                    .noneMatch { resultDTO -> relicsDTO.id == resultDTO?.id }
                        }
                        .collect(Collectors.toList())
                characterResp.relicsDTOS = result
                characterResp.score = maxScore
                characterResp.characterId = characterInfo.characterId
                characterResp.characterName = Constants.LOC_INFO?.get(characterInfo.characterId)?.asString
                characterResps.add(characterResp)
            }
            callback.success(characterResps)
            return
        }
        App.apiService.calculateCharacterRelics(calculateReq)
                .enqueue(object : Callback<Result<CalculateResp>> {
                    override fun onResponse(call: Call<Result<CalculateResp>>, response: Response<Result<CalculateResp>>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (result.isSuccess) {
                                callback.success(result.result?.characters ?: ArrayList())
                            } else {
                                callback.fail(response.message())
                            }
                        }
                    }

                    override fun onFailure(call: Call<Result<CalculateResp>>, t: Throwable) {
                    }
                })
    }


    /**
     * 从enka平台加载角色列表
     */
    fun loadRelicsInfos(uid: Long?, callback: ResultCallback<List<RelicsDTO>?>) {
        if (uid == null) {
            return
        }
        try {
            App.enkaService.getByUid(uid).enqueue(object : Callback<EnKaDO> {
                override fun onResponse(call: Call<EnKaDO>, response: Response<EnKaDO>) {
                    val enKaDO = response.body()
                    if (enKaDO == null) {
                        return
                    }
                    // 获取所有的圣遗物
                    val relicsDTOS: MutableList<RelicsDTO> = ArrayList()
                    enKaDO.avatarInfoList?.stream()?.forEach { avatarInfoListDTO: AvatarInfoListDTO ->
                        avatarInfoListDTO.equipList.forEach(Consumer { equipListDTO: EquipListDTO ->
                            if (TextUtils.equals(equipListDTO.flat.itemType, "ITEM_RELIQUARY")) {
                                val relicsDTO: RelicsDTO = YuanConverter.convert(equipListDTO.flat)
                                relicsDTO.characterId = avatarInfoListDTO.avatarId.toLong()
                                relicsDTOS.add(relicsDTO)
                            }
                        })
                    }
                    callback.success(relicsDTOS)
                }

                override fun onFailure(call: Call<EnKaDO>, t: Throwable) {
                }
            })
        } catch (e: Exception) {
            Log.d(Companion.TAG, "loadRelicsInfos $e")
        }
    }

    /**
     * 计算单个圣遗物的评分
     *
     * @param characterId 人物
     * @param relicsDTOs  圣遗物数据
     */
    fun calculateRelicsScore(characterId: String?, relicsDTOs: List<RelicsDTO>?, groupType: String?): List<RelicsDTO?>? {
        return try {
            if (relicsDTOs == null || relicsDTOs.isEmpty()) {
                return null
            }
            val relicsYield = Constants.getCharacter(characterId) ?: return null
            // 任意的圣遗物
            var tmpRandomScore = 0.0
            var tmpRandomRelicsDTO: RelicsDTO? = null
            // 符合类型的圣遗物
            var tmpTargetScore = 0.0
            var tmpTargetRelicsDTO: RelicsDTO? = null
            relicsDTOs.forEach(Consumer { relicsDTO: RelicsDTO ->
                val relicsAttributes = relicsDTO.attributes
                var score = Optional.ofNullable(relicsAttributes.criticalStrikeRate).orElse(0.0) * 2 * Optional.ofNullable(relicsYield.criticalStrikeRate).orElse(0.0) + Optional.ofNullable(relicsAttributes.criticalStrikeDamage).orElse(0.0) * 1 * Optional.ofNullable(relicsYield.criticalStrikeDamage).orElse(0.0) + Optional.ofNullable(relicsAttributes.proficients).orElse(0.0) * 0.33 * Optional.ofNullable(relicsYield.proficients).orElse(0.0) + Optional.ofNullable(relicsAttributes.chargingRate).orElse(0.0) * 1.1979 * Optional.ofNullable(relicsYield.chargingRate).orElse(0.0) + Optional.ofNullable(relicsAttributes.maxAttack).orElse(0.0) * 1.33 * Optional.ofNullable(relicsYield.maxAttack).orElse(0.0) + Optional.ofNullable(relicsAttributes.maxHealth).orElse(0.0) * 1.33 * Optional.ofNullable(relicsYield.maxHealth).orElse(0.0) + Optional.ofNullable(relicsAttributes.maxDefense).orElse(0.0) * 1.06 * Optional.ofNullable(relicsYield.maxDefense).orElse(0.0)
                if (relicsDTO.type == EquipType.DRESS &&
                        relicsAttributes.appendProp == AppendProp.CRITICAL_STRIKE_DAMAGE || relicsAttributes.appendProp == AppendProp.CRITICAL_STRIKE_RATE) {
                    // 如果是暴击率或者暴击头，加20分
                    score += 20.0
                }
                relicsDTO.score = score
                if (score > tmpRandomScore) {
                    tmpRandomRelicsDTO = relicsDTO
                    tmpRandomScore = score
                }
                if (TextUtils.equals(Constants.LOC_INFO?.get(groupType)?.asString, relicsDTO.groupType) && score > tmpTargetScore) {
                    tmpTargetRelicsDTO = relicsDTO
                    tmpTargetScore = score
                }
            })
            Stream.of(tmpRandomRelicsDTO, tmpTargetRelicsDTO).collect(Collectors.toList())
        } catch (e: java.lang.Exception) {
            null
        }
    }

    companion object {
        private const val TAG = "MainPresenter"
    }
}