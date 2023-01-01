package com.icia.friend.vo;

public class LikedVO {

    private String u_code;
    private String s_code;

    public String getU_code() {
        return u_code;
    }

    public void setU_code(String u_code) {
        this.u_code = u_code;
    }

    public String getS_code() {
        return s_code;
    }

    public void setS_code(String s_code) {
        this.s_code = s_code;
    }

    @Override
    public String toString() {
        return "LikedVO{" +
                "u_code='" + u_code + '\'' +
                ", s_code='" + s_code + '\'' +
                '}';
    }

}
