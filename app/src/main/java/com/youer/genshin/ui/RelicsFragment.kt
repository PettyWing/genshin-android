package com.youer.genshin.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.youer.genshin.App
import com.youer.genshin.constants.Constants
import com.youer.genshin.databinding.FragmentRelicsBinding
import com.youer.genshin.resp.LoginResp
import com.youer.genshin.resp.RelicsDTO
import com.youer.genshin.resp.Result
import com.youer.genshin.utils.SPUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author youer
 * @date 9/20/23
 */
class RelicsFragment : BaseFragment() {
    private lateinit var binding: FragmentRelicsBinding
    private var adapter: RelicsAdapter = RelicsAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRelicsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        binding.list.adapter = adapter
        binding.multiImport.setOnClickListener(multiImportListener)
        login(uid)
    }

    var multiImportListener = View.OnClickListener {
        App.apiService.loadRelic(uid)
                .enqueue(object : Callback<Result<List<RelicsDTO>>> {
                    override fun onResponse(call: Call<Result<List<RelicsDTO>>>, response: Response<Result<List<RelicsDTO>>>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (result.isSuccess) {
                                adapter.setData(result.result)
                                Toast.makeText(context, "同步完成", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<Result<List<RelicsDTO>>>, t: Throwable) {
                        Log.e(TAG, "onFailure: ", t)
                    }
                })
    }

    fun login(uid: Long?) {
        App.apiService.login(uid)
                .enqueue(object : Callback<Result<LoginResp>> {
                    override fun onResponse(call: Call<Result<LoginResp>>, response: Response<Result<LoginResp>>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (result.isSuccess) {
                                SPUtil.writeString(context, Constants.KEY_UID, uid.toString() + "")
                                adapter.setData(result.result?.relics)
                            } else {
                                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<Result<LoginResp>>, t: Throwable) {
                        Log.e(TAG, "onFailure: ", t)
                    }
                })
    }

    companion object {
        private const val TAG = "RelicsFragment"
    }
}