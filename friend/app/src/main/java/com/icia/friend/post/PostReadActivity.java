package com.icia.friend.post;

import static com.icia.friend.remote.PostRemoteService.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.icia.friend.R;
import com.icia.friend.chat.ChatActivity;
import com.icia.friend.chat.G;
import com.icia.friend.chat.ReadActivity;
import com.icia.friend.login.LoginActivity;
import com.icia.friend.login.RegisterActivity;
import com.icia.friend.remote.PostRemoteService;
import com.icia.friend.remote.StoreRemoteService;
import com.icia.friend.session.Session;
import com.icia.friend.vo.PostVO;
import com.icia.friend.vo.StoreVO;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostReadActivity extends AppCompatActivity {

    /** s_name: 가게 이름 s_code 받아서 불러와야함 **/
    /** btn_join 클릭 시 headcount 증가 **/
    /**
     * p_headcount (post) = headcount (condition) -> 알림
     **/

    Retrofit retrofit;
    PostRemoteService service;
    StoreRemoteService s_service;

    TextView text_s_name, text_p_title, text_p_content, text_age;
    RadioGroup group_gender;
    RadioButton radio_male, radio_female, radio_uni;
    Button btn_chat, btn_join;
    Uri imgUri;                  // 선택한 프로필 이미지 경로 Uri

    String s_code;
    int p_code;
    boolean isFirst = true;      // 앱 처음 실행
    boolean isChanged = false;   // 유저 정보 변경

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_read);

        // 백엔드 연결
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(PostRemoteService.class);

        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.193:8088/api/store/").addConverterFactory(GsonConverterFactory.create()).build();
        s_service = retrofit.create(StoreRemoteService.class);

        // layout 연결
        text_s_name = findViewById(R.id.text_s_name);
        text_p_title = findViewById(R.id.text_p_title);
        text_p_content = findViewById(R.id.text_p_content);
        text_age = findViewById(R.id.age);
        group_gender = findViewById(R.id.group_gender);
        radio_male = findViewById(R.id.radio_male);
        radio_female = findViewById(R.id.radio_female);
        radio_uni = findViewById(R.id.radio_uni);
        btn_chat = findViewById(R.id.btn_chat);
        btn_join = findViewById(R.id.btn_join);

        Intent intent = getIntent();
        p_code = intent.getIntExtra("p_code", 0);

        Call<PostVO> call = service.read(p_code);
        call.enqueue(new Callback<PostVO>() {
            @Override
            public void onResponse(Call<PostVO> call, Response<PostVO> response) {
                System.out.println("PostReadActivity - service.read :: response.body() : " + response.body());
                /**
                 * 가게 페이지에서 게시글 페이지로 이동하는 거라면
                 * s_code로 s_name 정보 가져오기 작업이 꼭 필요한지
                 * **/

                // response 데이터 가져오기
                s_code = response.body().getS_code();
                String p_title = response.body().getP_title();
                String p_content = response.body().getP_content();
                String age = String.valueOf(response.body().getAge());
                String p_headcount = String.valueOf(response.body().getP_headcount());
                String headcount = String.valueOf(response.body().getHeadcount());
                String gender = response.body().getGender();

                Call<StoreVO> storeRead = s_service.read(s_code);
                storeRead.enqueue(new Callback<StoreVO>() {
                    @Override
                    public void onResponse(Call<StoreVO> call, Response<StoreVO> response) {
                        // response 데이터 값으로 설정
                        text_s_name.setText(response.body().getS_name());
                        text_p_title.setText(p_title);
                        text_p_content.setText(p_content);
                        text_age.setText(age);
                        if(gender.equals("남자")) {
                            radio_male.setChecked(true);
                        } else if(gender.equals("여자")) {
                            radio_female.setChecked(true);
                        } else {
                            radio_uni.setChecked(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<StoreVO> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<PostVO> call, Throwable t) {
                System.out.println(".....오류 :: PostReadActivity - service.read : " + t.toString());
            }
        });

        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(PostReadActivity.this, ChatActivity.class);
                startActivity(intent);
                finish();

//                // 유저 정보 바뀌지 않은 상태
//                if(!isChanged && !isFirst){
//                    // ChatActivity로 이동
//                    Intent intent = new Intent(PostReadActivity.this, ChatActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    // save작업
//                    saveData();
//                }
            }
        });

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostVO postVO = new PostVO();
                postVO.setP_code(p_code);
                postVO.setU_code(Session.getU_code(PostReadActivity.this));
                System.out.println("PostReadActivity - u_code : " + postVO.getU_code());

                Call<Integer> call = service.updateHeadcount(postVO);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        // 참여 성공 시
                        if (response.body() == 1) {
                            Toast.makeText(getApplicationContext(), "참여 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PostReadActivity.this, PostListActivity.class);  // 게시글 목록 액티비티로 이동
                            intent.putExtra("s_code", s_code);
                            startActivity(intent);
                        } else if(response.body() == 2) {   // 마지막 참가자 참여 성공 시
                            Toast.makeText(getApplicationContext(), "참여 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PostReadActivity.this, PostListActivity.class);  // 게시글 목록 액티비티로 이동
                            intent.putExtra("s_code", s_code);
                            startActivity(intent);

                            // 알림 보내기
                            Call<ResponseBody> send = service.send(p_code);
                            send.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    System.out.println("PostReadActivity - service.send : " + response.body());
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    System.out.println(".....오류 :: PostReadActivity - service.send : " + t.toString());
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "참여 실패", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        System.out.println(".....오류 :: PostReadActivity - service.updateHeadcount : " + t.toString());
                    }
                });
           }
        });
    }

    void saveData(){
        // EditText의 닉네임 가져오기 [전역변수에]
        G.nickName = Session.getU_name(this);

        /** 이미지 관련 ( 추가 작업 요망 ) **/
//         사용자 이미지 있으면 본인 이미지로
        if(imgUri == null) return;

        // Firebase storage에 이미지 저장하기 위해 파일명 만들기(날짜를 기반으로)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyMMddhhmmss"); //20191024111224
        String fileName= sdf.format(new Date())+".png";

        //Firebase storage에 저장하기
        FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();
        final StorageReference imgRef= firebaseStorage.getReference("profileImages/"+fileName);

        // 파일 업로드
        UploadTask uploadTask=imgRef.putFile(imgUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // 이미지 업로드가 성공되었으므로...
                // 곧바로 firebase storage의 이미지 파일 다운로드 URL을 얻어오기
                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // 파라미터로 firebase의 저장소에 저장되어 있는
                        // 이미지에 대한 다운로드 주소(URL)을 문자열로 얻어오기
                        G.profileUrl= uri.toString();
                        Toast.makeText(PostReadActivity.this, "프로필 저장 완료", Toast.LENGTH_SHORT).show();

                        // 1. Firebase Database에 nickName, profileUrl을 저장
                        // firebase DB관리자 객체 소환
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        // 'profiles'라는 이름의 자식 노드 참조 객체 얻어오기
                        DatabaseReference profileRef= firebaseDatabase.getReference("profiles");

//                        // 닉네임을 key 식별자로 하고 프로필 이미지의 주소를 값으로 저장
//                        profileRef.child(G.nickName).setValue(G.profileUrl);
//
                        // 2. 내 phone에 nickName, profileUrl을 저장
                        SharedPreferences preferences= getSharedPreferences("account",MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();

                        editor.putString("nickName",G.nickName);
                        editor.putString("profileUrl", G.profileUrl);

                        editor.commit();
                        //저장이 완료되었으니 ChatActivity로 전환
                        Intent intent=new Intent(PostReadActivity.this, ChatActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
            }
        });
    }

}