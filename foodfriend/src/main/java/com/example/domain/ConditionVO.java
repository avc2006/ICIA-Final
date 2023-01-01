package com.example.domain;

public class ConditionVO {

	private int c_code;
	private int headcount;
	private String gender;
	private int age;

	public int getC_code() {
		return c_code;
	}

	public void setC_code(int c_code) {
		this.c_code = c_code;
	}

	public int getHeadcount() {
		return headcount;
	}

	public void setHeadcount(int headcount) {
		this.headcount = headcount;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "ConditionVO [c_code=" + c_code + ", headcount=" + headcount + ", gender=" + gender + ", age=" + age
				+ "]";
	}

}
