package com.icia.friend.login;

import static com.icia.friend.remote.UserRemoteService.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.icia.friend.R;
import com.icia.friend.remote.UserRemoteService;
import com.icia.friend.session.Session;
import com.icia.friend.store.CategoryActivity;
import com.icia.friend.vo.UserVO;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    Retrofit retrofit;                                              // 서버 http 통신
    UserRemoteService service;                                     // 백엔드 연결

    private EditText edit_u_id, edit_u_pass;                        // 사용자 입력 u_id, u_pass
    private Button btn_signin;                                      // SIGN IN 버튼
    private TextView btn_register;                                  // Create an account ( 링크 역할 )
    private ImageView iv_kakaoLogin, iv_naverLogin, iv_appleLogin;  // 간편 로그인 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(TAG, "getKeyHash : " + KakaoAPI.getKeyHash(this));    // 카카오 로그인 키 해시

        // 백엔드 연결
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(UserRemoteService.class);

        // layout 연결
        edit_u_id = findViewById(R.id.edit_u_id);
        edit_u_pass = findViewById(R.id.edit_u_pass);
        btn_signin = findViewById(R.id.btn_signin);
        btn_register = findViewById(R.id.btn_register);
        iv_kakaoLogin = findViewById(R.id.iv_kakaoLogin);
        iv_naverLogin = findViewById(R.id.iv_naverLogin);
        iv_appleLogin = findViewById(R.id.iv_appleLogin);

        // SIGN IN 클릭 ( 로그인 )
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자가 입력한 u_id, u_pass
                String u_id = edit_u_id.getText().toString();
                String u_pass = edit_u_pass.getText().toString();

                // 입력된 u_id 또는 u_pass 없으면 리턴
                if(u_id.equals("") || u_pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "모든 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 사용자가 입력한 u_id, u_pass를 userVO에 세팅
                UserVO userVO = new UserVO();
                userVO.setU_id(u_id);
                userVO.setU_pass(u_pass);

                // 로그인 유효성 체크( 서버 통신 )
                Call<UserVO> call = service.and_login(userVO);
                call.enqueue(new Callback<UserVO>() {
                    @Override
                    public void onResponse(Call<UserVO> call, Response<UserVO> response) {
//                        System.out.println("LoginActivity - call : " + response.body());
                        Session.setU_name(LoginActivity.this, response.body().getU_name());  // 유저 이름 세션에 저장
                        Session.setU_code(LoginActivity.this, response.body().getU_code());  // 유저 코드 세션에 저장
                        if(Session.getU_name(LoginActivity.this).length() != 0) {            // 세션에 저장된 유저가 있으면
//                            System.out.println("LoginActivity - call : " + response.body());
                            Intent intent = new Intent(LoginActivity.this, CategoryActivity.class); // 로그인 성공 후 액티비티 이동
                            startActivity(intent);

                            // 액티비티 스택제거
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserVO> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                        System.out.println(".....오류 :: LoginActivity - call : " + t.toString());
                    }
                });
            }
        });

        // create an account 클릭 시 ( 회원가입 )
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 카카오 로그인 클릭 시
        iv_kakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 카카오톡 설치 여부 확인
                // 카카오 설치 되어 있을 시
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, kakaoCallback);
                } else {    // 카카오 설치 되어 있지 않을 시
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, kakaoCallback);
                }
            }
        });
    }

    // 카카오 설치 여부 확인 ( 카카오에서 제공 콜백 객체 이용 )
    Function2<OAuthToken, Throwable, Unit> kakaoCallback = new Function2<OAuthToken, Throwable, Unit>() {
        @Override
        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
            updateKakaoLoginUI();
            return null;
        }
    };

    // 카카오 로그인
    private void updateKakaoLoginUI() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                // 로그인 되어 있을 시
                if(user != null) {
                    Log.d(TAG, "updateKakaoLoginUI - id : " + user.getId());    // u_code 생성에 사용

                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, KakaoRegisterActivity.class);
                    intent.putExtra("u_code", user.getId().toString());
                    startActivity(intent);
                    finish();
                } else {
                    // 로그인 되어 있지 않을 시
                    Log.d(TAG, "로그인이 필요합니다");
                }
                return null;
            }
        });
    }

}