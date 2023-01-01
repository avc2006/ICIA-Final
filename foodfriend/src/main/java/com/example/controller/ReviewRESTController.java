package com.example.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.dao.review.ReviewDAO;
import com.example.dao.store.StoreDAO;
import com.example.dao.user.UserDAO;
import com.example.domain.ReviewVO;
import com.example.service.review.ReviewService;

@RestController
@RequestMapping("/api/review")
public class ReviewRESTController {
	
	@Autowired
	ReviewDAO reviewDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	StoreDAO storeDAO;
	
	@Autowired
	ReviewService reviewService;
	
	
	@RequestMapping("/list")
	public List<ReviewVO> list(){
		return reviewDAO.list();
	}
	
	@RequestMapping("/uread/{u_code}")
	public HashMap<String, Object> uread(@PathVariable String u_code){
		HashMap<String, Object> map = new HashMap<>();
		map.put("review", reviewDAO.uread(u_code));
		map.put("user", userDAO.read(u_code));
		
		return map;
	}

	@RequestMapping("/sread/{s_code}")
	public HashMap<String, Object> sread(@PathVariable String s_code) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("review", reviewDAO.sread(s_code));
		map.put("store", storeDAO.read(s_code));
		return map;
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public void insert(@RequestBody ReviewVO vo) {
		
//		if (multi.getFile("partImage") != null) {
//		MultipartFile file = multi.getFile("partImage");
//
//		// 해당 주소에 폴더가 없으면 폴더 생성
//		String path = "/upload/store/review/";
//		File newPath = new File("c:/" + path);
//		if (!newPath.exists())
//			newPath.mkdir();
//
//		// 새로운 파일을 업로드 하고 vo에 데이터 입력
//		String fileName = file.getOriginalFilename();
//		File newFile = new File(newPath + "/" + fileName);
//		if (!newFile.exists())
//			file.transferTo(newFile);
//		vo.setR_photo(path + fileName);
//	}
		vo.setR_code(reviewDAO.newCode(vo.getS_code()));
		System.out.println("ReviewRestController - insert : " + vo);
		reviewService.insert(vo);
	}
	
	@RequestMapping(value="/photoupdate", method=RequestMethod.POST)
	public void update(ReviewVO vo, MultipartHttpServletRequest multi) throws Exception {
		if(multi.getFile("file") != null) {
			MultipartFile file = multi.getFile("file");
			String path = "/upload/store/review";
			File newPath = new File("c:/" + path);
			if(!newPath.exists()) newPath.mkdir();
			
			String fileName = file.getOriginalFilename();
			File newFile = new File(newPath + "/" + fileName);
			if(!newFile.exists()) file.transferTo(newFile);
			vo.setR_photo(path + fileName);
		}
		reviewDAO.photoupdate(vo.getR_code());
	}
	
	@RequestMapping(value="/delete/{u_code}", method=RequestMethod.POST)
	public void delete(@PathVariable String u_code){
		reviewService.delete(u_code);
	}
	
	@RequestMapping(value="/storeinsert", method=RequestMethod.POST)
	public void storeinsert(@RequestBody ReviewVO vo){
		reviewDAO.storeinsert(vo);
	}
	
	@RequestMapping(value="/reviewupdate/{r_code}", method=RequestMethod.POST)
	public void update(@PathVariable String r_code){
		reviewDAO.reviewupdate(r_code);
	}
	
}
