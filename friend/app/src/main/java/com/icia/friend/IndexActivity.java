package com.icia.friend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.icia.friend.login.LoginActivity;
import com.icia.friend.session.Session;
import com.icia.friend.store.CategoryActivity;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

//        LoginSession.getU_code(IndexActivity.this);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /** ? 로그인 세션 유무 비교 후 없으면 로그인으로 이동 처리 어디서 할건지 ? **/
                // u_code 세션 값 없으면 로그인으로 이동
                if (Session.getU_code(IndexActivity.this).equals("")) {
                    Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {    // u_code 세션 값 있으면 카테고리로 이동
                    Intent intent = new Intent(IndexActivity.this, CategoryActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 3000);
    }

}