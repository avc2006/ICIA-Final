package com.example.dao.post;

import java.util.List;

import com.example.domain.PostVO;

public interface PostDAO {
	
	public int get_p_code();
	
	public int get_c_code(int p_code);
	
	public void insert(PostVO vo);
	
	public List<PostVO> list(String s_code);

	public PostVO read(int p_code);
	
	public void updateHeadcount(int p_code);
	
	public void delete(int p_code);
	
	public PostVO order_read(int p_code);

	
	
	
}
