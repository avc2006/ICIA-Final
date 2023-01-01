package com.example.dao.reply;

import java.util.HashMap;
import java.util.List;

import com.example.domain.ReplyVO;

public interface ReplyDAO {
	
	public void insert(ReplyVO vo);
	
	public List<HashMap<String, Object>> uread(String r_code);
	
}
