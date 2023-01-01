package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.order.OrderDAO;
import com.example.domain.OrderVO;

@RestController
@RequestMapping("/api/order")
public class OrderRestController {
	
	// OrderDAO 연결
	@Autowired
	OrderDAO orderDAO;
	
	//주문 상태 변경
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public void update(@RequestBody OrderVO orderVO){
		orderDAO.update(orderVO);
		System.out.println("OrderRestController - update : " + orderVO.toString());
	}
	
}
