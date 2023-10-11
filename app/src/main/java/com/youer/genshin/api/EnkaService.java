package com.youer.genshin.api;

import com.youer.genshin.resp.EnKaDO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author youer
 * @date 10/9/23
 */
public interface EnkaService {
    @GET("api/uid/{uid}")
    Call<EnKaDO> getByUid(@Path("uid") Long uid);
} 