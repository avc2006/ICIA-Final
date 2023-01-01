package com.example.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.dao.like.LikedDAO;
import com.example.dao.store.StoreDAO;
import com.example.domain.StoreVO;
import com.example.service.store.StoreService;

@RestController
@RequestMapping("/api/store")
public class StoreRestController {

	@Autowired
	StoreDAO storeDAO;
	
	@Autowired
	StoreService storeService;
	
	@Autowired
	LikedDAO likedDAO;
	
	
	// 매장 등록
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public void insert(StoreVO storeVO, MultipartHttpServletRequest multi) throws Exception {
		MultipartFile file = multi.getFile("file");
		String path = "/upload/store/photo";
		File newPath = new File("c:/" + path);
		String fileName = file.getOriginalFilename();
		File newFile = new File(newPath + "/" + fileName);
		
		if(multi.getFile("file") != null) {
			if(!newPath.exists()) newPath.mkdir();
			
			if(!newFile.exists()) file.transferTo(newFile);
			
			storeVO.setS_photo(path + fileName);
		}
		storeVO.setS_code(storeDAO.newCode());
		storeDAO.insert(storeVO);
	}
	
	// 매장 리스트와 카테고리명
	@RequestMapping("/list")
	public List<StoreVO> list() {
		return storeDAO.list();
	}
	
	@RequestMapping("/weblist")
	public HashMap<String, Object> weblist(String column, String query, int page, int num) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("list", storeDAO.weblist(column, query, page, num));
		map.put("total", storeDAO.total(column, query));
		return map;
	}
	
	// 카테고리별 매장 리스트
	@RequestMapping("/clist/{s_c_code}")
	public List<StoreVO> s_c_list(@PathVariable int s_c_code){
		return storeDAO.clist(s_c_code);
	}
	
	//특정유저의 즐겨찾기 매장 목록
	@RequestMapping("/liked/{u_code}")
	public List<StoreVO> liked(@PathVariable String u_code){
		return likedDAO.list(u_code);
	}
	
	/**
	 * 리턴 타입 List<StoreVO> || StoreVO 리액트 참고 수정 요망
	 */
	// 특정 매장 정보
	@RequestMapping("/read/{s_code}")
	public StoreVO read(@PathVariable String s_code) {
		return storeDAO.read(s_code);
	}
	
	// 관리자 아이디로 특정 매장 정보 읽기
	@RequestMapping("/storeread/{u_id}")
	public StoreVO storeread(@PathVariable String u_id) {
		return storeDAO.storeread(u_id);
	}
	
	// 검색어에 따른 매장 및 메뉴
	@RequestMapping("/search")
	public List<HashMap<String, Object>> search (String query){
		return storeDAO.search(query);
	}
	
	// 매장 정보 수정
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public void update(StoreVO storeVO, MultipartHttpServletRequest multi) throws Exception {
		if(multi.getFile("file") != null) {
			MultipartFile file = multi.getFile("file");
			String path = "/upload/store/";
			File newPath = new File("c:/" + path);
			if(!newPath.exists()) newPath.mkdir();
			
			String fileName = file.getOriginalFilename();
			File newFile = new File(newPath + "/" + fileName);
			if(!newFile.exists()) file.transferTo(newFile);
			
			storeVO.setS_photo(path + fileName);
		}
		storeDAO.update(storeVO);
	}
	
	// 매장 영업 상태 변경
	@RequestMapping(value="/updateStatus/{s_code}", method=RequestMethod.POST)
	public void updateStatus(@PathVariable String s_code) {
		storeDAO.updateStatus(s_code);
	}
	
	// 특정 매장 삭제
	@RequestMapping(value="/delete/{s_code}", method=RequestMethod.POST)
	public void delete(@PathVariable String s_code) {
		StoreVO vo = storeDAO.read(s_code);
		File file = new File("c:/" + vo.getS_photo());
		if(file.exists()) file.delete();
		storeService.delete(vo.getS_code());
	}
	
}
