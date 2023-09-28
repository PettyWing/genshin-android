package com.youer.genshin.ui;

import java.util.List;
import java.util.Map;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.youer.genshin.constants.AppendProp;
import com.youer.genshin.databinding.ItemRelicsBinding;
import com.youer.genshin.resp.RelicsAttributes;
import com.youer.genshin.resp.RelicsDTO;
import com.youer.genshin.utils.CommonUtils;

/**
 * @author youer
 * @date 9/26/23
 */
public class RelicsAdapter extends RecyclerView.Adapter<RelicsAdapter.ViewHolder> {
    private List<RelicsDTO> data;

    public void setData(List<RelicsDTO> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRelicsBinding binding = ItemRelicsBinding.inflate(LayoutInflater.from(parent.getContext()));
        binding.getRoot().setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        ViewHolder viewHolder = new ViewHolder(binding);
        return viewHolder;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        RelicsDTO relicsDTO = data.get(position);
        if (relicsDTO == null) {
            return;
        }
        ItemRelicsBinding binding = holder.binding;
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

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ItemRelicsBinding binding;

        public ViewHolder(ItemRelicsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
} 