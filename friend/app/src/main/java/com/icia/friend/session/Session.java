package com.icia.friend.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Session {

    static final String username = "u_name";
    static final String userCode = "u_code";
    static final String userId = "u_id";
    static final String Token = "token";

    static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    // u_name
    public static void setU_name(Context context, String u_name) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(username, u_name);
        editor.commit();
    }

    public static String getU_name(Context context) {
        return getSharedPreferences(context).getString(username, "");
    }

    // u_code
    public static void setU_code(Context context, String u_code) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(userCode, u_code);
        editor.commit();
    }

    public static String getU_code(Context context) {
        return getSharedPreferences(context).getString(userCode, "");
    }

    // u_id
    public static void setU_id(Context context, String u_code) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(userId, u_code);
        editor.commit();
    }

    public static String getU_id(Context context) {
        return getSharedPreferences(context).getString(userId, "");
    }

    // 로그아웃
    public static void clearUserName(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.commit();
    }

    // 즐겨찾기
    public static void setStringArrayPref(Context context, String key, ArrayList<String> values) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        JSONArray a = new JSONArray();

        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }

        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();
    }

    public static ArrayList<String> getStringArrayPref(Context context, String key) {
        String json = getSharedPreferences(context).getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    // token
    public static void setToken(Context context, String token) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(Token, token);
        editor.commit();
    }

    public static String getToken(Context context) {
        return getSharedPreferences(context).getString(Token, "");
    }

}
