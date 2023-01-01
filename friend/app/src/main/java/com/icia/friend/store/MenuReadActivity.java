package com.icia.friend.store;

import static com.icia.friend.remote.CartRemoteService.BASE_URL;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.icia.friend.CartActivity;
import com.icia.friend.R;
import com.icia.friend.session.Session;
import com.icia.friend.remote.CartRemoteService;
import com.icia.friend.vo.CartVO;
import com.icia.friend.vo.MenuVO;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuReadActivity extends AppCompatActivity {

    Retrofit retrofit;
    CartRemoteService c_service;

    ImageView menu_read_photo;
    TextView menu_read_name, menu_read_price, number;
    Button increase, decrease, read_cartButton, view_cartButton;

    MenuVO vo;
    int amount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_read);

        Bundle intent = getIntent().getExtras();
        vo = (MenuVO) intent.getSerializable("vo");
        System.out.println("MenuReadActivity - onCreate : " + vo.toString());

        //이미지
        menu_read_photo = findViewById(R.id.menu_read_photo);
        final Bitmap[] bitmap = new Bitmap[1];
        String imageUrl = "http://192.168.0.193:8088/api/display?fileName=" + vo.getM_photo();

        if (vo.getM_photo() != null) {
            menu_read_photo.setVisibility(View.VISIBLE);
            Thread Thread = new Thread() {

                @Override
                public void run() {
                    try {
                        URL url = new URL(imageUrl);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        // HttpURLConnection의 인스턴스가 될 수 있으므로 캐스팅해서 사용한다
                        // conn.setDoInput(true);   //Server 통신에서 입력 가능한 상태로 만듦
                        conn.connect();
                        InputStream is = conn.getInputStream();     //inputStream 값 가져오기
                        bitmap[0] = BitmapFactory.decodeStream(is); // Bitmap으로 반환
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread.start();

            try {
                //join() 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다림
                Thread.join();
                menu_read_photo.setImageBitmap(bitmap[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            menu_read_photo.setVisibility(View.GONE);
        }

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build();
        c_service = retrofit.create(CartRemoteService.class);

        System.out.println("MenuReadActivity - onCreate : " + vo.getM_code());

        menu_read_name = findViewById(R.id.menu_read_name);
        menu_read_name.setText(vo.getM_name());
        menu_read_price = findViewById(R.id.menu_read_price);
        increase = findViewById(R.id.increase);
        decrease = findViewById(R.id.decrease);
        number = findViewById(R.id.number);
        read_cartButton = findViewById(R.id.read_cartButton);
        view_cartButton = findViewById(R.id.view_cartButton);

        menu_read_price.setText(String.valueOf(vo.getM_price()));
        increase.setOnClickListener(mclick);
        decrease.setOnClickListener(mclick);

        read_cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartVO vo2 = new CartVO();
                vo2.setS_code(vo.getS_code());
                vo2.setU_code(Session.getU_code(MenuReadActivity.this));
                vo2.setM_name(vo.getM_name());
                vo2.setAmount(amount);
                vo2.setM_photo(vo.getM_photo());
                vo2.setM_price(vo.getM_price());

                Call<String> getS_code = c_service.getS_code(vo2.getU_code());
                getS_code.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        System.out.println("MenuReadActivity - getS_code : " + response.body());
                        if (response.body() == null || vo2.getS_code().equals(response.body())) {
                            System.out.println("MenuReadActivity - getS_code :: vo2 : " + vo2.toString());

                            Call<Void> insert = c_service.insert(vo2);
                            insert.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Toast.makeText(MenuReadActivity.this, "장바구니에 메뉴를 추가하였습니다.", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    System.out.println(".....오류 :: MenuReadActivity - insert : " + t.toString());
                                }
                            });
                        } else {
                            AlertDialog.Builder box = new AlertDialog.Builder(MenuReadActivity.this);
                            box.setTitle("장바구니에는 같은 가게의 메뉴만 담을 수 있습니다.");
                            box.setMessage("장바구니에 담긴 메뉴를 삭제하시겠습니까?");
                            box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Call<Void> new_insert = c_service.new_insert(vo2);
                                    new_insert.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            Toast.makeText(MenuReadActivity.this, "장바구니에 메뉴를 추가하였습니다.", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            System.out.println(".....오류 :: MenuReadActivity - new_insert : " + t.toString());
                                        }
                                    });
                                }
                            });
                            box.setNegativeButton("아니오", null);
                            box.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println(".....오류 :: MenuReadActivity - getS_code : " + t.toString());
                        Call<Void> insert = c_service.insert(vo2);
                        insert.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(MenuReadActivity.this, "장바구니에 메뉴를 추가하였습니다.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println(".....오류 :: MenuReadActivity - c_service.insert : " + t.toString());
                            }
                        });
                    }
                });
            }
        });

        view_cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuReadActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    View.OnClickListener mclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.decrease:
                    if (amount != 1) {
                        amount--;
                        number.setText(String.valueOf(amount));
                    }
                    break;

                case R.id.increase:
                    amount++;
                    number.setText(String.valueOf(amount));
                    break;
            }
        }
    };

}