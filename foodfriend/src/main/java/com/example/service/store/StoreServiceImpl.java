package com.example.service.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.like.LikedDAO;
import com.example.dao.menu.MenuDAO;
import com.example.dao.review.ReviewDAO;
import com.example.dao.store.StoreDAO;

@Service
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	StoreDAO storeDAO;
	
	@Autowired
	MenuDAO menuDAO;
	
	@Autowired
	LikedDAO likedDAO;
	
	@Autowired
	ReviewDAO reviewDAO;
	

	@Transactional
	@Override
	public void delete(String s_code) {
		// 메뉴 전체 삭제
		if(menuDAO.list(s_code) != null) {
			menuDAO.allDelete(s_code);
		}
		
		// 즐겨찾기 삭제
		if(likedDAO.read(s_code) != null) {
			likedDAO.s_delete(s_code);
		}
		
		// 리뷰 삭제
		if(reviewDAO.read(s_code) != null) {
			reviewDAO.delete(s_code);
		}
		
		// 매장 삭제
		storeDAO.delete(s_code);
	}

}
