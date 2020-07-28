package com.season.stock.data.dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.season.stock.data.bean.Boll;
import com.season.stock.data.bean.Stock;
import com.season.stock.data.bean.WR;
import com.season.stock.data.handle.StockRowHandle;
import com.season.stock.data.utils.SpringTool;
import com.season.stock.data.utils.StatUtils;

public class DailyDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(DailyDao.class);
	
	public static void insert(Stock stock){
		try {
			LinkedList<Stock> stocks = new LinkedList<Stock>();
			stocks.add(stock);
			insert(stocks);
		} catch (Exception e) {
			LOG.error("", e);
		}
	}
	
	public static void insert(List<Stock> stocks){
		try {
			if(stocks.isEmpty()){
				return ;
			}
			String sql = "insert into t_daily(i_code,date,d_open,high,d_close,"
					+ " low,volume,amount,d_change,precentChange,"
					+ " ma5,ma10,ma20,vma5,vma10,"
					+ " vma20,turnover, s_md5, valueChange)"
					+ " values (?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?, MD5(concat(?, ?)) , ?)";
			int count = 0;
			List<Object[]> params = new LinkedList<Object[]>();
			for (Stock stock : stocks) {
				count++;
				
				Object[] param = new Object[]{
						stock.getCode(), stock.getDate(), stock.getOpen(), stock.getHigh(), stock.getClose(),
						stock.getLow(), stock.getVolume(), stock.getAmount(), stock.getChange(), stock.getPrecentChange(),
						stock.getMa5(), stock.getMa10(), stock.getMa20(), stock.getVma5(), stock.getVma10(),
						stock.getVma20(), stock.getTurnover(), stock.getCode(), stock.getDate(),  stock.getValueChange()
				};
				
				params.add(param);
				
				if(count > 0 && count % 100 == 0){
					JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
					jdbcTemplate.batchUpdate(sql, params);
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
	
	public static void removeRepeatDaily(){
		try {
			String query = "select s_md5 from t_daily GROUP BY s_md5 having count(s_md5) > 1";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String, Object>> list = jdbcTemplate.queryForList(query);
			
			String delete = "DELETE FROM t_daily where s_md5 in (";
			StringBuilder sb = new StringBuilder();
			for (Map<String,Object> map : list) {
				String md5 = map.get("s_md5") == null ? "" : (String)map.get("s_md5");
				sb.append("\"").append(md5).append("\"").append(",");
			}
			if(sb.length() > 1){
				sb.delete(sb.length() - 1 , sb.length());
				delete = delete + sb.toString() + ")";
				jdbcTemplate.update(delete);
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
	}
	
	public static void removeRepeatForecast(){
		try {
			String query = "select md5 from t_forecast GROUP BY md5 having count(md5) > 1";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String, Object>> list = jdbcTemplate.queryForList(query);
			
			String delete = "DELETE FROM t_forecast where md5 in (";
			StringBuilder sb = new StringBuilder();
			for (Map<String,Object> map : list) {
				String md5 = map.get("s_md5") == null ? "" : (String)map.get("s_md5");
				sb.append("\"").append(md5).append("\"").append(",");
			}
			if(sb.length() > 1){
				sb.delete(sb.length() - 1 , sb.length());
				delete = delete + sb.toString() + ")";
				jdbcTemplate.update(delete);
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
	}
	
	public static List<String> getAllTradeDay(){
		List<String> tradeDays = new LinkedList<String>();
		try {
			String sql = "select DISTINCT date from t_daily";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Map<String,Object> map : list) {
				Date date = map.get("date") == null ? new Date(System.currentTimeMillis()) : (Date)map.get("date");
				tradeDays.add(sdf.format(date));
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return tradeDays;
	}
	
	public static List<String> getTradeDayDesc(int limit){
		List<String> tradeDays = new LinkedList<String>();
		try {
			String sql = "select DISTINCT date from t_daily order by date desc limit ?";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new Object[]{limit});
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Map<String,Object> map : list) {
				Date date = map.get("date") == null ? new Date(System.currentTimeMillis()) : (Date)map.get("date");
				tradeDays.add(sdf.format(date));
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return tradeDays;
	}
	
	public static String getLastUpdateTime(String code){
		String date = "";
		try {
			String sql = "select MAX(date) as lastUpdateTime from t_daily where i_code = ?";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new Object[]{code});
			if(!list.isEmpty()){
				java.sql.Date temp = list.get(0).get("lastUpdateTime") == null ? null : (java.sql.Date)list.get(0).get("lastUpdateTime");
				date = temp == null ? date : new SimpleDateFormat("yyyy-MM-dd").format(temp);
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return date;
	}
	
	public static List<Stock> getStocks(String code){
		return getStocks(code, 30);
	}
	
	public static List<Stock> getStocks(String code, int number){
		List<Stock> stocks = new LinkedList<Stock>();
		try {
			int paramsSize = 1;
			String sql = "select * from t_daily where i_code = ? order by date desc ";
			if(number > 0){
				sql += " limit ?";
				paramsSize = 2;
			}
			Object[] params = new Object[]{};
			if(paramsSize == 1){
				params = new Object[]{code};
			} else if(paramsSize == 2){
				params = new Object[]{code, number};
			}
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			stocks = jdbcTemplate.query(sql, params, new StockRowHandle());
			Collections.reverse(stocks);
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return stocks;
	}
	
	public static List<Stock> getStocks(String code, String end, int number){
		List<Stock> stocks = new LinkedList<Stock>();
		try {
			String sql = "select * from t_daily where i_code = ? and date <= ? order by date desc ";
			int paramsSize = 2;
			if(number > 0){
				sql += " limit ?";
				paramsSize = 3;
			}
			Object[] params = new Object[]{};
			if(paramsSize == 2){
				params = new Object[]{code, end};
			} else if(paramsSize == 3){
				params = new Object[]{code, end, number};
			}
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			stocks = jdbcTemplate.query(sql, params, new StockRowHandle());
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return stocks;
	}
	
	
	public static List<Stock> queryHaveNotCalculateWesternIndex(){
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
	
	public static List<Stock> queryHaveNotCalculateMA30(){
		List<Stock> stocks = new LinkedList<Stock>();
		try {
			String sql = "select * from t_daily where ma30 is null limit 1000";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			stocks = jdbcTemplate.query(sql, new StockRowHandle());
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return stocks;
		
	}
	
	public static void updateBollAndWR(String id ,Boll boll, WR wr, double ma30){
		try {
			String sql = "update t_daily set boll_upper = ? , boll_lower = ?, boll_percentB = ? ,"
					+ " wr10 = ?, wr21= ? , wr42 = ? , ma30 = ?  where id = ?";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			jdbcTemplate.update(sql, new Object[] { 
					boll.getUpper(), boll.getLower(), boll.getPercentB(), wr.getWr10(), wr.getWr21(),
					wr.getWr42(), ma30, id });
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
	}
	
	public static void updateBollAndWR(List<Object[]> params){
		try {
			String sql = "update t_daily set boll_upper = ? , boll_lower = ?, boll_percentB = ? ,"
					+ " wr10 = ?, wr21= ? , wr42 = ? , ma30 = ?  where id = ? and boll_upper is null";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			jdbcTemplate.batchUpdate(sql, params);
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
	}
	
	public static void updateMa30(Object[][] params){
		String sql = "update t_daily set ma30 = ? where id = ?";
		try {
			List<Object[]> list = new LinkedList<Object[]>();
			for (Object[] objects : params) {
				list.add(objects);
			}
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			jdbcTemplate.batchUpdate(sql, list);
		} catch (Exception e) {
			LOG.error("", e);
		}
	}
	
	public static List<Stock> queryIndustyOneDay(String industry, String date){
		List<Stock> stocks = new LinkedList<Stock>();
		try {
			String sql = "select * from t_daily as d join (select code from t_code where industry = ?) as c "
					+ " on d.i_code = c.code "
					+ " where date = ?";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			stocks = jdbcTemplate.query(sql, new Object[]{industry, date}, new StockRowHandle());
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return stocks;
	}
	
	public static List<Stock> queryCategoryOneDay(String category, String date){
		List<Stock> stocks = new LinkedList<Stock>();
		try {
			String sql = "select * from t_daily as d "
					+ " join (select DISTINCT i_code from t_category where category = ?) as cat"
					+ " on d.i_code = cat.i_code "
					+ " where date = ?";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			stocks = jdbcTemplate.query(sql, new Object[]{category, date}, new StockRowHandle());
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return stocks;
	}
	
	public static List<Stock> getStocksBeforeSomeDay(String code, String date, int number){
		List<Stock> stocks = new LinkedList<Stock>();
		try {
			int paramsSize = 1;
			String sql = "select * from t_daily where i_code = ? and date <= ? order by date desc";
			if(number > 0){
				sql += " limit ?";
				paramsSize = 2;
			}
			Object[] params = new Object[]{};
			if(paramsSize == 1){
				params = new Object[]{code, date};
			} else if(paramsSize == 2){
				params = new Object[]{code, date, number};
			}
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			stocks = jdbcTemplate.query(sql, params, new StockRowHandle());
			Collections.reverse(stocks);
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return stocks;
	}
	
	public static void main(String[] args) {
//		System.out.println(queryIndustyOneDay("金融业 ", "2017-05-16"));
		List<Stock> stocks = queryCategoryOneDay("粤港澳自贸区", "2017-05-15");
		System.out.println(StatUtils.strength(stocks));
//		removeRepeatDaily();
	}
	
}
