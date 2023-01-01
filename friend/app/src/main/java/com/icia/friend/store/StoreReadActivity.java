package com.icia.friend.store;

import static com.icia.friend.remote.MenuRemoteService.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.icia.friend.R;
import com.icia.friend.remote.LikedRemoteService;
import com.icia.friend.remote.MenuRemoteService;
import com.icia.friend.remote.ReviewRemoteService;
import com.icia.friend.session.Session;
import com.icia.friend.store.InfoFragment;
import com.icia.friend.store.StorePagerAdapter;
import com.icia.friend.vo.LikedVO;
import com.icia.friend.vo.MenuVO;
import com.icia.friend.vo.StoreVO;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoreReadActivity extends AppCompatActivity {

    Retrofit m_retrofit, l_retrofit, r_retrofit;
    MenuRemoteService m_service;
    LikedRemoteService l_service;
    ReviewRemoteService r_service;

    ImageView store_photo, liked_boolean;

    StoreVO vo;
    LikedVO lvo = new LikedVO();
    List<MenuVO> array;
    List<HashMap<String, Object>> array2;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_read);

        m_retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        m_service = m_retrofit.create(MenuRemoteService.class);

        l_retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.193:8088/api/liked/").addConverterFactory(GsonConverterFactory.create()).build();
        l_service = l_retrofit.create(LikedRemoteService.class);

        r_retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.193:8088/api/review/").addConverterFactory(GsonConverterFactory.create()).build();
        r_service = r_retrofit.create(ReviewRemoteService.class);

        liked_boolean = findViewById(R.id.liked_boolean);

        Intent intent = getIntent();
        vo = (StoreVO) intent.getSerializableExtra("StoreVO");
        lvo.setS_code(vo.getS_code());
        lvo.setU_code(Session.getU_code(StoreReadActivity.this));
        System.out.println(lvo.toString());

        //즐겨찾기 여부 및 등록, 해제
        list = Session.getStringArrayPref(StoreReadActivity.this, "liked");
        System.out.println(list);

        onRestart();

        //이미지
        store_photo = findViewById(R.id.store_photo);
        final Bitmap[] bitmap = new Bitmap[1];
        String imageUrl = "http://192.168.0.193:8088/api/display?fileName=" + vo.getS_photo();

        if (vo.getS_photo() != null) {
            store_photo.setVisibility(View.VISIBLE);
            Thread Thread = new Thread() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(imageUrl);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        // HttpURLConnection의 인스턴스가 될 수 있으므로 캐스팅해서 사용한다
                        // conn.setDoInput(true);   //Server 통신에서 입력 가능한 상태로 만듦
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        // inputStream 값 가져오기
                        bitmap[0] = BitmapFactory.decodeStream(is);
                        // Bitmap으로 반환
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread.start();

            try {
                // join() 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다림
                Thread.join();
                store_photo.setImageBitmap(bitmap[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            store_photo.setVisibility(View.GONE);
        }

        Call<List<MenuVO>> call = m_service.list(vo.getS_code());
        call.enqueue(new Callback<List<MenuVO>>() {
            @Override
            public void onResponse(Call<List<MenuVO>> call, Response<List<MenuVO>> response) {
                array = response.body();

                Call<HashMap<String, Object>> rcall = r_service.list(vo.getS_code());
                rcall.enqueue(new Callback<HashMap<String, Object>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, Object>> call, Response<HashMap<String, Object>> response) {
                        System.out.println("StoreReadActivity - r_service.list : " + response.body());
                        System.out.println("StoreReadActivity - r_service.list : " + response);
                        HashMap<String, Object> map = response.body();
                        System.out.println("StoreReadActivity - r_service.list :: hashmap : " + map.get("review"));
                        array2 = (List) map.get("review");
                        System.out.println("StoreReadActivity - r_service.list :: array : " + array2.get(0));

                        // 탭 생성
                        TabLayout s_tabs = findViewById(R.id.s_tabs);
                        s_tabs.addTab(s_tabs.newTab().setText("메뉴"));
                        s_tabs.addTab(s_tabs.newTab().setText("정보"));
                        s_tabs.addTab(s_tabs.newTab().setText("리뷰"));
                        s_tabs.setTabGravity(s_tabs.GRAVITY_FILL);

                        //Adapter
                        final ViewPager viewPager = (ViewPager) findViewById(R.id.s_viewpager);
                        final StorePagerAdapter StorePagerAdapter = new StorePagerAdapter(getSupportFragmentManager(), 3, vo, array, array2);
                        viewPager.setAdapter(StorePagerAdapter);

                        //탭 선택 이벤트
                        s_tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
                        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(s_tabs));
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, Object>> call, Throwable t) {
                        System.out.println(".....오류 :: StoreReadActivity - r_service.list : " + t.toString());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<MenuVO>> call, Throwable t) {
                System.out.println(".....오류 :: StoreReadActivity - m_service.list : " + t.toString());
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (list.size() == 0) {
            liked_boolean.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<Void> insert = l_service.insert(lvo);
                    insert.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(StoreReadActivity.this, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                            list.add(vo.getS_code());
                            Session.setStringArrayPref(StoreReadActivity.this, "liked", list);
                            liked_boolean.setImageResource(R.drawable.liked);
                            onRestart();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println("StoreReadActivity - l_service.insert" + t.toString());
                        }
                    });
                }
            });
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(vo.getS_code())) {
                    liked_boolean.setImageResource(R.drawable.liked);
                    liked_boolean.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            System.out.println("StoreReadActivity - onRestart :: 클릭됨");
                            Call<Void> delete = l_service.delete(lvo);
                            delete.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Toast.makeText(StoreReadActivity.this, "즐겨찾기에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    list.remove(vo.getS_code());
                                    Session.setStringArrayPref(StoreReadActivity.this, "liked", list);
                                    liked_boolean.setImageResource(R.drawable.unliked);
                                    onRestart();
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    System.out.println("StoreReadActivity - onRestart : " + t.toString());
                                }
                            });
                        }
                    });

                    break;
                } else {
                    liked_boolean.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Call<Void> insert = l_service.insert(lvo);
                            insert.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Toast.makeText(StoreReadActivity.this, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                                    list.add(vo.getS_code());
                                    Session.setStringArrayPref(StoreReadActivity.this, "liked", list);
                                    liked_boolean.setImageResource(R.drawable.liked);
                                    onRestart();
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    System.out.println("StoreReadActivity - onRestart : " + t.toString());
                                }
                            });
                        }
                    });
                }
            }
        }
    }

}