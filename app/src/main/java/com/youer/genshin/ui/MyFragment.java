package com.youer.genshin.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.youer.genshin.App;
import com.youer.genshin.constants.Constants;
import com.youer.genshin.databinding.FragmentMyBinding;
import com.youer.genshin.resp.Result;
import com.youer.genshin.utils.SPUtil;
import com.youer.genshin.utils.StorageUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author youer
 * @date 9/20/23
 */
public class MyFragment extends BaseFragment {

    FragmentMyBinding binding;
    private static final String TAG = "MyFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMyBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btCharacter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getCharacter();
            }
        });
        binding.btRelics.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getRelics();
            }
        });
        binding.logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtil.removeKey(getContext(), Constants.KEY_UID);
                startActivity(new Intent(getContext(), LoginActivity.class));
                ((Activity)getContext()).finish();
            }
        });
        if (StorageUtil.fileExists(getContext(), Constants.FILE_CHARACTER)) {
            Constants.CHARACTERS = new Gson().fromJson(StorageUtil.readJsonFromFile(getContext(), Constants.FILE_CHARACTER), JsonObject.class);
        } else {
            getCharacter();
        }
        if (StorageUtil.fileExists(getContext(), Constants.FILE_RELICS)) {
            Constants.RELICS = new Gson().fromJson(StorageUtil.readJsonFromFile(getContext(), Constants.FILE_RELICS), JsonObject.class);
        } else {
            getCharacter();
        }
        getRelics();
    }

    /**
     * 加载角色list
     */
    public void getCharacter() {

        Call<Result<JsonObject>> call = App.getApiService().getCharacter();
        call.enqueue(new Callback<Result<JsonObject>>() {
            @Override
            public void onResponse(Call<Result<JsonObject>> call, Response<Result<JsonObject>> response) {
                if (response.isSuccessful()) {
                    Constants.CHARACTERS = response.body().getResult();
                    StorageUtil.saveJsonToFile(getContext(), new Gson().toJson(response.body().getResult()), Constants.FILE_CHARACTER);
                }
            }

            @Override
            public void onFailure(Call<Result<JsonObject>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    /**
     * 加载圣遗物list
     */
    public void getRelics() {

        Call<Result<JsonObject>> call = App.getApiService().getRelics();
        call.enqueue(new Callback<Result<JsonObject>>() {
            @Override
            public void onResponse(Call<Result<JsonObject>> call, Response<Result<JsonObject>> response) {
                if (response.isSuccessful()) {
                    Constants.RELICS = response.body().getResult();
                    StorageUtil.saveJsonToFile(getContext(), new Gson().toJson(response.body().getResult()), Constants.FILE_RELICS);
                }
            }

            @Override
            public void onFailure(Call<Result<JsonObject>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}