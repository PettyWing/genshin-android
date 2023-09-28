package com.youer.genshin;

import android.app.Application;
import com.youer.genshin.api.ApiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author youer
 * @date 9/12/23
 */
public class App extends Application {

    private static ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            // 添加OkHttp的配置（如超时时间等）
            .build();

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://120.26.163.1:6192/") // 设置API的基本URL
            .client(okHttpClient) // 设置OkHttpClient
            .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
            .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static ApiService getApiService() {
        return apiService;
    }
}