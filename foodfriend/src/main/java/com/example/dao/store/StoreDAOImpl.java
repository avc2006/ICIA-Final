package com.example.dao.store;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.domain.StoreVO;

@Repository
public class StoreDAOImpl implements StoreDAO {

	@Autowired
	SqlSession session;
	
	String namespace = "com.example.mapper.StoreMapper";
	

	@Override
	public String newCode() {
		return session.selectOne(namespace + ".newCode");
	}

	@Override
	public void insert(StoreVO storeVO) {
		session.insert(namespace + ".insert", storeVO);
	}

	@Override
	public List<StoreVO> list() {
		return session.selectList(namespace + ".list");
	}
	
	@Override
	public List<StoreVO> weblist(String column, String query, int page, int num) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("column", column);
		map.put("query", query);
		map.put("start", (page - 1) * num);
		map.put("num", num);
		return session.selectList(namespace + ".weblist", map);
	}
	
	@Override
	public int total(String column, String query) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("column", column);
		map.put("query", query);
		return session.selectOne(namespace + ".total", map);
	}
	
	@Override
	public List<StoreVO> clist(int s_c_code) {
		return session.selectList(namespace + ".clist", s_c_code);
	}
	
	@Override
	public List<HashMap<String, Object>> search(String query) {
		return session.selectList(namespace + ".search", query);
	}

	@Override
	public StoreVO read(String s_code) {
		return session.selectOne(namespace + ".read", s_code);
	}
	
	@Override
	public StoreVO storeread(String u_id) {
		return session.selectOne(namespace + ".storeread", u_id);
	}
	
	@Override
	public void update(StoreVO storeVO) {
		session.update(namespace + ".update", storeVO);
	}
	
	@Override
	public StoreVO updateStatus(String s_code) {
		return session.selectOne(namespace + ".updateBtn", s_code);
	}
	
	@Override
	public void update_waiting(String s_code) {
		session.update(namespace + ".update_waiting", s_code);
	}
	
	@Override
	public void s_waiting(String s_code, int count) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("s_code", s_code);
		map.put("count", count);
		session.update(namespace + ".s_waiting", map);
	}

	@Override
	public void delete(String s_code) {
		session.delete(namespace + ".delete", s_code);
	}
	
	@Override
	public void delete(StoreVO vo) {
		session.delete(namespace + ".delete", vo.getS_code());
	}
	
}
