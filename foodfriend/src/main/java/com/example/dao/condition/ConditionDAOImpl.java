package com.example.dao.condition;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.domain.ConditionVO;

@Repository
public class ConditionDAOImpl implements ConditionDAO{
	
	@Autowired
	SqlSession session;
	
	String namespace = "com.example.mapper.ConditionMapper";
	
	@Override
	public void insert(ConditionVO vo) {
		session.insert(namespace + ".insert", vo);
	}

	@Override
	public void delete(int c_code) {
		session.delete(namespace + ".delete", c_code);
	}

}
