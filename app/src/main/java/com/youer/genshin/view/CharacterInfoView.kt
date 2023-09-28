package com.youer.genshin.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.youer.genshin.constants.Constants
import com.youer.genshin.databinding.ViewCharacterInfoBinding
import com.youer.genshin.req.CalculateReq.CharacterInfo
import com.youer.genshin.view.CharacterInfoView

/**
 * @author youer
 * @date 9/27/23
 */
class CharacterInfoView(context: Context, private val characterInfo: CharacterInfo) : LinearLayout(context) {
    private var binding: ViewCharacterInfoBinding
    var listener: DeleteListener? = null

    init {
        binding = ViewCharacterInfoBinding.inflate(LayoutInflater.from(context), this, true)
                .apply {
                    character.text = Constants.CHARACTERS?.let {
                        it[characterInfo.characterId].asString
                    } + "/" + Constants.RELICS?.let {
                        it[characterInfo.groupType].asString
                    }
                    delete.setOnClickListener {
                        listener?.onDelete(this@CharacterInfoView, characterInfo)
                    }


                }
    }

    interface DeleteListener {
        fun onDelete(view: View, characterInfo: CharacterInfo)
    }

}