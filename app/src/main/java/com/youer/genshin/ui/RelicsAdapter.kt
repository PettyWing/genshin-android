package com.youer.genshin.ui

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.youer.genshin.constants.AppendProp.Companion.getType
import com.youer.genshin.databinding.ItemRelicsBinding
import com.youer.genshin.resp.RelicsAttributes
import com.youer.genshin.resp.RelicsDTO
import com.youer.genshin.utils.CommonUtils

/**
 * @author youer
 * @date 9/26/23
 */
class RelicsAdapter : RecyclerView.Adapter<RelicsAdapter.ViewHolder>() {
    private var data: List<RelicsDTO>? = null
    fun setData(data: List<RelicsDTO>?) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRelicsBinding.inflate(LayoutInflater.from(parent.context))
        binding.root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return ViewHolder(binding)
    }

    /**
     * 构建圣遗物子属性View
     */
    private fun buildRelicsSubValueView(container: LinearLayout, relicsAttributes: RelicsAttributes?) {
        val gson = Gson()
        val attributes = gson.fromJson(gson.toJson(relicsAttributes), JsonObject::class.java)
        container.removeAllViews()
        for ((key, value) in attributes.entrySet()) {
            if (!TextUtils.equals("appendPropName", key) && !TextUtils.equals("mainValue", key) && !TextUtils.equals("appendProp", key)) {
                val textView = TextView(container.context)
                textView.text = getType(key)?.displayName ?: "" + "：" + CommonUtils.displayRelicsValue(key, value.asDouble)
                container.addView(textView)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val relicsDTO = data!![position] ?: return
        val binding = holder.binding.apply {
            groupType.text = relicsDTO.groupType
            type.text = relicsDTO.equipTypeName
            mainProp.text = relicsDTO.attributes!!.appendPropName
            mainValue.text = CommonUtils.displayRelicsValue(CommonUtils.convertCamelCase(relicsDTO.attributes!!.appendProp), relicsDTO.attributes!!.mainValue!!)
            if (relicsDTO.score == null) {
                score.visibility = View.GONE
            } else {
                score.visibility = View.VISIBLE
                score.text = CommonUtils.format(relicsDTO.score!!)
            }
        }

        buildRelicsSubValueView(binding.llSubValue, relicsDTO.attributes)
    }

    override fun getItemCount(): Int {
        return if (data == null) 0 else data!!.size
    }

    class ViewHolder(var binding: ItemRelicsBinding) : RecyclerView.ViewHolder(binding.root)
}