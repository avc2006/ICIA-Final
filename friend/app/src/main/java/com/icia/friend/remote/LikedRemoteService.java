package com.icia.friend.remote;


import com.icia.friend.vo.LikedVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LikedRemoteService {

    public final String BASE_URL = "http://192.168.0.193:8088/api/liked/";

    //즐겨찾기 추가
    @POST("insert")
    Call<Void> insert(@Body LikedVO vo);

    //즐겨찾기 삭제
    @POST("delete")
    Call<Void> delete(@Body LikedVO vo);

}
