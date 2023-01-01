package com.icia.friend;

import static com.icia.friend.remote.StoreRemoteService.BASE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icia.friend.session.Session;
import com.icia.friend.remote.StoreRemoteService;
import com.icia.friend.store.StoreReadActivity;
import com.icia.friend.vo.StoreVO;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LikedActivity extends AppCompatActivity {

    Retrofit retrofit;
    StoreRemoteService s_service;

    RecyclerView liked_list;
    LikedAdapter LikedAdapter = new LikedAdapter();

    List<StoreVO> array = new ArrayList<>();
    String u_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        s_service = retrofit.create(StoreRemoteService.class);

        u_code = Session.getU_code(LikedActivity.this);

        onRestart();

        liked_list = findViewById(R.id.liked_list);
        liked_list.setLayoutManager(new LinearLayoutManager(this));
        liked_list.setAdapter(LikedAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Call<List<StoreVO>> liked = s_service.liked(u_code);
        liked.enqueue(new Callback<List<StoreVO>>() {
            @Override
            public void onResponse(Call<List<StoreVO>> call, Response<List<StoreVO>> response) {
                array = response.body();
                LikedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<StoreVO>> call, Throwable t) {
                System.out.println(".....오류 :: LikedActivity - liked : " + t.toString());
            }
        });
    }

    class LikedAdapter extends RecyclerView.Adapter<LikedAdapter.ViewHolder> {

        @NonNull
        @Override
        public LikedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_store, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LikedAdapter.ViewHolder holder, int position) {
            StoreVO vo = array.get(position);

            holder.s_location.setText(vo.getS_location());
            holder.s_name.setText(vo.getS_name());
            holder.s_tel.setText(vo.getS_tel());
            holder.w_count.setBackgroundResource(R.drawable.count);

            if (vo.getS_waiting() != 0) {
                holder.w_count.setText(String.valueOf(vo.getS_waiting()));
            }

            final Bitmap[] bitmap = new Bitmap[1];
            String imageUrl = "http://192.168.0.193:8088/api/display?fileName=" + vo.getS_photo();

            if (vo.getS_photo() != null) {
                holder.s_photo.setVisibility(View.VISIBLE);
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
                    //join() 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다림
                    Thread.join();
                    holder.s_photo.setImageBitmap(bitmap[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                holder.s_photo.setVisibility(View.GONE);
            }

            holder.s_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LikedActivity.this, StoreReadActivity.class);
                    intent.putExtra("StoreVO", vo);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return array.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            RelativeLayout s_item;
            TextView s_name, s_location, s_tel, w_count;
            ImageView s_photo;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                s_name = itemView.findViewById(R.id.s_name);
                w_count = itemView.findViewById(R.id.w_count);
                s_location = itemView.findViewById(R.id.s_location);
                s_tel = itemView.findViewById(R.id.s_tel);
                s_photo = itemView.findViewById(R.id.s_photo);
                s_item = itemView.findViewById(R.id.s_item);
            }
        }
    }

}