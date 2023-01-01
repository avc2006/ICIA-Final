package com.example.domain;

public class LikedVO {

	private String s_code;
	private String u_code;

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

	@Override
	public String toString() {
		return "LikedVO [s_code=" + s_code + ", u_code=" + u_code + "]";
	}

}
