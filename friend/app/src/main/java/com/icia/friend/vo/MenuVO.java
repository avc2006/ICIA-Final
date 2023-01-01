package com.icia.friend.vo;

import java.io.Serializable;

public class MenuVO implements Serializable {
    private int m_code;
    private String s_code;
    private String m_name;
    private int m_price;
    private String m_photo;

    @Override
    public String toString() {
        return "MenuVO{" +
                "m_code=" + m_code +
                ", s_code='" + s_code + '\'' +
                ", m_name='" + m_name + '\'' +
                ", m_price=" + m_price +
                ", m_photo='" + m_photo + '\'' +
                '}';
    }

    public int getM_code() {
        return m_code;
    }

    public void setM_code(int m_code) {
        this.m_code = m_code;
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

    public int getM_price() {
        return m_price;
    }

    public void setM_price(int m_price) {
        this.m_price = m_price;
    }

    public String getM_photo() {
        return m_photo;
    }

    public void setM_photo(String m_photo) {
        this.m_photo = m_photo;
    }
}
