package com.icia.friend.review;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.icia.friend.R;
import com.icia.friend.vo.MenuVO;
import com.icia.friend.vo.ReviewVO;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Review_Adapter extends RecyclerView.Adapter<Review_Adapter.ViewHolder> {

    Context context;

    List<HashMap<String, Object>> array2;

    // 생성자
    public Review_Adapter(Context context, List<HashMap<String, Object>> array2) {
        this.context = context;
        this.array2 = array2;
    }

    @Override
    public Review_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new Review_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map<String, Object> map = array2.get(position);
        System.out.println("Review_Adapter - onBindViewHolder : " + map);
        holder.r_content.setText(map.get("r_content").toString());
        holder.u_id.setText(map.get("u_id").toString());
        holder.r_rating.setRating(Float.parseFloat(map.get("r_rating").toString()));
        holder.review_date.setText(map.get("review_date").toString());

        final Bitmap[] bitmap = new Bitmap[1];
        String imageUrl = "http://192.168.0.193:8088/api/display?fileName=" + map.get("r_photo");

        if (!(map.get("r_photo") == null)) {
            holder.r_photo.setVisibility(View.VISIBLE);
            Thread Thread = new Thread() {

                @Override
                public void run() {
                    try {
                        URL url = new URL(imageUrl);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        // HttpURLConnection의 인스턴스가 될 수 있으므로 캐스팅해서 사용한다
                        // conn.setDoInput(true);   // Server 통신에서 입력 가능한 상태로 만듦
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
                holder.r_photo.setImageBitmap(bitmap[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            holder.r_photo.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return array2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout r_item;
        ImageView r_photo;
        TextView r_content, u_id, review_date;
        RatingBar r_rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            r_item = itemView.findViewById(R.id.r_item);
            review_date = itemView.findViewById(R.id.review_date);
            r_photo = itemView.findViewById(R.id.r_photo);
            r_content = itemView.findViewById(R.id.r_content);
            r_rating = itemView.findViewById(R.id.r_rating);
            u_id = itemView.findViewById(R.id.u_id);
        }
    }

}
