package com.icia.friend.store;

import static com.icia.friend.remote.StoreRemoteService.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.icia.friend.CartActivity;
import com.icia.friend.LikedActivity;
import com.icia.friend.UserPageActivity;
import com.icia.friend.login.LoginActivity;
import com.icia.friend.session.Session;
import com.icia.friend.R;
import com.icia.friend.remote.StoreRemoteService;
import com.icia.friend.vo.StoreVO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryActivity extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    StoreRemoteService s_service = retrofit.create(StoreRemoteService.class);

    SearchView search;
    Intent s_intent;
    Button c_search;

    String query;
    String u_code;
    List<StoreVO> array = new ArrayList<>();
    ArrayList<String> liked_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        u_code = Session.getU_code(CategoryActivity.this);

        Call<List<StoreVO>> liked = s_service.liked(u_code);
        liked.enqueue(new Callback<List<StoreVO>>() {
            @Override
            public void onResponse(Call<List<StoreVO>> call, Response<List<StoreVO>> response) {
                array = response.body();
                for (int i = 0; i < array.size(); i++) {
                    liked_list.add(array.get(i).getS_code());
                }
                Session.setStringArrayPref(CategoryActivity.this, "liked", liked_list);
            }

            @Override
            public void onFailure(Call<List<StoreVO>> call, Throwable t) {
                System.out.println(".....오류 :: CategoryActivity - 즐겨찾기 목록 : " + t.toString());
            }
        });

        findViewById(R.id.han).setOnClickListener(mclick);
        findViewById(R.id.il).setOnClickListener(mclick);
        findViewById(R.id.joong).setOnClickListener(mclick);
        findViewById(R.id.yang).setOnClickListener(mclick);
        findViewById(R.id.asian).setOnClickListener(mclick);
        findViewById(R.id.boon).setOnClickListener(mclick);
        findViewById(R.id.fast).setOnClickListener(mclick);
        findViewById(R.id.dessert).setOnClickListener(mclick);
        findViewById(R.id.etc).setOnClickListener(mclick);

        findViewById(R.id.menu_shopping).setOnClickListener(Caclik);
        findViewById(R.id.menu_star).setOnClickListener(Caclik);
        findViewById(R.id.menu_user).setOnClickListener(Caclik);

        s_intent = new Intent(CategoryActivity.this, SearchListActivity.class);

        search = findViewById(R.id.search);
        search.isSubmitButtonEnabled();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                s_intent.putExtra("query", s);
                startActivity(s_intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                query = newText;
                return true;
            }
        });

        c_search = findViewById(R.id.c_search);
        c_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_intent.putExtra("query", query);
                startActivity(s_intent);
            }
        });

        /** ? 로그인 세션 유무 비교 후 없으면 로그인으로 이동 처리 어디서 할건지 ? **/
        if (Session.getU_name(CategoryActivity.this).equals("")) {
            Intent loginIntent = new Intent(CategoryActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }

    View.OnClickListener mclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CategoryActivity.this, StoreListActivity.class);
            switch (v.getId()) {
                case R.id.han:
                    intent.putExtra("s_c_code", 1);
                    break;

                case R.id.il:
                    intent.putExtra("s_c_code", 2);
                    break;

                case R.id.joong:
                    intent.putExtra("s_c_code", 3);
                    break;

                case R.id.yang:
                    intent.putExtra("s_c_code", 4);
                    break;

                case R.id.asian:
                    intent.putExtra("s_c_code", 5);
                    break;

                case R.id.boon:
                    intent.putExtra("s_c_code", 6);
                    break;

                case R.id.fast:
                    intent.putExtra("s_c_code", 7);
                    break;

                case R.id.dessert:
                    intent.putExtra("s_c_code", 8);
                    break;

                case R.id.etc:
                    intent.putExtra("s_c_code", 9);
                    break;
            }
            startActivity(intent);
        }
    };

    View.OnClickListener Caclik = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.menu_shopping:
                    intent = new Intent(CategoryActivity.this, CartActivity.class);
                    break;

                case R.id.menu_star:
                    intent = new Intent(CategoryActivity.this, LikedActivity.class);
                    break;

                case R.id.menu_user:
                    intent = new Intent(CategoryActivity.this, UserPageActivity.class);
                    break;
            }
            startActivity(intent);
        }
    };

}