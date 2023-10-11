package com.youer.genshin.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.youer.genshin.App
import com.youer.genshin.constants.Constants
import com.youer.genshin.databinding.FragmentMyBinding
import com.youer.genshin.presenter.MainPresenter
import com.youer.genshin.resp.Result
import com.youer.genshin.utils.SPUtil
import com.youer.genshin.utils.StorageUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author youer
 * @date 9/20/23
 */
class MyFragment(presenter: MainPresenter) : BaseFragment(presenter) {
    private lateinit var binding: FragmentMyBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btCharacter.setOnClickListener { character }
            btRelics.setOnClickListener { relics }
            logout.setOnClickListener {
                SPUtil.removeKey(context, Constants.KEY_UID)
                startActivity(Intent(context, LoginActivity::class.java))
                (context as Activity?)?.finish()
            }
        }
        buildCharacter();
    }

    fun buildCharacter(){
        Constants.CHARACTERS = JsonObject()
        for ((key, value) in Constants.CHARACTERS_INFO?.entrySet()!!) {
            // 在这里对每个键值对进行操作
            var tmp = (value as JsonObject).get("NameTextMapHash")
            if(tmp!=null) {
                Constants.CHARACTERS?.add(key, Constants.LOC_INFO?.get(tmp.asString))
            }
        }
    }

    /**
     * 加载角色list
     */
    private val character: Unit
        get() {
            val call: Call<Result<JsonObject>> = App.apiService.character
            call.enqueue(object : Callback<Result<JsonObject>> {
                override fun onResponse(call: Call<Result<JsonObject>>, response: Response<Result<JsonObject>>) {
                    if (response.isSuccessful) {
                        Constants.CHARACTERS = response.body().result
                        StorageUtil.saveJsonToFile(context, Gson().toJson(response.body().result), Constants.FILE_CHARACTER)
                        Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Result<JsonObject>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ", t)
                }
            })
        }

    /**
     * 加载圣遗物list
     */
    private val relics: Unit
        get() {
            val call: Call<Result<JsonObject>> = App.apiService.relics
            call.enqueue(object : Callback<Result<JsonObject>> {
                override fun onResponse(call: Call<Result<JsonObject>>, response: Response<Result<JsonObject>>) {
                    if (response.isSuccessful) {
                        Constants.RELICS = response.body().result
                        StorageUtil.saveJsonToFile(context, Gson().toJson(response.body().result), Constants.FILE_RELICS)
                        Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Result<JsonObject>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ", t)
                }
            })
        }

    companion object {
        private const val TAG = "MyFragment"
    }
}