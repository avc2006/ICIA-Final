package com.example.dao.cart;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.domain.CartVO;

@Repository
public class CartDAOImpl implements CartDAO{
	
	@Autowired
	SqlSession session;
	
	String namespace = "com.example.mapper.CartMapper";
	
	@Override
	public void insert(CartVO vo) {
//		System.out.println("CartDAOImpl - insert : " + vo.toString());
		session.insert(namespace + ".insert", vo);
	}
	
	@Override
	public List<CartVO> list(String u_code) {
		return session.selectList(namespace + ".list", u_code);
	}

	@Override
	public CartVO read(CartVO vo) {
//		System.out.println("CartDAOImpl - read" + vo.toString());
		return session.selectOne(namespace + ".read", vo);
	}
	
	@Override
	public String getS_code(String u_code) {
		return session.selectOne(namespace + ".getS_code", u_code);
	}

	@Override
	public void update(CartVO vo) {
		session.update(namespace + ".update", vo);
	}

	@Override
	public void delete(CartVO vo) {
		session.delete(namespace + ".delete", vo);
	}

	@Override
	public void allDelete(String u_code) {
		session.delete(namespace + ".allDelete", u_code);
	}

	@Override
	public List<CartVO> orderlist(String p_code) {
		return session.selectList(namespace + ".orderlist", p_code);
	}
}
