package com.icia.friend.vo;

// VO 재확인 요망
public class ReviewVO {

    private String s_code;
    private String u_code;
    private String r_content;
    private String r_photo;
    private String r_rating;
    private String review_date; // 서버의 JsonFormat으로 인해 String 타입으로 받음
    private String u_id;
    private String u_reply;

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

    public String getR_content() {
        return r_content;
    }

    public void setR_content(String r_content) {
        this.r_content = r_content;
    }

    public String getR_photo() {
        return r_photo;
    }

    public void setR_photo(String r_photo) {
        this.r_photo = r_photo;
    }

    public String getR_rating() {
        return r_rating;
    }

    public void setR_rating(String r_rating) {
        this.r_rating = r_rating;
    }

    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_reply() {
        return u_reply;
    }

    public void setU_reply(String u_reply) {
        this.u_reply = u_reply;
    }

    @Override
    public String toString() {
        return "ReviewVO{" +
                "s_code='" + s_code + '\'' +
                ", u_code='" + u_code + '\'' +
                ", r_content='" + r_content + '\'' +
                ", r_photo='" + r_photo + '\'' +
                ", r_rating='" + r_rating + '\'' +
                ", review_date='" + review_date + '\'' +
                ", u_id='" + u_id + '\'' +
                ", u_reply='" + u_reply + '\'' +
                '}';
    }

}
