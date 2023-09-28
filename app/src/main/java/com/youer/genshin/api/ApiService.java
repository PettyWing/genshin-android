package com.youer.genshin.api;

import java.util.List;

import com.google.gson.JsonObject;
import com.youer.genshin.req.CalculateReq;
import com.youer.genshin.resp.CalculateResp;
import com.youer.genshin.resp.LoginResp;
import com.youer.genshin.resp.RelicsDTO;
import com.youer.genshin.resp.Result;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author youer
 * @date 9/12/23
 */
public interface ApiService {
    @GET("login")
    Call<Result<LoginResp>> login(@Query("uid") Long uid);

    @GET("loadRelic")
    Call<Result<List<RelicsDTO>>> loadRelic(@Query("uid") Long uid);

    @GET("getCharacter")
    Call<Result<JsonObject>> getCharacter();

    @GET("getRelics")
    Call<Result<JsonObject>> getRelics();

    @POST("calculateCharacterRelics")
    Call<Result<CalculateResp>> calculateCharacterRelics(@Body CalculateReq req);
}
