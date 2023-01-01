package com.example.service.review;

import com.example.domain.ReviewVO;

public interface ReviewService {
	
	public void insert(ReviewVO vo);

	public void delete(String u_code);
	
}
