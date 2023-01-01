package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.cart.CartDAO;
import com.example.domain.CartVO;
import com.example.service.cart.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartRESTController {

	@Autowired
	CartDAO cartDAO;
	
	@Autowired
	CartService cartService;
	
	// 장바구니에 메뉴 등록
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public void insert(@RequestBody CartVO vo){
//		System.out.println("CartRestController - insert : " + vo);
		// 등록하려는 메뉴가 장바구니에 이미 존재 하는지 확인
		CartVO read = cartDAO.read(vo);
//		System.out.println("CartRestController - insert : " + cartDAO.read(vo));
		
		// 등록하려는 메뉴가 장바구니에 존재 할 경우
		try{
			if(read.getM_name().equals(vo.getM_name())){
				System.out.println("CartRestController - insert :: 기존값 : " + read.getAmount());
				System.out.println("CartRestController - insert :: 추가되는 값 : " + vo.getAmount());
				// 추가로 담는 수량 업데이트
				vo.setAmount(read.getAmount() + vo.getAmount());
				System.out.println("CartRestController - insert :: 총합  : " + vo.getAmount());
				cartDAO.update(vo);
			}
		} catch(NullPointerException e) {	// 장바구니에 등록하려는 메뉴가 담겨있지 않은 경우
			// 장바구니에 새로운 메뉴 등록
			cartDAO.insert(vo);
		}
	}
	
	//기존 장바구니에 담긴 가게와 새로 담는 가게가 다를 경우 기존 장바구니 비우고 새로 등록
	@RequestMapping(value="/new_insert", method=RequestMethod.POST)
	public void new_insert(@RequestBody CartVO vo){
		cartService.new_insert(vo);
	}
	
	// 장바구니에 담긴 가게와의 일치 여부를 확인하기 위함
	@RequestMapping("/getS_code/{u_code}")
	public String getS_code(@PathVariable String u_code){
//		System.out.println("CartRestController - getS_code : " + u_code);
		return cartDAO.getS_code(u_code);
	}
	
	@RequestMapping("/orderlist/{p_code}")
	public List<CartVO> orderlist(@PathVariable String p_code){
		return cartDAO.orderlist(p_code);
	}
	
	// 특정 유저의 장바구니 목록
	@RequestMapping("/list/{u_code}")
	public List<CartVO> list(@PathVariable String u_code){
		return cartDAO.list(u_code);
	}
	
	// 특정 유저의 장바구니에 담긴 메뉴 수량 수정
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public void update(@RequestBody CartVO vo){
		cartDAO.update(vo);
	}
	
	// 특정 유저의 장바구니의 메뉴 삭제
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public void delete(@RequestBody CartVO vo){
		cartDAO.delete(vo);
	}
	
}
