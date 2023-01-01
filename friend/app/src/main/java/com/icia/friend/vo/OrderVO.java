package com.icia.friend.vo;

public class OrderVO {

    private int p_code;
    private String u_code;
    private String token;
    private String o_status;

    public int getP_code() {
        return p_code;
    }

    public void setP_code(int p_code) {
        this.p_code = p_code;
    }

    public String getU_code() {
        return u_code;
    }

    public void setU_code(String u_code) {
        this.u_code = u_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getO_status() {
        return o_status;
    }

    public void setO_status(String o_status) {
        this.o_status = o_status;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "p_code=" + p_code +
                ", u_code='" + u_code + '\'' +
                ", token='" + token + '\'' +
                ", o_status='" + o_status + '\'' +
                '}';
    }

}
