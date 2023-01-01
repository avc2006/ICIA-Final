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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iamport.sdk.data.sdk.IamPortRequest;
import com.iamport.sdk.data.sdk.PG;
import com.iamport.sdk.data.sdk.PayMethod;
import com.iamport.sdk.domain.core.Iamport;
import com.icia.friend.remote.OrderRemoteService;
import com.icia.friend.remote.PostRemoteService;
import com.icia.friend.session.Session;
import com.icia.friend.remote.CartRemoteService;
import com.icia.friend.remote.StoreRemoteService;
import com.icia.friend.store.CategoryActivity;
import com.icia.friend.vo.CartVO;
import com.icia.friend.vo.OrderVO;
import com.icia.friend.vo.PostVO;
import com.icia.friend.vo.StoreVO;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kotlin.Unit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderActivity extends AppCompatActivity {

    Retrofit retrofit, s_retrofit, p_retrofit, o_retrofit;
    CartRemoteService c_service;

    OrderAdapter OrderAdapter = new OrderAdapter();
    RecyclerView list;
    TextView o_price, o_cost, o_total;
    Button pay;

    List<CartVO> CartList = new ArrayList<>();
    StoreVO storeVO;
    String u_code;
    String s_code;
    String menu;
    int price = 0;
    int cost = 0;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Iamport.INSTANCE.init(this);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        c_service = retrofit.create(CartRemoteService.class);

        list = findViewById(R.id.order_list);
        o_price = findViewById(R.id.o_price);
        o_cost = findViewById(R.id.o_cost);
        o_total = findViewById(R.id.o_total);
        pay = findViewById(R.id.pay);

        u_code = Session.getU_code(OrderActivity.this);

        onRestart();

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(OrderAdapter);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 장바구니 목록
        Call<List<CartVO>> list = c_service.list(u_code);
        list.enqueue(new Callback<List<CartVO>>() {
            @Override
            public void onResponse(Call<List<CartVO>> call, Response<List<CartVO>> response) {
                CartList = response.body();
                menu = CartList.get(0).getM_name() + " 외 " + (CartList.size() - 1) + "건";
                OrderAdapter.notifyDataSetChanged();
                s_code = CartList.get(0).getS_code();
                System.out.println("OrderActivity - c_service.list : " + s_code);

                s_retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.193:8088/api/store/").addConverterFactory(GsonConverterFactory.create()).build();
                StoreRemoteService s_service = s_retrofit.create(StoreRemoteService.class);

                Call<StoreVO> read = s_service.read(s_code);
                read.enqueue(new Callback<StoreVO>() {
                    @Override
                    public void onResponse(Call<StoreVO> call, Response<StoreVO> response) {
                        storeVO = response.body();

                        p_retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.193:8088/api/post/").addConverterFactory(GsonConverterFactory.create()).build();
                        PostRemoteService p_service = p_retrofit.create(PostRemoteService.class);

                        Call<PostVO> order_read = p_service.order_read(u_code);
                        order_read.enqueue(new Callback<PostVO>() {
                            @Override
                            public void onResponse(Call<PostVO> call, Response<PostVO> response) {
                                PostVO pvo = response.body();
                                System.out.println("OrderActivity - p_service.order_read : " + pvo.toString());         // 게시글 정보
                                System.out.println("OrderActivity - p_service.order_read : " + pvo.getP_headcount());   // 인원수

                                cost = storeVO.getCost() / pvo.getP_headcount();
                                total = price + cost;
                                System.out.println("OrderActivity - p_service.order_read : " + cost);

                                o_total.setText(String.valueOf(total));
                                o_cost.setText(String.valueOf(cost));
                                System.out.println("OrderActivity - p_service.order_read : " + storeVO.toString());

                                IamPortRequest request = IamPortRequest.builder()
                                        .pg(PG.kcp.makePgRawName(""))                          // PG 사
                                        .pay_method(PayMethod.card.name())                          // 결제수단
                                        .name(menu)                                                 // 주문명
                                        .merchant_uid(storeVO.getS_code() + (new Date()).getTime()) // 주문번호
                                        .amount("100")                                              // 결제금액
                                        .buyer_name(Session.getU_name(OrderActivity.this)).build();

                                pay.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Iamport.INSTANCE.payment("iamport", null, null, request,
                                                iamPortApprove -> {
                                                    // (Optional) CHAI 최종 결제전 콜백 함수.
                                                    return Unit.INSTANCE;
                                                },
                                                iamPortResponse -> {
                                                    // 최종 결제결과 콜백 함수.
                                                    String responseText = iamPortResponse.toString();
                                                    Log.d("IAMPORT_SAMPLE", responseText);
                                                    Toast.makeText(OrderActivity.this, responseText, Toast.LENGTH_LONG).show();

                                                    //order status 변경
                                                    OrderVO ovo = new OrderVO();
                                                    ovo.setO_status("결제완료");
                                                    ovo.setU_code(u_code);
                                                    ovo.setP_code(pvo.getP_code());
                                                    System.out.println("OrderActivity - pay : " + ovo.toString());

                                                    o_retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.193:8088/api/order/").addConverterFactory(GsonConverterFactory.create()).build();
                                                    OrderRemoteService o_service = o_retrofit.create(OrderRemoteService.class);

                                                    Call<Void> update = o_service.update(ovo);
                                                    update.enqueue(new Callback<Void>() {
                                                        @Override
                                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                                            Intent intent = new Intent(OrderActivity.this, CategoryActivity.class);
                                                            startActivity(intent);
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Void> call, Throwable t) {

                                                        }
                                                    });
                                                    return Unit.INSTANCE;
                                                }
                                        );
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<PostVO> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<StoreVO> call, Throwable t) {
                        System.out.println(".....오류 :: OrderActivity - s_service.read : " + t.toString());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<CartVO>> call, Throwable t) {
                System.out.println(".....오류 :: OrderActivity - c_service.list : " + t.toString());
            }
        });
    }

    class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
        @NonNull
        @Override
        public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_order, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
            CartVO vo = CartList.get(position);
            price = price + (vo.getAmount() * vo.getM_price());
            o_price.setText(String.valueOf(price));
            holder.o_name.setText(vo.getM_name());
            holder.order_price.setText(String.valueOf(vo.getM_price()));
            holder.o_amount.setText(String.valueOf(vo.getAmount()));

            final Bitmap[] bitmap = new Bitmap[1];
            String imageUrl = "http://192.168.0.193:8088/api/display?fileName=" + vo.getM_photo();

            if (vo.getM_photo() != null) {
                holder.o_photo.setVisibility(View.VISIBLE);

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
                    holder.o_photo.setImageBitmap(bitmap[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                holder.o_photo.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return CartList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView o_name, order_price, o_amount;
            ImageView o_photo;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                o_name = itemView.findViewById(R.id.o_name);
                order_price = itemView.findViewById(R.id.order_price);
                o_amount = itemView.findViewById(R.id.o_amount);
                o_photo = itemView.findViewById(R.id.o_photo);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Iamport.INSTANCE.close();
    }

}