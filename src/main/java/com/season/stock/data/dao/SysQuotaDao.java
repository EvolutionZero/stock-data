package com.season.stock.data.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.season.stock.data.utils.SpringTool;

public class SysQuotaDao {

	private static final Logger LOG = LoggerFactory.getLogger(SysQuotaDao.class);
	
	public static void insert(String date, double accuracy){
		String sql = "insert into t_sys_quota(date,accuracy) values (?,?)";
		try {
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			jdbcTemplate.update(sql, new Object[]{date, accuracy});
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
	}
}
