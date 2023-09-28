package com.youer.genshin.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.youer.genshin.constants.AppendProp;
import com.youer.genshin.constants.Constants;
import com.youer.genshin.databinding.ItemCharacterTitleBinding;
import com.youer.genshin.databinding.ItemRelicsBinding;
import com.youer.genshin.resp.CalculateResp.CharacterResp;
import com.youer.genshin.resp.RelicsAttributes;
import com.youer.genshin.resp.RelicsDTO;
import com.youer.genshin.utils.CommonUtils;

/**
 * @author youer
 * @date 9/27/23
 */
public class ExpandAdapter extends BaseExpandableListAdapter {
    private List<CharacterResp> data = new ArrayList<>();

    public void setData(List<CharacterResp> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getRelicsDTOS().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getRelicsDTOS().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ItemCharacterTitleBinding binding;
        if (convertView == null) {
            binding = ItemCharacterTitleBinding.inflate(LayoutInflater.from(parent.getContext()));
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ItemCharacterTitleBinding)convertView.getTag();
        }
        CharacterResp characterResp = (CharacterResp)getGroup(groupPosition);
        if(characterResp !=null) {
            binding.character.setText(Constants.CHARACTERS.get(characterResp.getCharacterId()).getAsString());
            binding.score.setText(CommonUtils.format(characterResp.getScore()));
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ItemRelicsBinding binding;
        if (convertView == null) {
            binding = ItemRelicsBinding.inflate(LayoutInflater.from(parent.getContext()));
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ItemRelicsBinding)convertView.getTag();
        }
        RelicsDTO relicsDTO = (RelicsDTO)getChild(groupPosition, childPosition);
        if (relicsDTO != null) {
            binding.groupType.setText(relicsDTO.getGroupType());
            binding.type.setText(relicsDTO.getEquipTypeName());
            binding.mainProp.setText(relicsDTO.getAttributes().getAppendPropName());
            binding.mainValue.setText(
                CommonUtils.displayRelicsValue(CommonUtils.convertCamelCase(relicsDTO.getAttributes().getAppendProp()), relicsDTO.getAttributes().getMainValue()));
            if (relicsDTO.getScore() == null) {
                binding.score.setVisibility(View.GONE);
            } else {
                binding.score.setVisibility(View.VISIBLE);
                binding.score.setText(CommonUtils.format(relicsDTO.getScore()));
            }
            buildRelicsSubValueView(binding.llSubValue, relicsDTO.getAttributes());
        }
        return convertView;
    }

    /**
     * 构建圣遗物子属性View
     */
    private void buildRelicsSubValueView(LinearLayout container, RelicsAttributes relicsAttributes) {
        Gson gson = new Gson();
        JsonObject attributes = gson.fromJson(gson.toJson(relicsAttributes), JsonObject.class);
        container.removeAllViews();
        for (Map.Entry<String, JsonElement> entry : attributes.entrySet()) {
            if (!TextUtils.equals("appendPropName", entry.getKey()) && !TextUtils.equals("mainValue", entry.getKey()) && !TextUtils.equals("appendProp", entry.getKey())) {
                TextView textView = new TextView(container.getContext());
                textView.setText(AppendProp.getType(entry.getKey()).getDisplayName() + "：" + CommonUtils.displayRelicsValue(entry.getKey(), entry.getValue().getAsDouble()));
                container.addView(textView);
            }
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}