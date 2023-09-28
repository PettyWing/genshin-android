package com.youer.genshin.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.youer.genshin.constants.Constants.KEY_UID
import com.youer.genshin.databinding.ActivityLoginBinding
import com.youer.genshin.utils.SPUtil

/**
 * @author youer
 * @date 9/15/23
 */
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater);
        setContentView(binding.root)
        val uid = SPUtil.readString(this, KEY_UID, null)
        if (!TextUtils.isEmpty(uid)) {
            // 已登录进入主页
            jump2MainActivity(uid)
        }
    }

    fun onLoginClick(view: View?) {
        val uid = binding.etUid.text.toString()
        if (TextUtils.isEmpty(uid)) {
            Toast.makeText(this, "请输入uid", Toast.LENGTH_SHORT).show()
            return
        }
        jump2MainActivity(uid)
    }

    private fun jump2MainActivity(uid: String) {
        startActivity(Intent(this, MainActivity::class.java).apply {
            putExtra(KEY_UID, uid.toLong())
        })
        finish()
    }
}