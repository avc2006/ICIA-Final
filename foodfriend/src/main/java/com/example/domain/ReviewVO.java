package com.example.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

// VO ¿Á»Æ¿Œ ø‰∏¡
public class ReviewVO extends UserVO {

	private String r_code;
	private String s_code;
	private String u_code;
	private String r_content;
	private String r_photo;
	private Double r_rating;
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", timezone="Asia/Seoul")
	private Date review_date;
	private String r_reply;

	public String getR_code() {
		return r_code;
	}

	public void setR_code(String r_code) {
		this.r_code = r_code;
	}

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

	public Double getR_rating() {
		return r_rating;
	}

	public void setR_rating(Double r_rating) {
		this.r_rating = r_rating;
	}

	public Date getReview_date() {
		return review_date;
	}

	public void setReview_date(Date review_date) {
		this.review_date = review_date;
	}


	public String getR_reply() {
		return r_reply;
	}

	public void setR_reply(String r_reply) {
		this.r_reply = r_reply;
	}

	@Override
	public String toString() {
		return "ReviewVO [r_code=" + r_code + ", s_code=" + s_code + ", u_code=" + u_code + ", r_content=" + r_content
				+ ", r_photo=" + r_photo + ", r_rating=" + r_rating + ", review_date=" + review_date + ", r_reply=" + r_reply + "]";
	}

}
