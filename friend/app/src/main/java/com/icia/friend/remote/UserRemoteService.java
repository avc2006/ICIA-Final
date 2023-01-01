package com.icia.friend.remote;

import com.icia.friend.vo.UserVO;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserRemoteService {

    public final static String BASE_URL = "http://192.168.0.193:8088/api/user/";

    // 일반 회원가입
    @POST("and_insert")
    Call<Integer> and_insert(@Body UserVO userVO);

    // 카카오 회원가입
    @POST("and_kakao_insert")
    Call<Integer> and_kakao_insert(@Body UserVO userVO);

    // 일반 로그인
    @POST("and_login")
    Call<UserVO> and_login(@Body UserVO userVO);

    // 문자인증
    @GET("sendAuthSMS")
    Call<String> sendAuthSMS(@Query("phoneNumber") String phoneNumber);

    //특정 유저 정보
    @GET("read/{u_code}")
    Call<UserVO> read(@Path("u_code") String u_code);

    //사진 업로드 테스트
    @Multipart
    @POST("update")
    Call<Void> p_upload(@Part("u_code") RequestBody u_code,
                        @Part("u_pass") RequestBody u_pass,
                        @Part("u_address") RequestBody u_address,
                        @Part MultipartBody.Part u_image);

}
