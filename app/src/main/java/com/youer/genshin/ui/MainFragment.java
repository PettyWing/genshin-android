package com.youer.genshin.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.youer.genshin.App;
import com.youer.genshin.databinding.FragmentMainBinding;
import com.youer.genshin.req.CalculateReq;
import com.youer.genshin.req.CalculateReq.CharacterInfo;
import com.youer.genshin.resp.CalculateResp;
import com.youer.genshin.resp.Result;
import com.youer.genshin.view.CharacterDialog;
import com.youer.genshin.view.CharacterDialog.ConfirmListener;
import com.youer.genshin.view.CharacterInfoView;
import com.youer.genshin.view.CharacterInfoView.DeleteListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author youer
 * @date 9/20/23
 */
public class MainFragment extends BaseFragment {

    private static final String TAG = "MainFragment";
    CalculateReq calculateReq;
    FragmentMainBinding binding;
    private ExpandAdapter adapter;
    List<CharacterInfo> characterInfoList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        characterInfoList = new ArrayList<>();
        calculateReq = new CalculateReq();
        calculateReq.setUid(uid);
        calculateReq.setCharacters(characterInfoList);
        adapter = new ExpandAdapter();
        binding.list.setAdapter(adapter);
        binding.add.setOnClickListener(addListener);
        binding.calculate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });
    }

    public OnClickListener addListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            CharacterDialog dialog = new CharacterDialog(v.getContext());
            dialog.setConfirmListener(new ConfirmListener() {
                @Override
                public void onConfirm(CharacterInfo characterInfo) {
                    characterInfoList.add(characterInfo);
                    CharacterInfoView view = new CharacterInfoView(getContext(), characterInfo);
                    view.setListener(new DeleteListener() {
                        @Override
                        public void onDelete(View view, CharacterInfo characterInfo) {
                            characterInfoList.remove(characterInfo);
                            binding.llCharacter.removeView(view);
                        }
                    });
                    binding.llCharacter.addView(view);
                }
            });
            dialog.show();
        }
    };

    public void calculate() {
        if (characterInfoList.isEmpty()) {
            Toast.makeText(getContext(), "请先添加角色", Toast.LENGTH_SHORT).show();
            return;
        }
        Call<Result<CalculateResp>> call = App.getApiService().calculateCharacterRelics(calculateReq);
        call.enqueue(new Callback<Result<CalculateResp>>() {
            @Override
            public void onResponse(Call<Result<CalculateResp>> call, Response<Result<CalculateResp>> response) {
                if (response.isSuccessful()) {
                    Result<CalculateResp> result = response.body();
                    if (result.isSuccess()) {
                        CalculateResp resp = result.getResult();
                        adapter.setData(resp.getCharacters());
                    }
                }
            }

            @Override
            public void onFailure(Call<Result<CalculateResp>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

}