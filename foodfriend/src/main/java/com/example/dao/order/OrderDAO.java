package com.example.dao.order;

import com.example.domain.OrderVO;

public interface OrderDAO {
	
	public void insert(int p_code, String u_code);

	public String notificationJson(int p_code);
	
	public void update(OrderVO orderVO);

	public int get_p_code(String u_code);
}
