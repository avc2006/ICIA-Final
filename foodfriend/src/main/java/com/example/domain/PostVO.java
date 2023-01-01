package com.example.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PostVO extends ConditionVO {

	private int p_code;
	private int c_code;
	private String s_code;
	private String u_code;
	private String p_title;
	private String p_content;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private Date p_date;
	private int p_headcount;
	private boolean p_status;

	public int getP_code() {
		return p_code;
	}

	public void setP_code(int p_code) {
		this.p_code = p_code;
	}

	public int getC_code() {
		return c_code;
	}

	public void setC_code(int c_code) {
		this.c_code = c_code;
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

	public Date getP_date() {
		return p_date;
	}

	public void setP_date(Date p_date) {
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
		return "PostVO [p_code=" + p_code + ", c_code=" + c_code + ", s_code=" + s_code + ", u_code=" + u_code
				+ ", p_title=" + p_title + ", p_content=" + p_content + ", p_date=" + p_date + ", p_headcount="
				+ p_headcount + ", p_status=" + p_status + "]";
	}

}
