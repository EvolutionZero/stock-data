package com.season.stock.data.dao;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.season.stock.data.bean.StrengthIdx;
import com.season.stock.data.utils.SpringTool;

public class IndustryDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(IndustryDao.class);
	
	public static void insertIdx(List<StrengthIdx> idxs){
		List<Object[]> params = new LinkedList<Object[]>();
		try {
			if(idxs.isEmpty()){
				return ;
			}
			String sql = "insert into t_industry_stat(name, date, riseNum, riseTotalRate, riseProbabily, strength) values (?,?,?,?,? ,?)";
			int count = 0;
			for (StrengthIdx idx : idxs) {
				count++;
				
				Object[] param = new Object[]{
						idx.getName(), idx.getDate(), idx.getRiseNum(), idx.getRiseTotalRate(), idx.getRiseProbabily(),
						idx.getStrength()
				};
				
				params.add(param);
				
				if(count > 0 && count % 100 == 0){
					JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
					jdbcTemplate.update(sql, param);
					params.clear();
				}
				
			}
			
			if(!params.isEmpty()){
				JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
				jdbcTemplate.batchUpdate(sql, params);
				params.clear();
			}
		} catch (Exception e) {
			LOG.error("", e);
		}
	}
	
	public static String getLastStatDate(){
		String date = "";
		try {
			String sql = "select MAX(DATE) as lastStatDay from t_industry_stat";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			if(!list.isEmpty()){
				java.sql.Date temp = list.get(0).get("lastStatDay") == null ? null : (java.sql.Date)list.get(0).get("lastStatDay");
				date = temp == null ? date : new SimpleDateFormat("yyyy-MM-dd").format(temp);
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return date;
	}
}
