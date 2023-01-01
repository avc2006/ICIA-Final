package com.icia.friend.remote;


import com.icia.friend.vo.MenuVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MenuRemoteService {

    public final static String BASE_URL = "http://192.168.0.193:8088/api/menu/";

    //특정 가게의 메뉴 목록
    @GET("list")
    Call<List<MenuVO>> list(@Query("s_code") String s_code);

}
