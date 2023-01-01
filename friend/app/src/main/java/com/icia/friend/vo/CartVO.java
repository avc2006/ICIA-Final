package com.icia.friend.vo;

public class CartVO {

    private String u_code;
    private String s_code;
    private String m_name;
    private String m_photo;
    private int amount;
    private int m_price;

    public String getM_photo() {
        return m_photo;
    }

    public void setM_photo(String m_photo) {
        this.m_photo = m_photo;
    }

    public int getM_price() {
        return m_price;
    }

    public void setM_price(int m_price) {
        this.m_price = m_price;
    }

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

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CartVO{" +
                "u_code='" + u_code + '\'' +
                ", s_code='" + s_code + '\'' +
                ", m_name='" + m_name + '\'' +
                ", m_photo='" + m_photo + '\'' +
                ", amount=" + amount +
                ", m_price=" + m_price +
                '}';
    }
}
