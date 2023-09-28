package com.youer.genshin.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.nex3z.flowlayout.FlowLayout
import com.youer.genshin.constants.AppendProp
import com.youer.genshin.constants.Constants
import com.youer.genshin.databinding.DialogCharacterBinding
import com.youer.genshin.req.CalculateReq.CharacterInfo
import com.youer.genshin.req.CalculateReq.EquipTypesDTO
import java.util.*
import java.util.function.Consumer


/**
 * @author youer
 * @date 9/26/23
 */
class CharacterDialog(context: Context) : AppCompatDialog(context) {
    private lateinit var binding: DialogCharacterBinding
    var confirmListener: ConfirmListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setTitle("设置角色")
        binding = DialogCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initStyle()
        initView()
    }

    private fun initView() {
        // 初始化角色adapter
        val characters = GsonUtil.getValues(Constants.CHARACTERS)
        val characterAdapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line,
                characters.toTypedArray())
        binding.tvCharacter.setAdapter(characterAdapter)

        // 初始化圣遗物adapter
        val relics = GsonUtil.getValues(Constants.RELICS)
        val relicsAdapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line,
                relics.toTypedArray())
        binding.tvRelics.setAdapter(relicsAdapter)

        // 添加圣遗物主位置的选择
        addCheckbox(binding.llShoes, AppendProp.shoesProps)
        addCheckbox(binding.llDress, AppendProp.dressProps)
        addCheckbox(binding.llRing, AppendProp.ringProps)
        binding.save.setOnClickListener(saveListener)
    }

    private fun addCheckbox(container: FlowLayout, props: List<AppendProp>) {
        props.forEach(Consumer<AppendProp> {
            container.addView(CheckBox(container.context).apply {
                text = it.displayName
                tag = it.key
            })
        })
    }

    private fun getCheckboxResult(container: FlowLayout): List<String> {
        var result = ArrayList<String>()
        for (i in 0 until container.childCount) {
            val view = container.getChildAt(i)
            if (view is CheckBox) {
                if (view.isChecked) {
                    result.add(view.tag as String)
                }
            }
        }
        return result
    }

    private var saveListener = View.OnClickListener {
        val characterInfo = CharacterInfo()
        // 处理角色名
        if (binding.tvCharacter.text == null) {
            Toast.makeText(context, "请输入角色名", Toast.LENGTH_SHORT).show()
            return@OnClickListener
        }
        val characterId = Constants.CHARACTERS?.let { it1 -> GsonUtil.getKey(it1, binding.tvCharacter.text.toString()) }
        if (characterId == null) {
            Toast.makeText(context, "请输入正确的角色名", Toast.LENGTH_SHORT).show()
            return@OnClickListener
        }
        // 处理圣遗物名
        if (binding.tvRelics.text == null) {
            Toast.makeText(context, "请输入圣遗物名", Toast.LENGTH_SHORT).show()
            return@OnClickListener
        }
        val relicsId = Constants.RELICS?.let { it1 -> GsonUtil.getKey(it1, binding.tvRelics.text.toString()) }
        if (relicsId == null) {
            Toast.makeText(context, "请输入正确的圣遗物", Toast.LENGTH_SHORT).show()
            return@OnClickListener
        }
        // 处理圣遗物
        val equipTypesDTO = EquipTypesDTO()
        // TODO: 9/27/23 后续允许不填写
        val shoes = getCheckboxResult(binding.llShoes)
        val dress = getCheckboxResult(binding.llDress)
        val ring = getCheckboxResult(binding.llRing)
        if (shoes.isEmpty() || dress.isEmpty() || ring.isEmpty()) {
            Toast.makeText(context, "每个位置至少勾选一个词条", Toast.LENGTH_SHORT).show()
            return@OnClickListener
        }
        characterInfo.apply {
            equipTypes = equipTypesDTO.apply {
                equipShoes = shoes
                equipDress = dress
                equipRing = ring
            }
            this.characterId = characterId
            characterInfo.groupType = relicsId
        }
        dismiss()
        confirmListener?.onConfirm(characterInfo)
    }

    interface ConfirmListener {
        fun onConfirm(characterInfo: CharacterInfo)
    }

    /**
     * 初始化window背景框
     */
    private fun initStyle() {
        window?.attributes = window?.attributes?.apply {
            width = window?.windowManager?.defaultDisplay?.width!!
        }
    }
}