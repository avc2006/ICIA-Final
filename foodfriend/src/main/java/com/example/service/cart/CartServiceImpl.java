package com.example.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.cart.CartDAO;
import com.example.domain.CartVO;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	CartDAO cartDAO;

	// 새로운 가게의 메뉴를 담을 경우 기존 장바구니를 비우고 다른 가게의 메뉴 담기
	@Transactional
	@Override
	public void new_insert(CartVO vo) {
		// 기존 장바구니 비우기
		cartDAO.allDelete(vo.getU_code());
		// 새로운 메뉴 담기
		cartDAO.insert(vo);
	}
	
}
