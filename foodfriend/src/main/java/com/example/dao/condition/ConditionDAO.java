package com.example.dao.condition;

import com.example.domain.ConditionVO;

public interface ConditionDAO {
	
	public void insert(ConditionVO vo);
	
	public void delete(int c_code);

}
