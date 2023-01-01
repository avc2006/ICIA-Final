package com.example.dao.post;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.domain.PostVO;

@Repository
public class PostDAOImpl implements PostDAO{
	
	@Autowired
	SqlSession session;
	
	String namespace = "com.example.mapper.PostMapper";

	
	@Override
	public int get_p_code() {
		return session.selectOne(namespace + ".get_p_code");
	}
	
	@Override
	public int get_c_code(int p_code) {
		return session.selectOne(namespace + ".get_c_code", p_code);
	}
	
	@Override
	public void insert(PostVO vo) {
		session.insert(namespace + ".insert", vo);		
	}

	@Override
	public List<PostVO> list(String s_code) {
		return session.selectList(namespace + ".list", s_code);
	}
	
	@Override
	public PostVO read(int p_code) {
		return session.selectOne(namespace + ".read", p_code);
	}
	
	@Override
	public void updateHeadcount(int p_code) {
		session.update(namespace + ".updateHeadcount", p_code);
	}

	@Override
	public void delete(int p_code) {
		session.delete(namespace + ".delete", p_code);
	}

	@Override
	public PostVO order_read(int p_code) {
		return session.selectOne(namespace + ".order_read", p_code);
	}
	
}
