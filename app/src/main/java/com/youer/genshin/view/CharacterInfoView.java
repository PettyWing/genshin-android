package com.youer.genshin.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.youer.genshin.constants.Constants;
import com.youer.genshin.databinding.ViewCharacterInfoBinding;
import com.youer.genshin.req.CalculateReq.CharacterInfo;

/**
 * @author youer
 * @date 9/27/23
 */
public class CharacterInfoView extends LinearLayout {
    ViewCharacterInfoBinding binding;
    private CharacterInfo characterInfo;
    private DeleteListener listener;

    public CharacterInfoView(Context context, CharacterInfo characterInfo) {
        super(context);
        this.characterInfo = characterInfo;
        initView();
    }

    public CharacterInfoView setListener(DeleteListener listener) {
        this.listener = listener;
        return this;
    }

    private void initView() {
        binding = ViewCharacterInfoBinding.inflate(LayoutInflater.from(getContext()), this, true);
        binding.characterInfo.setText(
            Constants.CHARACTERS.get(characterInfo.getCharacterId()).getAsString()
                + "/"
                + Constants.RELICS.get(characterInfo.getGroupType()).getAsString());
        binding.delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDelete(CharacterInfoView.this, characterInfo);
                }
            }
        });
    }

    public interface DeleteListener {
        void onDelete(View view, CharacterInfo characterInfo);
    }

}