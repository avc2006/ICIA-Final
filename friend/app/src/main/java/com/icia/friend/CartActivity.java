package com.icia.friend;

import static com.icia.friend.remote.CartRemoteService.BASE_URL;


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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.icia.friend.session.Session;
import com.icia.friend.post.PostListActivity;
import com.icia.friend.post.PostInsertActivity;
import com.icia.friend.remote.CartRemoteService;
import com.icia.friend.remote.StoreRemoteService;
import com.icia.friend.store.StoreReadActivity;
import com.icia.friend.vo.CartVO;
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

public class CartActivity extends AppCompatActivity {

    Retrofit retrofit, retrofit2;
    StoreRemoteService s_service;
    CartRemoteService c_service;

    RecyclerView list;
    TextView s_name, c_total;
    Button cart_addbutton, cart_post, create_button;
    CartAdapter CartAdapter = new CartAdapter();

    StoreVO vo = new StoreVO();
    List<CartVO> CartList = new ArrayList<>();
    String u_code;
    String s_code;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        c_service = retrofit.create(CartRemoteService.class);

        retrofit2 = new Retrofit.Builder().baseUrl("http://192.168.0.193:8088/api/store/").addConverterFactory(GsonConverterFactory.create()).build();
        s_service = retrofit2.create(StoreRemoteService.class);

        s_name = findViewById(R.id.s_name);
        list = findViewById(R.id.cart_list);
        cart_addbutton = findViewById(R.id.cart_addbutton);
        c_total = findViewById(R.id.c_total);
        cart_post = findViewById(R.id.cart_post);
        create_button = findViewById(R.id.create_post);

        u_code = Session.getU_code(CartActivity.this);

        onRestart();

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(CartAdapter);

        //게시글 목록
        cart_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartList.size() == 0) {
                    Toast.makeText(CartActivity.this, "장바구니에 상품을 담아주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(CartActivity.this, PostListActivity.class);
                s_code = CartList.get(0).getS_code();
                intent.putExtra("s_code", s_code);
                total = 0;
                startActivity(intent);
            }
        });

        // 게시글 작성
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartList.size() == 0) {
                    Toast.makeText(CartActivity.this, "장바구니에 상품을 담아주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(CartActivity.this, PostInsertActivity.class);
                s_code = CartList.get(0).getS_code();
                intent.putExtra("s_code", s_code);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        total = 0;

        // 장바구니 목록
        Call<List<CartVO>> list = c_service.list(u_code);
        list.enqueue(new Callback<List<CartVO>>() {
            @Override
            public void onResponse(Call<List<CartVO>> call, Response<List<CartVO>> response) {
                CartList = response.body();
                if(CartList.size() != 0) {
                    s_code = CartList.get(0).getS_code();

                    Call<StoreVO> read = s_service.read(s_code);
                    read.enqueue(new Callback<StoreVO>() {
                        @Override
                        public void onResponse(Call<StoreVO> call, Response<StoreVO> response) {
                            vo = response.body();
                            s_name.setText(vo.getS_name());
                        }

                        @Override
                        public void onFailure(Call<StoreVO> call, Throwable t) {
                            System.out.println(".....오류 :: CartActivity - s_service.read : " + t.toString());
                        }
                    });
                }

                // 총액
                if (CartList.size() == 0) {
                    c_total.setText("0원");
                }
                CartAdapter.notifyDataSetChanged();

                // 더 담으러 가기
                if (CartList.size() == 0) {
                    cart_addbutton.setVisibility(View.GONE);
                } else {
                    s_code = CartList.get(0).getS_code();
                    cart_addbutton.setVisibility(View.VISIBLE);
                }

                cart_addbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CartActivity.this, StoreReadActivity.class);

                        Call<StoreVO> read = s_service.read(s_code);
                        read.enqueue(new Callback<StoreVO>() {
                            @Override
                            public void onResponse(Call<StoreVO> call, Response<StoreVO> response) {
                                StoreVO vo = response.body();
                                intent.putExtra("StoreVO", vo);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<StoreVO> call, Throwable t) {
                                System.out.println(".....오류 :: CartActivity - read : " + t.toString());
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<List<CartVO>> call, Throwable t) {
                System.out.println(".....오류 :: CartActivity - list : " + t.toString());
            }
        });
    }

    class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
        @NonNull
        @Override
        public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_cart, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
            CartVO vo = CartList.get(position);
            total = total + (vo.getAmount() * vo.getM_price());
            c_total.setText(String.valueOf(total) + "원");
            holder.c_name.setText(vo.getM_name());
            holder.c_price.setText(String.valueOf(vo.getM_price()));
            holder.c_amount.setText(String.valueOf(vo.getAmount()));

            if (vo.getAmount() == 1) {
                holder.c_decrease.setVisibility(View.GONE);
            } else {
                holder.c_decrease.setVisibility(View.VISIBLE);
            }
            final Bitmap[] bitmap = new Bitmap[1];
            String imageUrl = "http://192.168.0.193:8088/api/display?fileName=" + vo.getM_photo();

            if (vo.getM_photo() != null) {
                holder.c_photo.setVisibility(View.VISIBLE);

                Thread Thread = new Thread() {

                    @Override
                    public void run() {
                        try {
                            URL url = new URL(imageUrl);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                            // HttpURLConnection의 인스턴스가 될 수 있으므로 캐스팅해서 사용한다
                            // conn.setDoInput(true);   //Server 통신에서 입력 가능한 상태로 만듦
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
                    holder.c_photo.setImageBitmap(bitmap[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                holder.c_photo.setVisibility(View.GONE);
            }

            // 메뉴 삭제
            holder.c_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<Void> delete = c_service.delete(vo);
                    delete.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(CartActivity.this, "장바구니에서 메뉴를 삭제하였습니다.", Toast.LENGTH_SHORT).show();
                            onRestart();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println(".....오류 :: CartActivity - delete : " + t.toString());
                        }
                    });
                }
            });

            // 수량 변경
            holder.c_increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    holder.c_amount.setText(String.valueOf(vo.getAmount()+1));
                    vo.setAmount(vo.getAmount() + 1);
                    Call<Void> update = c_service.update(vo);
                    update.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            onRestart();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println(".....오류 :: CartActivity - update(증가) : " + t.toString());
                        }
                    });
                }
            });

            if (vo.getAmount() != 1) {
                holder.c_decrease.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vo.setAmount(vo.getAmount() - 1);
                        Call<Void> update = c_service.update(vo);
                        update.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                onRestart();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println(".....오류 :: CartActivity - update(감소) : " + t.toString());
                            }
                        });
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return CartList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            Button c_increase, c_decrease;
            TextView c_name, c_price, c_amount;
            ImageView c_photo, c_delete;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                c_name = itemView.findViewById(R.id.c_name);
                c_price = itemView.findViewById(R.id.c_price);
                c_amount = itemView.findViewById(R.id.c_amount);
                c_photo = itemView.findViewById(R.id.c_photo);
                c_delete = itemView.findViewById(R.id.c_delete);
                c_increase = itemView.findViewById(R.id.c_increase);
                c_decrease = itemView.findViewById(R.id.c_decrease);
            }
        }
    }

}