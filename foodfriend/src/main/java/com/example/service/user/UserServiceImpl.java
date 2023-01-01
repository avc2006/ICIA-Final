package com.example.service.user;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.like.LikedDAO;
import com.example.dao.report.ReportDAO;
import com.example.dao.review.ReviewDAO;
import com.example.dao.user.UserDAO;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO userDAO;

	@Autowired
	ReviewDAO reviewdao;

	@Autowired
	ReportDAO reportdao;

	@Autowired
	LikedDAO likedDAO;
	

	// 유저 삭제 테이블에 삭제 할 유저 등록
	@Transactional
	@Override
	public void delUser(String u_code) {
		userDAO.updateStatus(u_code);
		userDAO.delUser(u_code);
	}

	// 유저 삭제 테이블에서 특정 유저 복원
	@Transactional
	@Override
	public void reUser(String u_code) {
		userDAO.updateStatus(u_code);
		userDAO.reUser(u_code);
	}

	// 유저 삭제 테이블에서 14일 이상 지난 유저 완전 삭제
	@Transactional
	@Override
	public void del_user() {
		reviewdao.update_del();
		reportdao.update_del();
		likedDAO.u_delete();
		userDAO.del_user();
	}
	
	// 문자 인증
	@Override
	public void authPhoneNumber(String phoneNumber, String authNum) {
		String api_key = "NCSYQ6CPPVTTOVUM";
		String api_secret = "5OWEDMRN9DRE7GUJ2IKYTI8S7ZPG6ZPA";
		Message coolSMS = new Message(api_key, api_secret);
		
		HashMap<String, String> params = new HashMap<>();
		params.put("to", phoneNumber);		// 수신 전화 번호
		params.put("from", "01076595907");	// 테스트 시 본인 번호 입력
		params.put("type", "SMS");
		params.put("text", "[밥친구 인증]\n본인 확인 인증 번호는 [ " + authNum + " ] 입니다.");	// 문자 내용
		params.put("app_version", "test app 1.2");				// application name and version
		
		try {
			JSONObject object = (JSONObject)coolSMS.send(params);
			System.out.println("UserServiceImpl - authPhoneNumber : " + object.toString());
		} catch(CoolsmsException e) {
			System.out.println("UserServiceImpl - authPhoneNumber : " + e.getMessage());
			System.out.println("UserServiceImpl - authPhoneNumber : " + e.getCode());
		}
	}

}
