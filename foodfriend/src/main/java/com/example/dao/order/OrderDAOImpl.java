package com.example.dao.order;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderVO;

@Repository
public class OrderDAOImpl implements OrderDAO{
	
	@Autowired
	SqlSession session;
	
	String namespace = "com.example.mapper.OrderMapper";

	@Override
	public void insert(int p_code, String u_code) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("p_code", p_code);
		map.put("u_code", u_code);
		
		session.insert(namespace + ".insert", map);
	}
	
	// ��ū �� �޾Ƽ� �˸� ������ - PostRestController���� �����
	public String notificationJson(int p_code) throws JSONException {
		JSONObject body = new JSONObject();
		
		List<String> tokenList = session.selectList(namespace + ".getTokenList", p_code);
		System.out.println("OrderDAOImpl - notificationJson : " + tokenList);

		// �� ����̽� ��ū ��
		JSONArray array = new JSONArray();
		for (int i = 0; i < tokenList.size(); i++) {
			array.put(tokenList.get(i));
		}
		body.put("registration_ids", array);

		JSONObject notification = new JSONObject();
		notification.put("title", "Click Me!");					// �˸� ����
		notification.put("body", "Matching completed! plz proceed to pay");	// �˸� ����
		body.put("notification", notification);

		System.out.println(body.toString());

		return body.toString();
	}

	@Override
	public void update(OrderVO orderVO) {
		session.update(namespace + ".update", orderVO);
	}

	@Override
	public int get_p_code(String u_code) {
		return session.selectOne(namespace+".get_p_code",u_code);
	}
	


}
