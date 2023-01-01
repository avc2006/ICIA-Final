package com.icia.friend.post;

import static com.icia.friend.remote.PostRemoteService.BASE_URL;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.icia.friend.R;
import com.icia.friend.session.Session;
import com.icia.friend.remote.PostRemoteService;
import com.icia.friend.vo.ConditionVO;
import com.icia.friend.vo.PostVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostInsertActivity extends AppCompatActivity {

    Retrofit retrofit;
    PostRemoteService p_service;

    EditText p_i_title, p_i_body;
    TextView p_s_name;
    Button p_insert_button;
    Spinner ageSpinner, personSpinner;
    RadioGroup selected;

    String gender;
    String s_code;
    int age,headcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_insert);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        p_service = retrofit.create(PostRemoteService.class);

        Intent intent = getIntent();
        s_code = intent.getStringExtra("s_code");

        ageSpinner = findViewById(R.id.ageSpinner);
        personSpinner = findViewById(R.id.personSpinner);
        selected = findViewById(R.id.gender);
        p_s_name = findViewById(R.id.p_s_name);
        p_i_title = findViewById(R.id.p_i_title);
        p_i_body = findViewById(R.id.p_i_body);
        p_insert_button = findViewById(R.id.p_insert_button);

        ArrayAdapter ageAdapter = ArrayAdapter.createFromResource(this, R.array.postArray,
                android.R.layout.simple_spinner_dropdown_item);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(ageAdapter);

        ArrayAdapter personAdapter = ArrayAdapter.createFromResource(this, R.array.postPersonArray,
                android.R.layout.simple_spinner_dropdown_item);
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        personSpinner.setAdapter(personAdapter);

        // 나이 조건
        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        age = 10;
                        break;

                    case 1:
                        age = 20;
                        break;

                    case 2:
                        age = 30;
                        break;

                    case 3:
                        age = 40;
                        break;

                    case 4:
                        age = 50;
                        break;
                }
                System.out.println("PostInsertActivity - onItemSelected : " + age);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 성별 조건
        selected.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.M:
                        gender = "남자";
                        break;

                    case R.id.W:
                        gender = "여자";
                        break;

                    case R.id.A:
                        gender = "무관";
                        break;
                }
            }
        });

        // 인원수 조건
        personSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        headcount = 2;
                        break;

                    case 1:
                        headcount = 3;
                        break;

                    case 2:
                        headcount = 4;
                        break;

                }
                System.out.println("PostInsertActivity - personSpinner : " + headcount);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        p_insert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // s_code, u_code, p_title, p_content, headcount, gender, age
                String p_title = p_i_title.getText().toString();    // p_title
                String p_content = p_i_body.getText().toString();   // p_content
//                System.out.println("PostInsertActivity - p_insert_button :: check");

                if (p_title.equals("") || p_content.equals("")) {
                    Toast.makeText(PostInsertActivity.this, "모든 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder box = new AlertDialog.Builder(PostInsertActivity.this);
                box.setTitle("질의");
                box.setMessage("등록 하시겠습니까?");
                box.setPositiveButton("예", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PostVO vo = new PostVO();
                        vo.setS_code(s_code);
                        vo.setU_code(Session.getU_code(PostInsertActivity.this));
                        vo.setP_title(p_title);
                        vo.setP_content(p_content);
                        vo.setAge(age);
                        vo.setGender(gender);
                        vo.setHeadcount(headcount);

                        System.out.println("PostInsertActivity : " + vo.toString());

                        Call<Void> call = p_service.insert(vo);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println("PostInsertActivity - call : " + vo.toString());
                                Toast.makeText(getApplicationContext(), "등록이 완료되었습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PostInsertActivity.this, PostListActivity.class);
                                intent.putExtra("s_code", s_code);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println(".....오류 :: PostInsertActivity - call : " + vo.toString());
                            }
                        });
                    }
                });
                box.setNegativeButton("아니요", null);
                box.show();
            }
        });
    }

}