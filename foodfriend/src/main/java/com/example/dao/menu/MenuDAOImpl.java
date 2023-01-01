package com.example.dao.menu;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.domain.MenuVO;

@Repository
public class MenuDAOImpl implements MenuDAO {

	@Autowired
	SqlSession session;
	
	String namespace = "com.example.mapper.MenuMapper";
	

	@Override
	public int newCode(String s_code) {
		return session.selectOne(namespace + ".newCode", s_code);
	}

	@Override
	public void insert(MenuVO menuVO) {
		session.insert(namespace + ".insert", menuVO);
	}

	@Override
	public List<MenuVO> list(String s_code) {
		return session.selectList(namespace + ".list", s_code);
	}

	@Override
	public MenuVO read(String s_code, int m_code) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("s_code", s_code);
		map.put("m_code", m_code);
		
		return session.selectOne(namespace + ".read", map);
	}
	
	@Override
	public void update(MenuVO menuVO) {
		session.update(namespace + ".update", menuVO);
	}

	@Override
	public void delete(String s_code, String m_name) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("s_code", s_code);
		map.put("m_name", m_name);
		
		session.delete(namespace + ".delete", map);
	}

	@Override
	public void allDelete(String s_code) {
		session.delete(namespace + ".allDelete", s_code);
	}
	
}
