package com.youer.genshin.ui;

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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.youer.genshin.App;
import com.youer.genshin.constants.Constants;
import com.youer.genshin.databinding.FragmentRelicsBinding;
import com.youer.genshin.resp.LoginResp;
import com.youer.genshin.resp.RelicsDTO;
import com.youer.genshin.resp.Result;
import com.youer.genshin.utils.SPUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author youer
 * @date 9/20/23
 */
public class RelicsFragment extends BaseFragment {

    private static final String TAG = "RelicsFragment";
    private FragmentRelicsBinding binding;
    private RelicsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRelicsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new RelicsAdapter();
        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.list.setAdapter(adapter);
        binding.list.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        binding.multiImport.setOnClickListener(multiImportListener);
        login(uid);
    }

    public OnClickListener multiImportListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Call<Result<List<RelicsDTO>>> call = App.getApiService().loadRelic(uid);
            call.enqueue(new Callback<Result<List<RelicsDTO>>>() {
                @Override
                public void onResponse(Call<Result<List<RelicsDTO>>> call, Response<Result<List<RelicsDTO>>> response) {
                    if (response.isSuccessful()) {
                        Result<List<RelicsDTO>> result = response.body();
                        if (result.isSuccess()) {
                            adapter.setData(result.getResult());
                            Toast.makeText(getContext(), "同步完成", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Result<List<RelicsDTO>>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        }
    };

    public void login(long uid) {
        Call<Result<LoginResp>> call = App.getApiService().login(uid);
        call.enqueue(new Callback<Result<LoginResp>>() {
            @Override
            public void onResponse(Call<Result<LoginResp>> call, Response<Result<LoginResp>> response) {
                if (response.isSuccessful()) {
                    Result<LoginResp> result = response.body();
                    if (result.isSuccess()) {
                        SPUtil.writeString(getContext(), Constants.KEY_UID, uid + "");
                        adapter.setData(result.getResult().getRelics());
                    }
                }
            }

            @Override
            public void onFailure(Call<Result<LoginResp>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

}