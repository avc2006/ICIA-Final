package com.example.service.user;

public interface UserService {
	
	public void delUser(String u_code);

	public void reUser(String u_code);

	public void del_user();
	
	// 문자 인증
	public void authPhoneNumber(String phoneNumber, String authNum);
	
}
