package com.icia.friend;

import static com.icia.friend.remote.UserRemoteService.BASE_URL;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icia.friend.remote.UserRemoteService;
import com.icia.friend.session.Session;
import com.icia.friend.store.CategoryActivity;
import com.icia.friend.vo.UserVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderCompleteActivity extends AppCompatActivity {

    Retrofit u_retrofit;
    UserRemoteService u_service;

    TextView order_c_u_id, order_c_juso;
    Button order_c_button;

    UserVO vo = new UserVO();
    String u_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);

        u_retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        u_service = u_retrofit.create(UserRemoteService.class);

        order_c_u_id = findViewById(R.id.order_c_u_id);
        order_c_juso = findViewById(R.id.order_c_juso);
        order_c_button = findViewById(R.id.order_c_button);

        u_code = Session.getU_code(OrderCompleteActivity.this);

        Call<UserVO> call = u_service.read(u_code);
        call.enqueue(new Callback<UserVO>() {
            @Override
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                vo = response.body();

                order_c_u_id.setText(vo.getU_id());
                order_c_juso.setText(vo.getU_address());
            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable t) {

            }
        });

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(OrderCompleteActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        }, 5000);

        // 홈 가기 버튼 클릭
        order_c_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderCompleteActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });
    }

}