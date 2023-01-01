package com.icia.friend.vo;

import java.util.Date;

public class PostVO extends ConditionVO {
    private int p_code;
    private int c_code;
    private String s_code;
    private String u_code;
    private String p_title;
    private String p_content;
    private String p_date;  // 서버의 JsonFormat으로 인해 String 타입으로 받음
    private int p_headcount;
    private boolean p_status;

    public String getS_code() {
        return s_code;
    }

    public void setS_code(String s_code) {
        this.s_code = s_code;
    }

    public String getU_code() {
        return u_code;
    }

    public void setU_code(String u_code) {
        this.u_code = u_code;
    }

    public int getC_code() {
        return c_code;
    }

    public void setC_code(int c_code) {
        this.c_code = c_code;
    }

    public int getP_code() {
        return p_code;
    }

    public void setP_code(int p_code) {
        this.p_code = p_code;
    }

    public String getP_title() {
        return p_title;
    }

    public void setP_title(String p_title) {
        this.p_title = p_title;
    }

    public String getP_content() {
        return p_content;
    }

    public void setP_content(String p_content) {
        this.p_content = p_content;
    }

    public String getP_date() {
        return p_date;
    }

    public void setP_date(String p_date) {
        this.p_date = p_date;
    }

    public int getP_headcount() {
        return p_headcount;
    }

    public void setP_headcount(int p_headcount) {
        this.p_headcount = p_headcount;
    }

    public boolean isP_status() {
        return p_status;
    }

    public void setP_status(boolean p_status) {
        this.p_status = p_status;
    }

    @Override
    public String toString() {
        return "PostVO{" +
                "s_code='" + s_code + '\'' +
                ", u_code='" + u_code + '\'' +
                ", c_code=" + c_code +
                ", p_code=" + p_code +
                ", p_title='" + p_title + '\'' +
                ", p_content='" + p_content + '\'' +
                ", p_date=" + p_date +
                ", p_headcount=" + p_headcount +
                ", p_status=" + p_status +
                '}';
    }
}
