package com.youer.genshin.ui

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.youer.genshin.constants.AppendProp
import com.youer.genshin.constants.AppendProp.Companion.getType
import com.youer.genshin.constants.Constants
import com.youer.genshin.databinding.ItemCharacterTitleBinding
import com.youer.genshin.databinding.ItemRelicsBinding
import com.youer.genshin.resp.CalculateResp.CharacterResp
import com.youer.genshin.resp.RelicsAttributes
import com.youer.genshin.resp.RelicsDTO
import com.youer.genshin.utils.CommonUtils

/**
 * @author youer
 * @date 9/27/23
 */
class ExpandAdapter : BaseExpandableListAdapter() {
    private var data: List<CharacterResp> = ArrayList()
    fun setData(data: List<CharacterResp>?) {
        this.data = data?:ArrayList()
        notifyDataSetChanged()
    }

    override fun getGroupCount(): Int {
        return data.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return if (data[groupPosition].relicsDTOS == null) {
            0
        } else {
            data[groupPosition].relicsDTOS!!.size
        }
    }

    override fun getGroup(groupPosition: Int): Any {
        return data[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any? {
        return if (data[groupPosition].relicsDTOS == null) {
            null
        } else {
            data[groupPosition].relicsDTOS?.get(childPosition)
        }
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        val binding: ItemCharacterTitleBinding
        if (convertView == null) {
            binding = ItemCharacterTitleBinding.inflate(LayoutInflater.from(parent.context))
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemCharacterTitleBinding
        }
        val characterResp = getGroup(groupPosition) as CharacterResp
        if (characterResp != null) {
            binding.apply {
                character.text = Constants.CHARACTERS?.let { it[characterResp.characterId].asString }
                score.text = CommonUtils.format(characterResp.score)
            }
        }
        return binding.root
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        val binding: ItemRelicsBinding
        if (convertView == null) {
            binding = ItemRelicsBinding.inflate(LayoutInflater.from(parent.context))
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemRelicsBinding
        }
        val relicsDTO = getChild(groupPosition, childPosition) as RelicsDTO
        if (relicsDTO != null) {
            binding.apply {
                groupType.text = relicsDTO.groupType
                type.text = relicsDTO.equipTypeName
                mainProp.text = relicsDTO.attributes?.appendPropName
                mainValue.text = CommonUtils.displayRelicsValue(relicsDTO.attributes?.appendProp?.apiName, relicsDTO.attributes?.mainValue)
                if (relicsDTO.score == null) {
                    score.visibility = View.GONE
                } else {
                    score.visibility = View.VISIBLE
                    score.text = CommonUtils.format(relicsDTO.score)
                }
                buildRelicsSubValueView(binding.llSubValue, relicsDTO.attributes)
            }

        }
        return binding.root
    }

    /**
     * 构建圣遗物子属性View
     */
    private fun buildRelicsSubValueView(container: LinearLayout, relicsAttributes: RelicsAttributes?) {
        val attributes = Gson().fromJson(Gson().toJson(relicsAttributes), JsonObject::class.java)
        container.removeAllViews()
        for ((key, value) in attributes.entrySet()) {
            if (!TextUtils.equals("appendPropName", key) && !TextUtils.equals("mainValue", key) && !TextUtils.equals("appendProp", key)) {
                val textView = TextView(container.context)
                textView.text = (AppendProp.getTypeByApiName(key)?.displayName ?: "") + "：" + CommonUtils.displayRelicsValue(key, value.asDouble)
                container.addView(textView)
            }
        }
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}