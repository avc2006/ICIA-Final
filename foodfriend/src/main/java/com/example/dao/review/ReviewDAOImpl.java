package com.example.dao.review;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.domain.ReviewVO;

@Repository
public class ReviewDAOImpl implements ReviewDAO {

	@Autowired
	SqlSession session;
	
	String namespace = "com.example.mapper.ReviewMapper";
	
	
	@Override
	public String newCode(String s_code) {
		return session.selectOne(namespace + ".newCode", s_code);
	}

	@Override
	public void insert(ReviewVO vo) {
		session.insert(namespace + ".insert", vo);
	}
	
	@Override
	public void storeinsert(ReviewVO vo) {
		session.insert(namespace + ".storeinsert", vo);
	}
	
	@Override
	public List<ReviewVO> list() {
		return session.selectList(namespace + ".list");
	}
	
	@Override
	public List<ReviewVO> read(String s_code) {
		return session.selectList(namespace + ".read", s_code);
	}

	@Override
	public List<HashMap<String, Object>> uread(String u_code) {
		return session.selectList(namespace + ".uread", u_code);
	}

	@Override
	public List<HashMap<String, Object>> sread(String s_code) {
		return session.selectList(namespace + ".sread", s_code);
	}
	
	@Override
	public void reviewupdate(String r_code) {
		session.update(namespace + ".reviewupdate", r_code);
	}
	
	// ? »ç¿ë X?
	@Override
	public void photoupdate(String r_code) {
		session.update(namespace+".photoupdate", r_code);
	}

	@Override
	public void update_del() {
		session.update(namespace + ".update_del");
	}

	@Override
	public void delete(String u_code) {
		session.delete(namespace + ".delete", u_code);
	}
	
}
