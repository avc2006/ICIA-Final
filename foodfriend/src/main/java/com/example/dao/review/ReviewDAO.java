package com.example.dao.review;

import java.util.HashMap;
import java.util.List;

import com.example.domain.ReviewVO;

public interface ReviewDAO {
	
	public String newCode(String s_code);
	
	public void insert(ReviewVO vo);
	
	public void storeinsert(ReviewVO vo);
	
	public List<ReviewVO> list();
	
	public List<ReviewVO> read(String s_code);

	public List<HashMap<String, Object>> uread(String u_code);

	public List<HashMap<String, Object>> sread(String s_code);
	
	public void reviewupdate(String r_code);
	
	public void photoupdate(String r_code);	// ? »ç¿ë X?
	
	public void update_del();

	public void delete(String u_code);
	
}
