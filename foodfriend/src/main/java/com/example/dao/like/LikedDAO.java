package com.example.dao.like;

import java.util.List;

import com.example.domain.LikedVO;
import com.example.domain.StoreVO;

public interface LikedDAO {
	
	public void insert(LikedVO vo);

	public List<StoreVO> list(String u_code);
	
	public List<LikedVO> read(String s_code);
	
	public void delete(LikedVO vo);
	
	public void u_delete();
	
	public void s_delete(String s_code);
	
}