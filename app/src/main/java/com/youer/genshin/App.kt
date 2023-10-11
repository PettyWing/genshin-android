package com.youer.genshin

import android.app.Application
import com.youer.genshin.api.ApiService
import com.youer.genshin.api.EnkaService
import okhttp3.OkHttpClient
import org.litepal.LitePal
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author youer
 * @date 9/12/23
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
        val okHttpClient = OkHttpClient.Builder() // 添加OkHttp的配置（如超时时间等）
                .build()
        // 后续再通过远端来请求数据
        val retrofit = Retrofit.Builder()
                .baseUrl("http://120.26.163.1:6192/") // 设置API的基本URL
                .client(okHttpClient) // 设置OkHttpClient
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .build()
        apiService = retrofit.create(ApiService::class.java)

        val enkaRetrofit = Retrofit.Builder()
                .baseUrl("https://enka.network/") // 设置API的基本URL
                .client(okHttpClient) // 设置OkHttpClient
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .build()
        enkaService = enkaRetrofit.create(EnkaService::class.java)
    }

    companion object {
        lateinit var apiService: ApiService
        lateinit var enkaService: EnkaService
    }
}