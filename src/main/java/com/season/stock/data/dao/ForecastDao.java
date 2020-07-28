package com.season.stock.data.dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.season.stock.data.bean.HaveNotForecastResult;
import com.season.stock.data.bean.Stock;
import com.season.stock.data.bean.StockAnalyseResult;
import com.season.stock.data.utils.SpringTool;

public class ForecastDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(ForecastDao.class);
	
	public static int getCnt(){
		int cnt = 0;
		try {
			String sql = "select count(*) cnt from t_forecast";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
			if(!list.isEmpty()){
				long temp = (Long)list.get(0).get("cnt");
				cnt = (int)temp;
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return cnt;
	}
	
	public static void insert(StockAnalyseResult result){
		try {
			List<StockAnalyseResult> results = new LinkedList<StockAnalyseResult>();
			results.add(result);
			insert(results);
		} catch (Exception e) {
			LOG.error("", e);
		}
	}
	
	public static void insert(List<StockAnalyseResult> results){
		if(results.isEmpty()){
			return ;
		}
		String sql = "INSERT INTO t_forecast(code, candle, score, preVenus, venus,"
				+ " risingThreeMethods, bullishSwallowedUp, pregnantRise, pierce,"
				+ " counterAttack, bullishBelt, bullishCrossLine, forecastDate,"
				+ " checkDate, precentChange, _5DayPercent, md5,"
				+ " riseWindow,hammerLine,forwardThreeSoldiers,"
				+ " MA5MA10GoldenCross, MA5MA20GoldenCross, MA5MA30GoldenCross, passThroughMiddle,downThroughLower,"
				+ "wrMtop, drawdown, _5DayHighest, _5DayLowest, shock)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, MD5(concat(code, forecastDate))"
				+ " ,?,?,?,?,?,?,?,?,?, ?,?,?,?)";
		int count = 0;
		try {
			List<Object[]> params = new LinkedList<Object[]>();
			for (StockAnalyseResult result : results) {
				count++;
				Object[] param = new Object[]{
					result.getCode(),
					result.getCandle(),
					result.getScore(),
					toInt(result.isPreVenus()),
					toInt(result.isVenus()),
					
					toInt(result.isRisingThreeMethods()),
					toInt(result.isBullishSwallowedUp()),
					toInt(result.isPregnantRise()),
					toInt(result.isPierce()),
					toInt(result.isCounterAttack()),
					
					toInt(result.isBullishBelt()),
					toInt(result.isBullishCrossLine()),
					result.getForecastDate(),
					result.getCheckDate(),
					result.getPrecentChange(),
					
					result.get_5DayPercent(),
					toInt(result.isRiseWindow()),
					toInt(result.isHammerLine()),
					toInt(result.isForwardThreeSoldiers()),
					toInt(result.isMA5MA10GoldenCross()),
					
					toInt(result.isMA5MA20GoldenCross()),
					toInt(result.isMA5MA30GoldenCross()),
					toInt(result.isPassThroughMiddle()),
					toInt(result.isDownThroughLower()),
					toInt(result.isWrMTop()),
					
					result.getDrawdown(),
					result.get_5DayHighest(),
					result.get_5DayLowest(),
					toInt(result.isShock())
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
	
	private static int toInt(boolean bln){
		return bln ? 1 : 0 ;
	}
	
	public static double getCandleCount(String candleName){
		double cnt = 0;
		try {
			String sql = "select count(*) as cnt from t_forecast where " + candleName + " = 1";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
			if(!list.isEmpty()){
				long temp = (Long)list.get(0).get("cnt");
				cnt = temp;
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return cnt;
	}
	
	public static double getCandleUpCount(String candleName){
		return getCandleUpCount(candleName, 0);
	}
	
	public static double getCandleUpCount(String candleName, double expect){
		String sql = "select count(*) as cnt from t_forecast where " + candleName + " = 1 and precentChange >= " + expect;
		double cnt = 0;
		try {
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
			if(!list.isEmpty()){
				long temp = (Long)list.get(0).get("cnt");
				cnt = temp;
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return cnt;
	}
	
	public static double getCandle5DayUpCount(String candleName){
		return getCandle5DayUpCount(candleName, 0);
	}

	public static double getCandle5DayUpCount(String candleName, double expect){
		String sql = "select count(*) as cnt from t_forecast where " + candleName + " = 1 and _5DayPercent >= " + expect;
		double cnt = 0;
		try {
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
			if(!list.isEmpty()){
				long temp = (Long)list.get(0).get("cnt");
				cnt = temp;
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return cnt;
	}
	
	public static List<HaveNotForecastResult> queryHaveNotNextDayForecast(){
		List<HaveNotForecastResult> results = new LinkedList<HaveNotForecastResult>();
		try {
			String sql = "select id,code,forecastDate from t_forecast where precentChange = 0";
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Map<String, Object> map : list) {
				Integer id = map.get("id") == null ? -1 : (Integer)map.get("id");
				String code = map.get("code") == null ? "" : (String)map.get("code");
				Date forecastDate = map.get("forecastDate") == null ? new Date(System.currentTimeMillis()) : (Date)map.get("forecastDate");
				
				HaveNotForecastResult result = new HaveNotForecastResult();
				result.setId(id + "");
				result.setCode(code);
				result.setForecastDate(sdf.format(forecastDate));
				
				results.add(result);
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return results;
	}
	
	public static List<HaveNotForecastResult> queryHaveNot5DayForecast(){
		List<HaveNotForecastResult> results = new LinkedList<HaveNotForecastResult>();
		String sql = "select id,code,forecastDate from t_forecast where _5DayPercent = 0";
		try {
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Map<String, Object> map : list) {
				Integer id = map.get("id") == null ? -1 : (Integer)map.get("id");
				String code = map.get("code") == null ? "" : (String)map.get("code");
				Date forecastDate = map.get("forecastDate") == null ? new Date(System.currentTimeMillis()) : (Date)map.get("forecastDate");
				
				HaveNotForecastResult result = new HaveNotForecastResult();
				result.setId(id + "");
				result.setCode(code);
				result.setForecastDate(sdf.format(forecastDate));
				
				results.add(result);
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return results;
	}
	
	public static List<Stock> getStocks(HaveNotForecastResult param){
		String sql = "select * from t_daily where i_code = ? and date >= ? limit 30";
		List<Stock> stocks = new LinkedList<Stock>();
		try {
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, new Object[]{param.getCode(), param.getForecastDate()});
			while(rs.next()){
				Stock stock = new Stock(rs);
				stocks.add(stock);
			}
		} catch (InvalidResultSetAccessException e) {
			LOG.error("", e);
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return stocks;
	}
	
	public static List<Double> getNxDyFcstPer(String date){
		String sql = "select precentChange from t_forecast where forecastDate = ?";
		JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
		List<Double> pers = new LinkedList<Double>();
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new Object[]{date});
			for (Map<String,Object> map : list) {
				Double per = map.get("precentChange") == null ? 0 : (Double)map.get("precentChange");
				pers.add(per);
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return pers;
	}
	
	public static void updateNextDayForecast(String id , String date, double precent){
		String sql = "update t_forecast set precentChange = ?, checkDate = ? where id = ?";
		try {
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			jdbcTemplate.update(sql, new Object[]{precent, date, id});
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
	}
	
	public static void update5DayForecast(String id , double precent, double drawdown, double _5DayHighest, double _5DayLowest){
		String sql = "update t_forecast set _5DayPercent = ?, drawdown = ?, _5DayHighest= ?, _5DayLowest = ?  where id = ?";
		try {
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			jdbcTemplate.update(sql, new Object[]{precent, drawdown, _5DayHighest, _5DayLowest, id});
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
	}
	
	public static String getLastForecastDay(){
		String sql = "select MAX(forecastDate) as lastDay from t_forecast";
		String lastFcstDay = "";
		try {
			JdbcTemplate jdbcTemplate = SpringTool.getJdbcTemplate();
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
			if(!list.isEmpty()){
				Date temp = list.get(0).get("lastDay") == null ? null : (Date)list.get(0).get("lastDay");
				lastFcstDay = temp == null ? lastFcstDay : new SimpleDateFormat("yyyy-MM-dd").format(temp);
			}
		} catch (DataAccessException e) {
			LOG.error("", e);
		}
		return lastFcstDay;
	}
	
	public static void main(String[] args) {
		System.out.println(getLastForecastDay());
	}
}
