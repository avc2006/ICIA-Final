package com.icia.friend.remote;

import com.icia.friend.vo.ReportVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReportRemoteService {

    public final static String BASE_URL = "http://192.168.0.193:8088/api/report/";

    @POST("insert")
    Call<Void> insert(@Body ReportVO vo);

}
