package com.example.dao.report;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.domain.ReportVO;

@Repository
public class ReportDAOImpl implements ReportDAO{
	
	@Autowired
	SqlSession session;
	
	String namespace = "com.example.mapper.ReportMapper";
	
	
	@Override
	public String newCode() {
		return session.selectOne(namespace+ ".newCode");
	}
	
	@Override
	public void insert(ReportVO vo) {
		session.insert(namespace + ".insert", vo);	
	}

	@Override
	public List<ReportVO> list(String column, String query, int page, int num) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("column", column);
		map.put("query", query);
		map.put("start", (page - 1) * num);
		map.put("num", num);
		return session.selectList(namespace + ".list", map);
	}
	
	@Override
	public int total(String column, String query) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("column", column);
		map.put("query", query);
		return session.selectOne(namespace + ".total", map);
	}

	@Override
	public ReportVO read(String r_code) {
		return session.selectOne(namespace + ".read" , r_code);
	}

	@Override
	public void update(ReportVO vo) {
		session.update(namespace + ".update" , vo);	
	}

	@Override
	public void delete(String r_code) {
		session.delete(namespace + ".delete" , r_code);
		
	}
	
	@Override
	public void update_del() {
		session.update(namespace + ".update_del");
	}

}
