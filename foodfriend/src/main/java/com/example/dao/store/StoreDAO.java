package com.example.dao.store;

import java.util.HashMap;
import java.util.List;

import com.example.domain.StoreVO;

public interface StoreDAO {

	public String newCode();

	public void insert(StoreVO storeVO);

	public List<StoreVO> list();

	public List<StoreVO> clist(int s_c_code);

	public List<HashMap<String, Object>> search(String query);

	public List<StoreVO> weblist(String column, String query, int page, int num);

	public int total(String column, String query);

	public StoreVO read(String s_code);

	public StoreVO storeread(String u_id);

	public void update(StoreVO storeVO);

	public StoreVO updateStatus(String s_code);

	public void update_waiting(String s_code);

	public void s_waiting(String s_code, int count);

	public void delete(String s_code);

	public void delete(StoreVO vo);

}
