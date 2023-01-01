package com.example.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.reply.ReplyDAO;
import com.example.dao.review.ReviewDAO;
import com.example.domain.ReplyVO;

@RestController
@RequestMapping("/api/reply")
public class ReplyRestController {

	@Autowired
	ReviewDAO reviewDAO;
	
	@Autowired
	ReplyDAO replyDAO;
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public void insert(ReplyVO vo){
		replyDAO.insert(vo);
	}
	
	@RequestMapping("/uread/{r_code}")
	public HashMap<String, Object> uread(@PathVariable String r_code){
		HashMap<String, Object> map = new HashMap<>();
		map.put("reply", replyDAO.uread(r_code));
		
		return map;
	}
}
