package com.example.dao.cart;

import java.util.List;

import com.example.domain.CartVO;

public interface CartDAO {
	
	public void insert(CartVO vo);
	
	public List<CartVO> list(String u_code);
	
	public List<CartVO> orderlist(String p_code);
	
	public CartVO read(CartVO vo);
	
	public String getS_code(String u_code);
	
	public void update(CartVO vo);
	
	public void delete(CartVO vo);
	
	public void allDelete(String u_code);

}
