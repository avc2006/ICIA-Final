package com.icia.friend.remote;

import com.icia.friend.vo.CartVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartRemoteService {

    public final String BASE_URL = "http://192.168.0.193:8088/api/cart/";

    //카트에 담기
    @POST("insert")
    Call<Void> insert(@Body CartVO vo);

    //가게 코드 받아오기(기존 가게와 같은 가게만 담도록)
    @GET("getS_code/{u_code}")
    Call<String> getS_code(@Path("u_code") String u_code);

    //기존 장바구니 비우고 새로운 가게의 메뉴 담기
    @POST("new_insert")
    Call<Void> new_insert(@Body CartVO vo);

    //특정 유저의 장바구니 목록
    @GET("list/{u_code}")
    Call<List<CartVO>> list(@Path("u_code") String u_code);

    //장바구니에서 특정 상품 삭제
    @POST("delete")
    Call<Void> delete(@Body CartVO vo);

    //특정 상품 수량 수정
    @POST("update")
    Call<Void> update(@Body CartVO vo);

}