package com.example.service.post;

import com.example.domain.PostVO;

public interface PostService {
	
	public void insert(PostVO vo);
	
	public void delete(int p_code);
	
}
