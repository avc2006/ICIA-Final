package com.example.dao.user;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.domain.UserVO;

@Repository
public class UserDAOImpl implements UserDAO{
	
	@Autowired
	SqlSession session;
	
	String namespace = "com.example.mapper.UserMapper";
	

	// 일반 회원가입 사용자 코드 부여
	@Override
	public String newCode() {
		return session.selectOne(namespace + ".newCode");
	}
	
	// 카카오 회원가입 사용자 코드 부여
	@Override
	public String newKakaoCode(String number) {
		return session.selectOne(namespace + ".newKakaoCode", number);
	}

	// 회원가입
	@Override
	public void insert(UserVO userVO) {
		session.insert(namespace + ".insert", userVO);
	}
	
	@Override
	public List<UserVO> list(String column, String query, int page, int num) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("column", column);
		map.put("query", query);
		map.put("start", (page - 1) * num);
		map.put("num", num);
		return session.selectList(namespace + ".list", map);
	}
	
	@Override
	public UserVO read(String u_code) {
		return session.selectOne(namespace + ".read" , u_code);
	}
	
	@Override
	public UserVO loginRead(String u_id) {
		return session.selectOne(namespace + ".loginRead", u_id);
	}

	@Override
	public int total(String column, String query) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("column", column);
		map.put("query", query);
		return session.selectOne(namespace + ".total", map);
	}
	
	@Override
	public void update(UserVO vo) {
		session.update(namespace + ".update", vo);
	}

	@Override
	public void updateStatus(String u_code) {
		session.update(namespace + ".updateStatus", u_code);
	}

	@Override
	public void delUser(String u_code) {
		session.insert(namespace + ".delUser", u_code);
	}

	@Override
	public void reUser(String u_code) {
		session.delete(namespace + ".reUser", u_code);		
	}
	
	@Override
	public void del_user() {
		session.delete(namespace + ".del_user");
	}

	@Override
	public int getAge(String birthday) {
		return session.selectOne(namespace + ".getAge", birthday);
	}
	
}
