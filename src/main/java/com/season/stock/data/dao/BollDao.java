package com.season.stock.data.dao;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.season.stock.data.bean.Boll;
import com.season.stock.data.bean.Stock;
import com.season.stock.data.handle.StockRowHandle;
import com.season.stock.data.utils.SpringTool;

public class BollDao {

	private static final Logger LOG = LoggerFactory.getLogger(BollDao.class);
	
	public static List<Stock> queryHaveNotCalculateBoll(){
		List<Stock> stocks = new LinkedList<Stock>();
		try {
			String sql = "select * from t_daily where boll_upper =0 or boll_upper is null limit 1000";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			stocks = jdbcTemplate.query(sql, new StockRowHandle());
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return stocks;
		
	}
	
	public static void updateBollById(String id , Boll boll){
		try {
			String sql = "update t_daily set boll_upper = ? , boll_lower = ?, boll_percentB = ? where id = ?";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			jdbcTemplate.update(sql, new Object[]{boll.getUpper(), boll.getLower(), boll.getPercentB() , id});
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(queryHaveNotCalculateBoll());
	}
	
}
