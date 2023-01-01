package com.example.dao.user;

import java.util.List;

import com.example.domain.UserVO;

public interface UserDAO {
	
	// 일반 사용자 코드 부여
	public String newCode();
	
	// 카카오 회원가입 사용자 코드 부여
	public String newKakaoCode(String number);
	
	// 회원가입
	public void insert(UserVO userVO);
	
	public List<UserVO> list(String column, String query, int page, int num);

	public int total(String column, String query);
	
	public void update(UserVO vo);

	public void updateStatus(String u_code);

	public void delUser(String u_code);

	public void reUser(String u_code);

	public UserVO read(String u_code);

	public void del_user();

	public UserVO loginRead(String u_id);
	
	public int getAge(String birthday);
	
}
