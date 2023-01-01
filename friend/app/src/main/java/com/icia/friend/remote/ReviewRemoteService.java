package com.icia.friend.remote;

import com.icia.friend.vo.ReviewVO;
import com.squareup.okhttp.RequestBody;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ReviewRemoteService {

    public final static String BASE_URL = "http://192.168.0.193:8088/api/review/";

    @GET("sread/{s_code}")
    Call<HashMap<String, Object>> list(@Path("s_code") String s_code);

    @POST("insert")
    Call <Void> insert (@Body ReviewVO vo);

}
