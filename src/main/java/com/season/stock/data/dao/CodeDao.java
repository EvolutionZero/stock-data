package com.season.stock.data.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.season.stock.data.bean.Code;
import com.season.stock.data.envm.Status;
import com.season.stock.data.utils.SpringTool;

public class CodeDao {

	private static final Logger LOG = LoggerFactory.getLogger(CodeDao.class);
	
	public static void insert(Code code){
		try {
			String sql = "insert into t_code(code,isStock,name,plate) values (?,?,?,?) ON DUPLICATE KEY UPDATE code=VALUES(code)";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			jdbcTemplate.update(sql, new Object[]{code.getCode(), code.getIsStock(), code.getName(), code.getPlate()});
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
	}
	
	public static List<String> queryAllCodeHaveNotIndusty(){
		List<String> codes = new LinkedList<String>();
		try {
			String sql = "select code from t_code where isStock = 'Y' and industry is null and status = " + Status.EXCHANGE;
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			for (Map<String,Object> map : list) {
				String code = map.get("code") == null ? null : (String)map.get("code");
				if(code != null){
					codes.add(code);
				}
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return codes;
	}
	
	public static List<String> queryAllCode(){
		List<String> codes = new LinkedList<String>();
		try {
			String sql = "select code from t_code ";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			for (Map<String,Object> map : list) {
				String code = map.get("code") == null ? null : (String)map.get("code");
				if(code != null){
					codes.add(code);
				}
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return codes;
	}
	
	public static List<String> queryAllCodeOnExchange(){
		List<String> codes = new LinkedList<String>();
		try {
			String sql = "select code from t_code where isStock = 'Y' and status = " + Status.EXCHANGE;
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : list) {
				String code = (String)map.get("code");
				codes.add(code);
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return codes;
	}
	
	public static int queryExchangeStatus(String code){
		int status = Status.UNKNOW;
		try {
			String sql = "select status from t_code where code = ?";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, new Object[]{code});
			if(!list.isEmpty()){
				status = list.get(0).get("status") == null ? status : (Integer)list.get(0).get("status");
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return status;
	}
	
	public static Map<String, String> queryAllCodeAndName(){
		Map<String, String> kvs = new HashMap<String, String>();
		try {
			String sql = "select code,name from t_code where isStock = 'Y'";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : list) {
				String code = (String)map.get("code");
				String name = (String)map.get("name");
				kvs.put(code, name);
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return kvs;
	}
	
	public static boolean isExistCode(String code){
		boolean isExist = false;
		try {
			String sql = "select code from t_code where code = ?";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, new Object[]{code});
			
			if(!list.isEmpty()){
				isExist = true;
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return isExist;
	}
	
	public static void updateName(String code, String name){
		try {
			String sql = "update t_code set name = ? where code = ?";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			jdbcTemplate.update(sql, new Object[]{name, code});
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
	}
	
	public static void updateIsStock(String code, String isStock){
		try {
			String sql = "update t_code set isStock = ? where code = ?";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			jdbcTemplate.update(sql, new Object[]{isStock, code});
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
	}
	
	public static void updateStatus(String code, int status){
		try {
			String sql = "update t_code set status = ? where code = ?";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			jdbcTemplate.update(sql, new Object[]{status, code});
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
	}
	
	public static void updateIndustry(String code, String industry){
		try {
			String sql = "update t_code set industry = ? where code = ?";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			jdbcTemplate.update(sql, new Object[]{industry, code});
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
	}
	
	public static void updateCompany(String code, String listDate, String location, String bussiness){
		try {
			String sql = "update t_code set listDate = ?, location = ?, bussiness = ? where code = ?";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			jdbcTemplate.update(sql, new Object[]{listDate, location, bussiness,  code});
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
	}
	
	public static List<String> getAllIndustry(){
		List<String> results = new LinkedList<String>();
		String sql = "select DISTINCT industry from t_code where industry is not null and isStock = 'Y' and `status` = 0 ";
		JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		for (Map<String,Object> map : list) {
			String industry = (String)map.get("industry");
			results.add(industry);
		}
		return results;
	}
	
	public static void main(String[] args) {
		System.out.println(queryAllCodeOnExchange());
	}
}
