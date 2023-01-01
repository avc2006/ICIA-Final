package com.example.dao.like;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.domain.LikedVO;
import com.example.domain.StoreVO;

@Repository

public class LikedDAOImpl implements LikedDAO {
	
	@Autowired
	SqlSession session;
	
	String namespace = "com.example.mapper.LikedMapper";
	
	
	@Override
	public void insert(LikedVO vo) {
		session.insert(namespace + ".insert", vo);
	}
	
	@Override
	public List<StoreVO> list(String u_code) {
		return session.selectList(namespace + ".list", u_code);
	}

	@Override
	public List<LikedVO> read(String s_code) {
		return session.selectList(namespace + ".read", s_code);
	}
	

	@Override
	public void delete(LikedVO vo) {
		session.delete(namespace + ".delete", vo);
	}

	@Override
	public void u_delete() {
		session.delete(namespace + ".u_delete");
	}
	
	@Override
	public void s_delete(String s_code) {
		session.delete(namespace + ".s_delete", s_code);
	}
	
}
