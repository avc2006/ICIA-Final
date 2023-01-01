package com.icia.friend;

import static com.icia.friend.remote.ReportRemoteService.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.icia.friend.remote.ReportRemoteService;
import com.icia.friend.store.CategoryActivity;
import com.icia.friend.vo.CategoryVO;
import com.icia.friend.vo.ReportVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportActivity extends AppCompatActivity {

    Retrofit retrofit;
    ReportRemoteService service;

    TextView suer;
    private EditText defender, r_result;
    private Spinner r_type;
    private Button btn_report;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(ReportRemoteService.class);

        suer = findViewById(R.id.suer);
        defender = findViewById(R.id.defender);
        r_result = findViewById(R.id.r_result);
        r_type = findViewById(R.id.r_type);
        btn_report = findViewById(R.id.btn_report);

        suer.setText("u13");

        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r_defender = defender.getText().toString();
                String r_r_result = r_result.getText().toString();
                String s_suer = suer.getText().toString();

                if (r_defender.equals("") || r_r_result.equals("")) {
                    Toast.makeText(ReportActivity.this, "모든 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                ReportVO reportVO = new ReportVO();
                reportVO.setDefender(r_defender);
                reportVO.setSuer(s_suer);
                reportVO.setR_result(r_r_result);
                System.out.println("ReportActivity - btn_report : " + reportVO.toString());

                Call<Void> call = service.insert(reportVO);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent = new Intent(ReportActivity.this, CategoryActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "내용을 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
