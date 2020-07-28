package com.season.stock.data.dao;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.season.stock.data.bean.Extreme;
import com.season.stock.data.utils.SpringTool;

public class ExtremeDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExtremeDao.class);
	
	public static void save(List<Extreme> extremes){
		String sql = "insert into t_extreme(code,value,date,level,md5,type ) values (?,?,?,?, MD5(?),?)";
		try {
			List<Object[]> params = new LinkedList<Object[]>();
			for (Extreme extreme : extremes) {
				String key = extreme.getCode() + "||" + extreme.getValue() + "||" + extreme.getDate()
					+ "||" + extreme.getLevel();
				
				Object[] param = new Object[]{
						extreme.getCode(), extreme.getValue(), extreme.getDate(), extreme.getLevel(), key,
						extreme.getType()
				};
				
				params.add(param);
			}
			
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			jdbcTemplate.batchUpdate(sql, params);
		} catch (Exception e) {
			LOG.error("", e);
		}
	}
	
	public static List<Extreme> query(String code, int level){
		List<Extreme> extremes = new LinkedList<Extreme>();
		try {
			String sql = "select * from t_extreme where code = ? and level = ?";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, new Object[]{code , level});
			for (Map<String, Object> map : list) {
				String sCode = map.get("code") == null ? "" : (String)map.get("code");
				double value = map.get("value") == null ? 0 : (Double)map.get("value");
				String date = map.get("date") == null ? "" : (String)map.get("date");
				
				Extreme extreme = new Extreme();
				extreme.setCode(sCode);
				extreme.setValue(value);
				extreme.setDate(date);
				
				extremes.add(extreme);
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return extremes;
	}
	
	public static String getLastDate(String code, int level){
		String date = "";
		try {
			String sql = "select MAX(date) as lastDate from t_extreme where code = ? and level = ?";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new Object[]{code, level});
			if(!list.isEmpty()){
				date = list.get(0).get("lastDate") == null ? date : (String)list.get(0).get("lastDate");
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return date;
	}
}
