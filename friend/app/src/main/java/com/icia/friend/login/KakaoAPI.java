package com.icia.friend.login;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import com.kakao.sdk.common.KakaoSdk;

import java.security.MessageDigest;

public class KakaoAPI extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 카카오 SDK 초기화
        KakaoSdk.init(this, "430aa7d07409704345a7ee1c6c4570c0");

//        Log.d("getKeyHash", "" + getKeyHash(KakaoAPI.this));    // 카카오 로그인 키 해시
    }

    // 카카오 로그인 키 해시
    public static String getKeyHash(final Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            if (packageInfo == null) return null;

            for (Signature signature : packageInfo.signatures) {
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                    messageDigest.update(signature.toByteArray());
                    return android.util.Base64.encodeToString(messageDigest.digest(), android.util.Base64.NO_WRAP);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
