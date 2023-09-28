package com.youer.genshin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.youer.genshin.R;
import com.youer.genshin.utils.SPUtil;

import static com.youer.genshin.constants.Constants.KEY_UID;

/**
 * @author youer
 * @date 9/15/23
 */
public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String uid = SPUtil.readString(this, KEY_UID, null);
        if (!TextUtils.isEmpty(uid)) {
            // 已登录进入主页
            jump2MainActivity(uid);
        }
    }

    public void onLoginClick(View view) {
        String uid = ((EditText)findViewById(R.id.et_uid)).getText().toString();
        if (TextUtils.isEmpty(uid)) {
            Toast.makeText(this, "请输入uid", Toast.LENGTH_SHORT).show();
            return;
        }
        jump2MainActivity(uid);
    }

    public void jump2MainActivity(String uid) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(KEY_UID, Long.parseLong(uid));
        startActivity(intent);
        finish();
    }
}