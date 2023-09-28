package com.youer.genshin.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import com.nex3z.flowlayout.FlowLayout;
import com.youer.genshin.constants.AppendProp;
import com.youer.genshin.constants.Constants;
import com.youer.genshin.databinding.DialogCharacterBinding;
import com.youer.genshin.req.CalculateReq.CharacterInfo;
import com.youer.genshin.req.CalculateReq.EquipTypesDTO;

/**
 * @author youer
 * @date 9/26/23
 */
public class CharacterDialog extends AppCompatDialog {

    DialogCharacterBinding binding;
    private ConfirmListener confirmListener;

    public CharacterDialog(@NonNull Context context) {
        super(context);
    }

    public CharacterDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTitle("设置角色");
        binding = DialogCharacterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initStyle();
        initView();
    }

    public CharacterDialog setConfirmListener(ConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
        return this;
    }

    private void initView() {
        List<String> characters = GsonUtil.getValues(Constants.CHARACTERS);
        ArrayAdapter<String> characterAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line,
            characters.toArray(new String[characters.size()]));
        binding.tvCharacter.setAdapter(characterAdapter);

        List<String> relics = GsonUtil.getValues(Constants.RELICS);
        ArrayAdapter<String> relicsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line,
            relics.toArray(new String[relics.size()]));
        binding.tvRelics.setAdapter(relicsAdapter);

        addCheckbox(binding.llShoes, AppendProp.shoesProps);
        addCheckbox(binding.llDress, AppendProp.dressProps);
        addCheckbox(binding.llRing, AppendProp.ringProps);
        binding.save.setOnClickListener(saveListener);
    }

    private void addCheckbox(FlowLayout container, List<AppendProp> props) {
        for (AppendProp prop : props) {
            CheckBox checkBox = new CheckBox(container.getContext());
            checkBox.setText(prop.getDisplayName());
            checkBox.setTag(prop.getName());
            container.addView(checkBox);
        }
    }

    private List<String> getCheckboxResult(FlowLayout container) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < container.getChildCount(); i++) {
            View view = container.getChildAt(i);
            if (view instanceof CheckBox) {
                CheckBox checkBox = (CheckBox)view;
                if (checkBox.isChecked()) {
                    result.add((String)checkBox.getTag());
                }
            }
        }
        return result;
    }

    public View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CharacterInfo characterInfo = new CharacterInfo();
            // 处理角色名
            if (binding.tvCharacter.getText() == null) {
                Toast.makeText(getContext(), "请输入角色名", Toast.LENGTH_SHORT).show();
                return;
            }
            String characterId = GsonUtil.getKey(Constants.CHARACTERS, binding.tvCharacter.getText().toString());
            if (characterId == null) {
                Toast.makeText(getContext(), "请输入正确的角色名", Toast.LENGTH_SHORT).show();
                return;
            }
            characterInfo.setCharacterId(characterId);
            // 处理圣遗物名
            if (binding.tvRelics.getText() == null) {
                Toast.makeText(getContext(), "请输入圣遗物名", Toast.LENGTH_SHORT).show();
                return;
            }
            String relicsId = GsonUtil.getKey(Constants.RELICS, binding.tvRelics.getText().toString());
            if (relicsId == null) {
                Toast.makeText(getContext(), "请输入正确的圣遗物", Toast.LENGTH_SHORT).show();
                return;
            }
            characterInfo.setGroupType(relicsId);
            // 处理圣遗物
            EquipTypesDTO equipTypesDTO = new EquipTypesDTO();
            // TODO: 9/27/23 后续允许不填写
            List<String> shoes = getCheckboxResult(binding.llShoes);
            List<String> dress = getCheckboxResult(binding.llDress);
            List<String> ring = getCheckboxResult(binding.llRing);
            if (shoes.isEmpty() || dress.isEmpty()||ring.isEmpty()){
                Toast.makeText(getContext(), "每个位置至少勾选一个词条", Toast.LENGTH_SHORT).show();
                return;
            }
            equipTypesDTO.setEquipShoes(shoes);
            equipTypesDTO.setEquipDress(dress);
            equipTypesDTO.setEquipRing(ring);
            characterInfo.setEquipTypes(equipTypesDTO);
            dismiss();
            if (confirmListener != null) {
                confirmListener.onConfirm(characterInfo);
            }
        }
    };

    public interface ConfirmListener {
        void onConfirm(CharacterInfo characterInfo);
    }

    /**
     * 初始化window背景框
     */
    private void initStyle() {
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth(); //设置dialog的宽度为当前手机屏幕的宽度-100,单位为dp
        getWindow().setAttributes(p);
    }
}