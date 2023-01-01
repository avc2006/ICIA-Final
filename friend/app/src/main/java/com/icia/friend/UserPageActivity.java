package com.icia.friend;

import static com.icia.friend.remote.UserRemoteService.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.icia.friend.login.LoginActivity;
import com.icia.friend.remote.UserRemoteService;
import com.icia.friend.session.Session;
import com.icia.friend.vo.UserVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserPageActivity extends AppCompatActivity {

    Retrofit u_retrofit;
    UserRemoteService u_service;

    TextView nickname;
    LinearLayout heartLevel, profile, liked, customerService, logout;

    UserVO vo = new UserVO();
    String u_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        u_retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        u_service = u_retrofit.create(UserRemoteService.class);

        nickname = findViewById(R.id.nickname);
        heartLevel = findViewById(R.id.heartLevel);
        profile = findViewById(R.id.profile);
        liked = findViewById(R.id.liked);
        customerService = findViewById(R.id.customerService);
        logout = findViewById(R.id.logout);

        u_code = Session.getU_code(UserPageActivity.this);

        Call<UserVO> call = u_service.read(u_code);
        call.enqueue(new Callback<UserVO>() {
            @Override
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                vo = response.body();
                nickname.setText(vo.getU_name());
            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable t) {

            }
        });

        //등급별 클릭
        Dialog dialog = new Dialog(this);
        heartLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(UserPageActivity.this);
                final View view = factory.inflate(R.layout.heartlevel, null);
                Dialog dialog = new Dialog(UserPageActivity.this);
                dialog.setContentView(view);
                dialog.show();
            }
        });

        // 내 정보 수정 클릭
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPageActivity.this, UserUpdateActivity.class);
                startActivity(intent);
            }
        });

        // 즐겨찾기 클릭
        liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPageActivity.this, LikedActivity.class);
                startActivity(intent);
            }
        });

        //고객센터 클릭
        customerService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // 로그아웃 클릭
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.clearUserName(UserPageActivity.this);
                Toast.makeText(UserPageActivity.this, "로그아웃되었습니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserPageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}