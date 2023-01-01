package com.icia.friend.login;

import static com.icia.friend.remote.UserRemoteService.BASE_URL;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.icia.friend.MainActivity;
import com.icia.friend.R;
import com.icia.friend.remote.UserRemoteService;
import com.icia.friend.vo.UserVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KakaoRegisterActivity extends AppCompatActivity {

    Retrofit retrofit;          // 서버 http 통신
    UserRemoteService service;  // 백엔드 연결

    private EditText edit_u_name, edit_u_id, edit_u_pass, edit_cf_u_pass, edit_u_address;   // 사용자 입력 u_name, u_id, u_pass, cf_u_pass, phone number, u_address
    private Button btn_signup;  // SIGN UP 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_register);

        // 백엔드 연결
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(UserRemoteService.class);

        // layout 연결
        edit_u_name = findViewById(R.id.edit_u_name);
        edit_u_id = findViewById(R.id.edit_u_id);
        edit_u_pass = findViewById(R.id.edit_u_pass);
        edit_cf_u_pass = findViewById(R.id.edit_cf_u_pass);
        edit_u_address = findViewById(R.id.edit_u_address);
        btn_signup = findViewById(R.id.btn_signup);

        // SIGN UP 클릭 ( 회원가입 )
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자가 입력한 값 String 타입으로 변환
                String u_name = edit_u_name.getText().toString();
                String u_id = edit_u_id.getText().toString();
                String u_pass = edit_u_pass.getText().toString();
                String cf_u_pass = edit_cf_u_pass.getText().toString();
                String u_address = edit_u_address.getText().toString();

                // 빈 칸이 있으면 리턴
                if (u_name.equals("") || u_id.equals("") || u_pass.equals("") || cf_u_pass.equals("") || u_address.equals("")) {
                    Toast.makeText(KakaoRegisterActivity.this, "모든 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호와 비밀번호 확인이 일치하지 않으면 리턴
                if (!u_pass.equals(cf_u_pass)) {
                    Toast.makeText(KakaoRegisterActivity.this, "비밀번호와 비밀번호 확인이 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = getIntent();
                String u_code = intent.getStringExtra("u_code");
//                System.out.println("KakaoRegisterActivity - u_code : " + u_code);

                // 사용자가 입력한 값으로 UserVO 설정
                UserVO userVO = new UserVO();
                userVO.setU_code(u_code);
                userVO.setU_name(u_name);
                userVO.setU_id(u_id);
                userVO.setU_pass(u_pass);
                userVO.setU_address(u_address);
//                System.out.println("KakaoRegisterActivity : " + userVO.toString());

                // 회원가입 진행 여부 컨펌
                AlertDialog.Builder box = new AlertDialog.Builder(KakaoRegisterActivity.this);
                box.setTitle("질의");
                box.setMessage("회원가입 하시겠습니까?");
                // 예 클릭 시
                box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<Integer> call = service.and_kakao_insert(userVO);
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
//                                System.out.println("KakaoRegisterActivity - call : " + response.body());
                                if (response.body() == 2) {
                                    Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(KakaoRegisterActivity.this, MainActivity.class);  // 회원가입 완료 후 메인 액티비티로 이동
                                    startActivity(intent);
                                } else if (response.body() == 1) {
                                    Toast.makeText(getApplicationContext(), "이미 가입된 사용자입니다", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    Toast.makeText(getApplicationContext(), "같은 아이디가 존재합니다", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다", Toast.LENGTH_SHORT).show();    // 회원가입 실패 시
                                System.out.println(".....오류 :: KakaoRegisterActivity - call : " + t.toString());
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