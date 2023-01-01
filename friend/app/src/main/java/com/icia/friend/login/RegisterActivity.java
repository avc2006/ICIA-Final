package com.icia.friend.login;

import static com.icia.friend.remote.UserRemoteService.BASE_URL;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.icia.friend.MainActivity;
import com.icia.friend.R;
import com.icia.friend.api.FCMMessagingService;
import com.icia.friend.remote.UserRemoteService;
import com.icia.friend.session.Session;
import com.icia.friend.vo.UserVO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterActivity extends AppCompatActivity {

    Retrofit retrofit;                              // 서버 http 통신
    UserRemoteService service;                      // 백엔드 연결

    private EditText edit_u_name, edit_u_id, edit_u_pass, edit_cf_u_pass, edit_birthday, edit_phone_num, edit_auth_num, edit_u_address;   // 사용자 입력 u_name, u_id, u_pass, cf_u_pass, phone number, u_address
    private RadioGroup group_gender;                // 성별 라디오 버튼 그룹
    private Button btn_send, btn_signup;            // SEND, SIGN UP 버튼

    String gender;
    String authNumber = "";                         // 인증 번호 확인 초기값

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 백엔드 연결
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(UserRemoteService.class);

        // layout 연결
        edit_u_name = findViewById(R.id.edit_u_name);
        edit_u_id = findViewById(R.id.edit_u_id);
        edit_u_pass = findViewById(R.id.edit_u_pass);
        edit_cf_u_pass = findViewById(R.id.edit_cf_u_pass);
        edit_birthday = findViewById(R.id.edit_birthday);
        group_gender = findViewById(R.id.group_gender);
        edit_phone_num = findViewById(R.id.edit_phone_num);
        edit_auth_num = findViewById(R.id.edit_auth_num);
        edit_u_address = findViewById(R.id.edit_u_address);
        btn_send = findViewById(R.id.btn_send);
        btn_signup = findViewById(R.id.btn_signup);

        // SEND 클릭 ( 문자 인증 )
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자가 입력한 휴대폰 번호, 인증 번호 String 타입으로 변환
                String phone_num = edit_phone_num.getText().toString();

                if (phone_num.equals("")) {
                    Toast.makeText(RegisterActivity.this, "휴대폰 번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone_num.length() != 11) {
                    Toast.makeText(RegisterActivity.this, "휴대폰 번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                Call<String> call = service.sendAuthSMS(phone_num);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        System.out.println("SEND :: RegisterActivity - service.sendAuthSMS : " + response.body());
                        Toast.makeText(RegisterActivity.this, "인증번호가 발송되었습니다\n 인증 번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                        authNumber = response.body();
                    }

                    @Override
                    // 인증번호 발송 실패 시
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "인증번호 발송 실패", Toast.LENGTH_SHORT).show();
                        System.out.println(".....오류 :: (SEND) RegisterActivity - service.sendAuthSMS : " + t.toString());
                    }
                });
            }
        });

        // 성별 라디오 버튼 값 String 타입으로 변환
        group_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_male:
                        gender = "남자";
                        System.out.println("gender = " + gender);
                        break;

                    case R.id.radio_female:
                        gender = "여자";
                        System.out.println("gender = " + gender);
                        break;
                }
            }
        });

        // SIGN UP 클릭 ( 회원가입 )
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // 사용자가 입력한 값 String 타입으로 변환
                String u_name = edit_u_name.getText().toString();
                String u_id = edit_u_id.getText().toString();
                String u_pass = edit_u_pass.getText().toString();
                String cf_u_pass = edit_cf_u_pass.getText().toString();
                String birthday = edit_birthday.getText().toString();
                String phone_num = edit_phone_num.getText().toString();
                String auth_num = edit_auth_num.getText().toString();
                String u_address = edit_u_address.getText().toString();

                // 빈 칸이 있으면 리턴
                if (u_name.equals("") || u_id.equals("") || u_pass.equals("") || cf_u_pass.equals("") || birthday.equals("")
                        || gender.equals("") || phone_num.equals("") || auth_num.equals("") || u_address.equals("")) {
                    Toast.makeText(RegisterActivity.this, "모든 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 생년월일 8자리 아니면 리턴
                if (birthday.length() != 8) {
                    Toast.makeText(RegisterActivity.this, "생년월일을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호와 비밀번호 확인이 일치하지 않으면 리턴
                if (!u_pass.equals(cf_u_pass)) {
                    Toast.makeText(RegisterActivity.this, "비밀번호와 비밀번호 확인이 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 인증번호 일치하지 않으면
                if (!auth_num.equals(authNumber)) {
                    Toast.makeText(RegisterActivity.this, "인증번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();

                    // 인증번호 재발송
                    Call<String> call = service.sendAuthSMS(phone_num);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            System.out.println("SIGN UP :: RegisterActivity - service.sendAuthSMS : " + response.body());
                            Toast.makeText(RegisterActivity.this, "새로운 인증번호가 발송되었습니다\n 인증 번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                            authNumber = response.body();
                        }

                        @Override
                        // 인증번호 발송 실패 시
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "인증번호 발송 실패", Toast.LENGTH_SHORT).show();
                            System.out.println(".....오류 :: (SIGN UP) RegisterActivity - service.sendAuthSMS : " + t.toString());
                        }
                    });
                    return;
                }

                // 사용자가 입력한 값으로 UserVO 설정
                UserVO userVO = new UserVO();
                userVO.setU_name(u_name);
                userVO.setU_id(u_id);
                userVO.setU_pass(u_pass);
                userVO.setU_address(u_address);
                userVO.setBirthday(birthday);
                userVO.setGender(gender);
                userVO.setToken(Session.getToken(RegisterActivity.this));
                System.out.println("RegisterActivity - btn_signup : " + userVO.toString());

                // 회원가입 진행 여부 컨펌
                AlertDialog.Builder box = new AlertDialog.Builder(RegisterActivity.this);
                box.setTitle("질의");
                box.setMessage("회원가입 하시겠습니까?");
                // 예 클릭 시
                box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<Integer> call = service.and_insert(userVO);
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
//                                System.out.println("RegisterActivity - service.and_insert : " + response.body());
                                if (response.body() == 1) {
                                    Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);  // 회원가입 완료 후 메인 액티비티로 이동
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "같은 아이디가 존재합니다", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다", Toast.LENGTH_SHORT).show();    // 회원가입 실패 시
                                System.out.println(".....오류 :: RegisterActivity - service.and_insert : " + t.toString());
                            }
                        });
                    }
                });
                // 아니요 클릭 시
                box.setNegativeButton("아니요", null);
                box.show();
            }
        });
    }

}
