package com.youer.genshin.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.youer.genshin.App
import com.youer.genshin.databinding.FragmentMainBinding
import com.youer.genshin.req.CalculateReq
import com.youer.genshin.req.CalculateReq.CharacterInfo
import com.youer.genshin.resp.CalculateResp
import com.youer.genshin.resp.Result
import com.youer.genshin.view.CharacterDialog
import com.youer.genshin.view.CharacterDialog.ConfirmListener
import com.youer.genshin.view.CharacterInfoView
import com.youer.genshin.view.CharacterInfoView.DeleteListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author youer
 * @date 9/20/23
 */
class MainFragment : BaseFragment() {
    private var calculateReq = CalculateReq()
    private lateinit var binding: FragmentMainBinding
    private var adapter: ExpandAdapter = ExpandAdapter()
    private var characterInfoList = ArrayList<CharacterInfo>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calculateReq.uid= uid
        calculateReq.characters = characterInfoList
        binding.list.setAdapter(adapter)
        binding.add.setOnClickListener(addListener)
        binding.calculate.setOnClickListener { calculate() }
    }

    private var addListener = View.OnClickListener { v ->
        CharacterDialog(v.context).apply {
            confirmListener = object : ConfirmListener {
                override fun onConfirm(characterInfo: CharacterInfo) {
                    characterInfoList.add(characterInfo)
                    CharacterInfoView(context, characterInfo)
                            .apply {
                                listener = object : DeleteListener {
                                    override fun onDelete(view: View, characterInfo: CharacterInfo) {
                                        characterInfoList.remove(characterInfo)
                                        binding.llCharacter.removeView(view)
                                    }
                                }
                                binding.llCharacter.addView(this)
                            }

                }

            }
            show()
        }
    }

    fun calculate() {
        if (characterInfoList.isEmpty()) {
            Toast.makeText(context, "请先添加角色", Toast.LENGTH_SHORT).show()
            return
        }
        App.apiService.calculateCharacterRelics(calculateReq)
                .enqueue(object : Callback<Result<CalculateResp>> {
                    override fun onResponse(call: Call<Result<CalculateResp>>, response: Response<Result<CalculateResp>>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (result.isSuccess) {
                                val resp = result.result
                                adapter.setData(resp?.characters ?: ArrayList())
                            } else {
                                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<Result<CalculateResp>>, t: Throwable) {
                        Log.e(TAG, "onFailure: ", t)
                    }
                })
    }

    companion object {
        private const val TAG = "MainFragment"
    }
}