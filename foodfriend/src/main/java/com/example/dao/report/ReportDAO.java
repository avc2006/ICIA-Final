package com.example.dao.report;

import java.util.List;

import com.example.domain.ReportVO;

public interface ReportDAO {
	
	public String newCode();
	
	public void insert(ReportVO vo);
	
	public List<ReportVO> list(String column, String query, int page, int num);

	public int total(String column, String query);
	
	public ReportVO read(String r_code);

	public void update(ReportVO vo);

	public void delete(String r_code);

	public void update_del();
	
}
