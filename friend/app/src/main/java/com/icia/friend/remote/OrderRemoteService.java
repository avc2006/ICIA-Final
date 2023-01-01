package com.icia.friend.remote;

import com.icia.friend.vo.OrderVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderRemoteService {
    public final static String BASE_URL = "http://192.168.0.193:8088/api/order/";

    // 주문상태 변경
    @POST("update")
    Call<Void> update(@Body OrderVO vo);

}
