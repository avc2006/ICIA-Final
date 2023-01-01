package com.icia.friend.remote;


import com.icia.friend.vo.PostVO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface PostRemoteService {

    public final static String BASE_URL = "http://192.168.0.193:8088/api/post/";

    // 특정 가게의 게시글 목록
    @GET("list/{s_code}")
    Call<List<PostVO>> list(@Path("s_code") String s_code);

    // 게시글 등록하기
    @POST("insert")
    Call<Void> insert(@Body PostVO vo);

    // 특정 게시글 정보
    @GET("read/{p_code}")
    Call<PostVO> read(@Path("p_code") int p_code);

    // order에서 사용할 특정 게시글 정보
    @GET("order_read/{u_code}")
    Call<PostVO> order_read(@Path("u_code") String u_code);

    // 게시글 참가 인원수 증가
    @POST("updateHeadcount")
    Call<Integer> updateHeadcount(@Body PostVO postVO);

    // 알림 전송
    @GET("send/{p_code}")
    Call<ResponseBody> send(@Path("p_code") int p_code);

}

