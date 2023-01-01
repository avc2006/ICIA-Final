package com.icia.friend.store;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.icia.friend.R;
import com.icia.friend.vo.MenuVO;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Menu_Adapter extends RecyclerView.Adapter<Menu_Adapter.ViewHolder> {

    Context context;

    List<MenuVO> array;

    // 생성자
    public Menu_Adapter(Context context, List<MenuVO> array) {
        this.context = context;
        this.array = array;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuVO vo = array.get(position);
        holder.m_name.setText(vo.getM_name());
        holder.m_price.setText(String.valueOf(vo.getM_price()));

        final Bitmap[] bitmap = new Bitmap[1];
        String imageUrl = "http://192.168.0.193:8088/api/display?fileName=" + vo.getM_photo();

        if (vo.getM_photo() != null) {
            holder.m_photo.setVisibility(View.VISIBLE);
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
                //join() 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다림
                Thread.join();
                holder.m_photo.setImageBitmap(bitmap[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            holder.m_photo.setVisibility(View.GONE);
        }

        holder.m_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("vo", vo);

                Intent intent = new Intent(context, MenuReadActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView m_photo;
        TextView m_price, m_name;
        RelativeLayout m_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            m_item = itemView.findViewById(R.id.m_item);
            m_photo = itemView.findViewById(R.id.m_photo);
            m_price = itemView.findViewById(R.id.m_price);
            m_name = itemView.findViewById(R.id.m_name);
        }
    }

}
