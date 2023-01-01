package com.example.service.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.condition.ConditionDAO;
import com.example.dao.order.OrderDAO;
import com.example.dao.post.PostDAO;
import com.example.dao.store.StoreDAO;
import com.example.domain.ConditionVO;
import com.example.domain.PostVO;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	PostDAO postDAO;

	@Autowired
	ConditionDAO conditionDAO;
	
	@Autowired
	StoreDAO storeDAO;
	
	@Autowired
	OrderDAO orderDAO;
	
	// 조건 insert 후 게시글 insert
	@Transactional
	@Override
	public void insert(PostVO vo) {
		// 조건 테이블에 데이터 등록
		ConditionVO cvo = new ConditionVO();
		cvo.setHeadcount(vo.getHeadcount());
		cvo.setGender(vo.getGender());
		cvo.setAge(vo.getAge());
//		System.out.println("PostServiceImpl - insert : " + cvo.toString());
		conditionDAO.insert(cvo);
//		cdao.insert(vo);
		
		// 조건 테이블의 조건 코드 == 게시글의 조건 코드
		// 위에서 등록한 조건 코드를 게시글의 조건 코드로 설정한 후 등록
//		System.out.println("PostServiceImpl - insert : " + vo.toString());
		postDAO.insert(vo);
		storeDAO.s_waiting(vo.getS_code(), +1);
	}
	
	// 게시글 삭제 후 조건 삭제
	@Transactional
	@Override
	public void delete(int p_code) {
		// 조건 테이블의 조건 코드 == 게시글의 조건 코드
		// 삭제할 게시글의 p_code를 통해 c_code를 얻음
		int c_code = postDAO.get_c_code(p_code);
		String s_code = postDAO.read(p_code).getS_code();
		
		// 게시글 삭제
		postDAO.delete(p_code);
		// 위에서 얻은 c_code로 조건 삭제
		conditionDAO.delete(c_code);
		storeDAO.s_waiting(s_code, -1);
	}

}