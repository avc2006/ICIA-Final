package com.icia.friend;

import static com.icia.friend.remote.UserRemoteService.BASE_URL;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.icia.friend.remote.UserRemoteService;
import com.icia.friend.session.Session;
import com.icia.friend.vo.UserVO;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserUpdateActivity extends AppCompatActivity {

    Retrofit u_retrofit;
    UserRemoteService u_service;

    EditText user_name, user_id, user_pass, user_address;
    CircleImageView user_photo;
    ImageView change_photo;
    Button user_update, cancle;

    UserVO vo = new UserVO();
    String u_code;
    String strImage = "";   // 이미지의 경로

    // 갤러리에서 사진 선택 후 이곳으로
    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 갤러리에서 선택한 이미지의 경로와 파일명 읽어오기
        Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
        cursor.moveToFirst();

        strImage = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        user_photo.setImageBitmap(BitmapFactory.decodeFile(strImage));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);

        u_retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        u_service = u_retrofit.create(UserRemoteService.class);

        u_code = Session.getU_code(UserUpdateActivity.this);
        user_name = findViewById(R.id.user_name);
        user_id = findViewById(R.id.user_id);
        user_pass = findViewById(R.id.user_pass);
        user_address = findViewById(R.id.user_address);
        user_photo = findViewById(R.id.user_photo);
        cancle = findViewById(R.id.cancle);
        change_photo = findViewById(R.id.change_photo);
        user_update = findViewById(R.id.user_update);

        // 유저 정보 불러오기
        Call<UserVO> call = u_service.read(u_code);
        call.enqueue(new Callback<UserVO>() {
            @Override
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                vo = response.body();

                user_name.setText(vo.getU_name());
                user_id.setText(vo.getU_id());
//                user_pass.setText(vo.getU_pass());    //암호화 비밀번호 출력으로 인한 미사용
                user_address.setText(vo.getU_address());

                final Bitmap[] bitmap = new Bitmap[1];
                String imageUrl = "http://192.168.0.193:8088/api/display?fileName=" + vo.getU_photo();
                System.out.println(vo.getU_photo());
                if (vo.getU_photo() != null) {
                    Thread Thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(imageUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                // HttpURLConnection의 인스턴스가 될 수 있으므로 캐스팅해서 사용한다
                                // conn.setDoInput(true); //Server 통신에서 입력 가능한 상태로 만듦
                                conn.connect();
                                InputStream is = conn.getInputStream();     // inputStream 값 가져오기
                                bitmap[0] = BitmapFactory.decodeStream(is); // Bitmap으로 반환
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    Thread.start();

                    try {
                        // join() 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다림
                        Thread.join();
                        user_photo.setImageResource(0);
                        user_photo.setImageBitmap(bitmap[0]);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    user_photo.setImageResource(R.drawable.ic_person);
                }
            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable t) {
                System.out.println(".....오류 :: UserUpdateActivity - u_service.read : " + t.toString());
            }
        });

        // 취소 버튼 클릭 시
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserUpdateActivity.this, UserPageActivity.class);
                startActivity(intent);
            }
        });

        // 유저 이미지 불러오기
        change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        // 변경 버튼 클릭 시
        user_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vo.setU_address(user_address.getText().toString());
                if (user_pass.getText().toString() != null && !user_pass.getText().toString().equals("")) {
                    vo.setU_pass(user_pass.getText().toString());
                }

                RequestBody u_code = RequestBody.create(MediaType.parse("multipart/form-data"), vo.getU_code());
                RequestBody u_pass = RequestBody.create(MediaType.parse("multipart/form-data"), vo.getU_pass());
                RequestBody u_address = RequestBody.create(MediaType.parse("multipart/form-data"), vo.getU_address());

                MultipartBody.Part u_image;
                if (strImage.equals("")) {
                    u_image = null;
                } else {
                    File file = new File(strImage);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    u_image = MultipartBody.Part.createFormData("u_image", file.getName(), requestBody);
                }

                Call<Void> p_upload = u_service.p_upload(u_code, u_pass, u_address, u_image);
                p_upload.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println("UserUpdateActivity - u_service.p_upload :: 정보 수정 완료");
                        Intent intent = new Intent(UserUpdateActivity.this, UserPageActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println(".....오류 :: UserUpdateActivity - u_service.p_upload : " + t.toString());
                    }
                });
            }
        });
    }   // oncreate

}