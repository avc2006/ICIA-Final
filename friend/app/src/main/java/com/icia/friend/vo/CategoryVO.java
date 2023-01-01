package com.icia.friend.vo;

public class CategoryVO {
    private int c_code;
    private String c_type;

    @Override
    public String toString() {
        return "CategoryVO{" +
                "c_code=" + c_code +
                ", c_type='" + c_type + '\'' +
                '}';
    }

    public int getC_code() {
        return c_code;
    }

    public void setC_code(int c_code) {
        this.c_code = c_code;
    }

    public String getC_type() {
        return c_type;
    }

    public void setC_type(String c_type) {
        this.c_type = c_type;
    }
}
