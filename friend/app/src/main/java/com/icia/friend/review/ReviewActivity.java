package com.icia.friend.review;

import static com.icia.friend.remote.ReviewRemoteService.BASE_URL;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.icia.friend.MainActivity;
import com.icia.friend.R;
import com.icia.friend.remote.ReviewRemoteService;
import com.icia.friend.session.Session;
import com.icia.friend.vo.ReviewVO;
import com.icia.friend.vo.StoreVO;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//리뷰작성
public class ReviewActivity extends AppCompatActivity {

    Retrofit retrofit;
    ReviewRemoteService service;

    TextView u_id;
    RatingBar reviewRating;
    EditText reviewEdit;
    ImageView reviewImageview;
    Button cancelButton, okButton;

    StoreVO vo = new StoreVO();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(ReviewRemoteService.class);

        u_id = findViewById(R.id.u_id);
        reviewRating = findViewById(R.id.reviewRating);
        reviewEdit = findViewById(R.id.reviewEdit);
        reviewImageview = findViewById(R.id.reviewImageview);
        cancelButton = findViewById(R.id.cancelButton);
        okButton = findViewById(R.id.okButton);

        u_id.setText(Session.getU_id(ReviewActivity.this));

        Intent intent = getIntent();
        StoreVO vo = (StoreVO) intent.getSerializableExtra("StoreVO");
        System.out.println("ReviewActivity - onCreate : " + vo);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String r_content = reviewEdit.getText().toString();
                String r_rating = String.valueOf(reviewRating.getRating());
                String u_code = Session.getU_code(ReviewActivity.this);
                String s_code = vo.getS_code();
                String r_photo = "";

                System.out.println("ReviewActivity - okButton :: s_code : " + s_code);
                System.out.println("ReviewActivity - okButton :: r_content : " + r_content);
                System.out.println("ReviewActivity - okButton :: r_rating : " + r_rating);
                System.out.println("ReviewActivity - okButton :: u_id : " + u_id);

                if (r_content.equals("") || r_rating.equals("")) {
                    Toast.makeText(ReviewActivity.this, "빈칸없이 작성해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ReviewVO reviewVO = new ReviewVO();
                reviewVO.setR_content(r_content);
                reviewVO.setR_rating(r_rating);
                reviewVO.setU_code(u_code);
                reviewVO.setS_code(s_code);
                reviewVO.setR_photo(r_photo);
                System.out.println("ReviewActivity - okButton : " + reviewVO.toString());

                AlertDialog.Builder box = new AlertDialog.Builder(ReviewActivity.this);
                box.setTitle("질의");
                box.setMessage("리뷰를 등록하시겠습니까?");
                box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Call<Void> call = service.insert(reviewVO);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(getApplicationContext(), "등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ReviewActivity.this, MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "리뷰등록에 실패하셨습니다.", Toast.LENGTH_SHORT).show();    // 리뷰 등록 실패 시
                                System.out.println(".....오류 :: RegisterActivity - okButton : " + t.toString());
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