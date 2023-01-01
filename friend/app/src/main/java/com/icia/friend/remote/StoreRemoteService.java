package com.icia.friend.remote;

import com.icia.friend.vo.MenuVO;
import com.icia.friend.vo.StoreVO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StoreRemoteService {

    public final static String BASE_URL = "http://192.168.0.193:8088/api/store/";

    // 특정 카테고리의 가게 목록
    @GET("clist/{s_c_code}")
    Call<List<StoreVO>> clist(@Path("s_c_code") int s_c_code);

    // 검색 조건에 일치하는 메장이름과 메뉴명을 가진 가게 목록
    @GET("search")
    Call<List<HashMap<String, Object>>> search(@Query("query") String query);

    // 특정 가게 정보
    @GET("read/{s_code}")
    Call<StoreVO> read(@Path("s_code") String s_code);

    // 특정 유저의 즐겨찾기 매장 목록
    @GET("liked/{u_code}")
    Call<List<StoreVO>> liked(@Path("u_code") String u_code);

}
