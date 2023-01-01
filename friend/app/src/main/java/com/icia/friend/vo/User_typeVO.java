package com.icia.friend.vo;

public class User_typeVO {

    private int u_t_code;
    private String u_type;

    public int getU_t_code() {
        return u_t_code;
    }

    public void setU_t_code(int u_t_code) {
        this.u_t_code = u_t_code;
    }

    public String getU_type() {
        return u_type;
    }

    public void setU_type(String u_type) {
        this.u_type = u_type;
    }

    @Override
    public String toString() {
        return "User_typeVO{" +
                "u_t_code='" + u_t_code + '\'' +
                ", u_type='" + u_type + '\'' +
                '}';
    }

}
