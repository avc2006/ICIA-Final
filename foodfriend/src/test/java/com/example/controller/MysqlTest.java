package com.example.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.dao.user.UserDAO;
import com.example.domain.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class MysqlTest {
	
//	@Autowired
//	StoreDAO storedao;
//	
//	@Autowired
//	MenuDAO menudao;
//	
//	@Autowired
//	LikedDAO likeddao;
//	
//	@Autowired
//	ReviewDAO reviewdao;
//	
//	@Autowired
//	PostDAO postdao;
//	
//	@Autowired
//	CartDAO cartdao;
	
	@Autowired
	UserDAO userdao;
	
//	@Autowired
//	StoreService storeservice;
	
	
//	@Transactional
//	@Test
//	public void storeInsert() {
//		StoreVO vo = new StoreVO();
//		vo.setS_code(storedao.newCode());
//		vo.setS_name("롯데리아");
//		vo.setS_admin("담당자");
//		vo.setS_location("인천");
//		vo.setS_tel("010");
////		vo.setS_c_code(3);
//		storedao.insert(vo);
//	}
//	
//	@Transactional
//	@Test
//	public void cartInsert() {
//		CartVO vo = new CartVO();
//		vo.setU_code("u1");
//		vo.setS_code("s1");
//		vo.setM_name("테스트");
//		vo.setAmount(1);
//		vo.setM_photo(null);
//		vo.setM_price(20000);
//		cartdao.insert(vo);
//	}
	
	@Test
	public void userInsert() {
		UserVO vo = new UserVO();
		vo.setU_code("d0");
		vo.setU_name("삭제된 사용자 입니다");
		vo.setU_address("삭제된 사용자 입니다");
		vo.setU_id("delete");
		vo.setU_pass("pass");
		vo.setAge(0);
		vo.setGender("uni");
		userdao.insert(vo);
	}
	
	
//	@Transactional
//	@Test
//	public void menuNewCode(String s_code) {
//		menudao.newCode("s1");
//	}
//	
//	@Transactional
//	@Test
//	public void storeList() {
//		storedao.list();
//	}
//	
//	@Transactional
//	@Test
//	public void storeCList() {
//		storedao.clist(1);
//	}
//	
//	@Transactional
//	@Test
//	public void menuList() {
//		menudao.list("s1");
//	}
//	
//	@Transactional
//	@Test
//	public void postList() {
//		postdao.list("s1");
//	}
//	
//	
//	@Transactional
//	@Test
//	public void storeRead() {
//		storedao.read("s1");
//	}
//	
//	@Transactional
//	@Test
//	public void menuRead() {
//		menudao.read("s1", 1);
//	}
//	
//	
//	@Transactional
//	@Test
//	public void storeUpdate() {
//		StoreVO vo = new StoreVO();
//		vo.setS_code("s4");
////		vo.setS_c_code(3);
//		vo.setS_name("롯데리아 인천학익점");
//		vo.setS_location("인천 미추홀구 매소홀로 466");
//		vo.setS_tel("032-873-4115");
//		vo.setS_admin("담당자");
//		vo.setS_photo("ex.jpg");
//		storedao.update(vo);
//	}
//	
//	@Transactional
//	@Test
//	public void menuUpdate() {
//		MenuVO vo = new MenuVO();
//		vo.setS_code("s1");
//		vo.setM_name("맛있는 불고기버거");
//		vo.setM_price(1000);
//		vo.setM_photo("ex.jpg");
//		menudao.update(vo);
//	}
//	
//	
//	@Transactional
//	@Test
//	public void storeDelete() {
//		storedao.delete("s4");
//	}
//	
//	@Transactional
//	@Test
//	public void menuDelete() {
//		menudao.delete("s3", "1");
//	}
//	
//	@Transactional
//	@Test
//	public void menuAllDelete() {
//		menudao.allDelete("s3");
//	}
//	
//	@Transactional
//	@Test
//	public void LikedDelete() {
//		likeddao.delete("s3");
//	}
//	
//	@Transactional
//	@Test
//	public void ReviewDelete() {
//		reviewdao.delete("s3");
//	}
//	
//	@Transactional
//	@Test
//	public void storeServiceDelete() {
//		storeservice.delete("s3");
//	}
	
}