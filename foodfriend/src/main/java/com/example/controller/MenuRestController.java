package com.example.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.dao.menu.MenuDAO;
import com.example.domain.MenuVO;

@RestController
@RequestMapping("/api/menu")
public class MenuRestController {

	@Autowired
	MenuDAO menuDAO;
	
	// 특정 가게에 새로운 메뉴 등록
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public int insert(MenuVO menuVO, MultipartHttpServletRequest multi) {
		try {
			// 이미지 파일을 받음
			if(multi.getFile("file") != null) {
				MultipartFile file = multi.getFile("file");
				
				// 지정한 주소의 폴더가 없으면 폴더 생성
				String path = "/upload/menu/";
				File newPath = new File("c:/" + path);
				if(!newPath.exists()) newPath.mkdir();
				
				// 새로운 파일을 업로드 하고 VO에 데이터 입력
				String fileName = file.getOriginalFilename();
				File newFile = new File(newPath + "/" + fileName);
				if(!newFile.exists()) file.transferTo(newFile);
				
				menuVO.setM_photo(path + fileName);
			}
			// 새로운 메뉴 코드를 발급받은 후 등록
			menuVO.setM_code(menuDAO.newCode(menuVO.getS_code()));
			System.out.println("MenuRestController - insert : " + menuVO.toString());
			menuDAO.insert(menuVO);
			// 등록 성공 시 1을 리턴해줌 ( 웹에서 alert 용도 )
			return 1;
		} catch(Exception e) {
			System.out.println("MenuRestController - insert : " + e.toString());
			// 등록 실패 시 2를 리턴해줌 ( 웹에서 alert 용도 )
			return 2;
		}
	}
	
	// 특정 가게의 특정 메뉴 목록
	@RequestMapping("/list")
	public List<MenuVO> list(String s_code) {
		return menuDAO.list(s_code);
	}
	
	// 특정 가게의 특정 메뉴의 정보
	@RequestMapping(value="/read", method=RequestMethod.POST)
	public MenuVO read(@RequestBody MenuVO menuVO) {
		return menuDAO.read(menuVO.getS_code(), menuVO.getM_code());
	}
	
	// 특정 가게의 특정 메뉴 데이터 수정
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public void update(MenuVO menuVO, MultipartHttpServletRequest multi) throws Exception {
		if(multi.getFile("file") != null) {
			MultipartFile file = multi.getFile("file");
			
			// 수정 전 사진 삭제
			if(menuVO.getM_photo() != null) {
				File oldFile = new File("c:/" + menuVO.getM_photo());
				if(oldFile.exists()) oldFile.delete();
			}
			
			// 사진 등록
			String path = "/upload/menu/";
			File newPath = new File("c:/" + path);
			String fileName = file.getOriginalFilename();
			File newFile = new File(newPath + "/" + fileName);
			
			if(!newFile.exists()) file.transferTo(newFile);
			
			menuVO.setM_photo(path + fileName);
		}
//		System.out.println("MenuRestController - update : " + menuVO.toString());
		menuDAO.update(menuVO);
	}
	
	// 특정 가게의 특정 메뉴를 삭제하고 업로드 된 이미지도 삭제
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public void delete(@RequestBody MenuVO menuVO) {
		File file = new File("c:/" + menuVO.getM_photo());
		if(file.exists()) file.delete();
		
		menuDAO.delete(menuVO.getS_code(), menuVO.getM_name());
	}
	
}
