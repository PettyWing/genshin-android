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
import com.youer.genshin.presenter.MainPresenter
import com.youer.genshin.presenter.ResultCallback
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
class RelicsFragment(var presenter: MainPresenter) : BaseFragment(presenter) {
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
        presenter.multiImport(uid, object : ResultCallback<List<RelicsDTO>?> {
            override fun success(relicsDTOS: List<RelicsDTO>?) {
                adapter.setData(relicsDTOS)
                Toast.makeText(context, "同步完成", Toast.LENGTH_SHORT).show()
            }
            override fun fail(s: String) {
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun login(uid: Long?) {
        presenter.login(uid ,object :ResultCallback<List<RelicsDTO>?>{
            override fun success(relicsDTOS: List<RelicsDTO>?) {
                    adapter.setData(relicsDTOS)
            }

            override fun fail(s: String) {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
            }

        })
    }

    companion object {
        private const val TAG = "RelicsFragment"
    }
}