package com.example.dao.menu;

import java.util.List;

import com.example.domain.MenuVO;

public interface MenuDAO {
	
	public int newCode(String s_code);
	
	public void insert(MenuVO menuVO);
	
	public List<MenuVO> list(String s_code);
	
	public MenuVO read(String s_code, int m_code);
	
	public void update(MenuVO menuVO);
	
	public void delete(String s_code, String m_name);
	
	public void allDelete(String s_code);
	
}
