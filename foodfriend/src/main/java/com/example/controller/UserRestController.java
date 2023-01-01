package com.example.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.user.UserDAO;
import com.example.domain.UserVO;
import com.example.service.user.UserService;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
	
	// UserDAO 연결
	@Autowired
	UserDAO userDAO;
	
	// UserService 연결	
	@Autowired
	UserService userService;
	
	// 비밀번호 암호화
	@Autowired
	PasswordEncoder encoder;
	
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public void insert(@RequestBody UserVO userVO) {
		userVO.setU_code(userDAO.newCode());
		userVO.setU_pass(encoder.encode(userVO.getU_pass()));
//		System.out.println("UserRestController - insert : " + userVO.toString());
		userDAO.insert(userVO);
	}
	
	// 유저 검색 (웹)
	@RequestMapping("/list")
	public HashMap<String, Object> list(String column, String query, int page, int num) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("list", userDAO.list(column, query, page, num));
		map.put("total", userDAO.total(column, query));
		return map;
	}
	
	// 코드로  특정 유저의 정보 읽기 (앱)
	@RequestMapping("/read/{u_code}")
	public UserVO read(@PathVariable String u_code) {
		UserVO vo = userDAO.read(u_code);
//			System.out.println("UserRestController - read : " + vo.toString());
		return vo;
	}
	
	// 유저 정보 변경
	@ResponseBody
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public void update(
			@RequestParam(value="u_code") String u_code,
			@RequestParam(value="u_pass") String u_pass,
			@RequestParam(value="u_address") String u_address,
			@RequestParam(value="u_image", required=false) MultipartFile u_image) {
		
		try {
//			System.out.println("UserRestController - update :: 동작");
			UserVO oldVO = userDAO.read(u_code);
			UserVO vo = new UserVO();
			
			if (u_image != null) {
	            if (oldVO.getU_photo() != null) {
	               File oldFile = new File("c:/" + oldVO.getU_photo());
	               if (oldFile.exists())
	                  oldFile.delete();
	            }
	            String path = "c:/upload/user/";
	            String name = u_image.getOriginalFilename();
	            File newFile = new File(path + name);
	            if (!newFile.exists()) {
	               u_image.transferTo(newFile);
	            }

	            vo.setU_photo("/upload/user/" + name);
	         } else {
	            vo.setU_photo(oldVO.getU_photo());
	         }

	         if (!oldVO.getU_pass().equals(u_pass)) {
	            vo.setU_pass(encoder.encode(u_pass));
	         } else {
	            vo.setU_pass(oldVO.getU_pass());
	         }
	         vo.setU_code(u_code);
	         vo.setU_address(u_address);
//	         System.out.println("UserRestController - update : " + vo.getU_address());
	         userDAO.update(vo);
		} catch (Exception e) {
			System.out.println("UserRestController - update : " + e.toString());
		}
	}
	
	// 유저 삭제 테이블에 삭제 할 유저 등록
	@RequestMapping(value="/delete/{u_code}", method=RequestMethod.POST)
	public void delUser(@PathVariable String u_code){
		userService.delUser(u_code);
	}
	
	// 삭제 테이블에 있는 유저 복원
	@RequestMapping(value="/recover/{u_code}", method=RequestMethod.POST)
	public void reUser(@PathVariable String u_code){
		userService.reUser(u_code);
	}
	
	// 유저 삭제 테이블에서 14일 이상 지난 유저 삭제 (최종삭제)
	@RequestMapping(value="/del_user", method=RequestMethod.POST)
	public void del_user(){
//		System.out.println("UserRestController - del_user :: check");
		userService.del_user();
	}
	
	// 로그인 체크
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public HashMap<String, Object> login(@RequestBody UserVO vo) {
//		System.out.println("UserRestController - login : " + vo.toString());
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		int u_type = 0;
		String u_id = "";
		String u_code = "";
		UserVO readvo = userDAO.loginRead(vo.getU_id());
		if (readvo != null) {
			if (encoder.matches(vo.getU_pass(), readvo.getU_pass())) {
				result = 2;
				u_type = readvo.getU_type();
				u_id = readvo.getU_id();
				u_code = readvo.getU_code();
			} else {
				result = 1;
			}
		}
		map.put("check", result);
		map.put("u_type", u_type);
		map.put("u_id", u_id);
		map.put("u_code", u_code);
//		System.out.println("UserRestController - login : " + map);
		return map;
	}
	
	// 안드로이드 회원가입
	@RequestMapping(value="/and_insert", method=RequestMethod.POST)
	public int and_insert(@RequestBody UserVO userVO) {
//		System.out.println("UserRestController - and_insert : " + userVO.toString());
		int result = 0;
		
		UserVO getUserVO = userDAO.loginRead(userVO.getU_id());	// 동일 아이디 존재여부 확인
		if(getUserVO != null) {	// 동일 아이디 존재 시
				System.out.println("UserRestController - and_insert :: 같은 아이디 존재");
		} else {	// 동일 아이디 부재 시
			userVO.setU_code(userDAO.newCode());
			userVO.setU_pass(encoder.encode(userVO.getU_pass()));
			userVO.setAge(userDAO.getAge(userVO.getBirthday()));
//			System.out.println("UserRestController - and_insert : " + userVO.toString());
			userDAO.insert(userVO);
			result = 1;
		}
		return result;
	}
	
	// 안드로이드 카카오 회원가입
	@RequestMapping(value="/and_kakao_insert", method=RequestMethod.POST)
	public int and_kakao_insert(@RequestBody UserVO userVO) {
//		System.out.println("UserRestController - and_kakao_insert : " + userVO.toString());
		int result = 0;
		
		UserVO getUserVO = userDAO.loginRead(userVO.getU_id());	// 동일 아이디 존재여부 확인
		try {
			if(getUserVO != null) {	// 동일 아이디 존재 시
					System.out.println("UserRestController - and_kakao_insert :: 같은 아이디 존재");
			} else {	// 동일 아이디 부재 시
				userVO.setU_code(userDAO.newKakaoCode(userVO.getU_code()));	// 카카오 회원가입 사용자 코드 부여
				userVO.setU_pass(encoder.encode(userVO.getU_pass()));		// 비밀번호 암호화
				userDAO.insert(userVO);
				result = 2;
			}
			return result;
		} catch(Exception e) {
			// 동일 카카오 코드 존재 시
			System.out.println("UserRestController - and_kakao_insert :: 이미 회원가입 된 사용자입니다");
			result = 1;
			return result;
		}
	}
	
	// 안드로이드 로그인 체크
	@RequestMapping(value="/and_login", method=RequestMethod.POST)
	public UserVO and_login(@RequestBody UserVO userVO) {
		UserVO getUserVO = userDAO.loginRead(userVO.getU_id());
		
		if(getUserVO != null) {
			if (encoder.matches(userVO.getU_pass(), getUserVO.getU_pass())) {
				System.out.println("UserRestController - and_login :: 로그인 성공");
				return getUserVO;
			} else {
				System.out.println("UserRestController - and_login :: 비밀번호 불일치");
				return null;
			}
		} else {
			System.out.println("UserRestController - and_login :: 입력된 아이디와 일치하는 아이디 없음");
			return null;
		}
	}
	
	// 안드로이드 회원가입 시 문자 인증
	@RequestMapping("/sendAuthSMS")
	public String sendAuthSMS(String phoneNumber) {
		Random randomNum = new Random();	// 난수 형성
		String authNum = "";
		
		for(int i = 0; i < 6; i++) {	// 인증 번호 6자리로 형성
			String random = Integer.toString(randomNum.nextInt(10));
			authNum += random;
		}
		
//		System.out.println("UserRestController - sendAuthSMS :: 수신자 번호 : " + phoneNumber);
		System.out.println("UserRestController - sendAuthSMS :: 인증 번호 : " + authNum);
		userService.authPhoneNumber(phoneNumber, authNum);
		
		return authNum;
	}
	
}
