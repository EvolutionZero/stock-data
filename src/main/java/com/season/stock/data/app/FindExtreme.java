package com.season.stock.data.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.season.stock.data.bean.Extreme;
import com.season.stock.data.bean.Stock;
import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.dao.DailyDao;
import com.season.stock.data.dao.ExtremeDao;
import com.season.stock.data.utils.LogUtils;

public class FindExtreme {

	private static final Logger LOG = LoggerFactory.getLogger(FindExtreme.class);
	
	public static void main(String[] args) {
		LogUtils.loadLogBackConfig();
		findHistoryExtreme();
	}

	private static void findHistoryExtreme() {
		findAreaExtreme(Integer.MAX_VALUE);
	}

	public static void findAreaExtreme(int lastDay) {
		LOG.info("开始寻找极值！");
		List<String> codes = CodeDao.queryAllCodeOnExchange();
		for (String code : codes) {
			LOG.info("寻找{}的极值！", code);
			long start = System.currentTimeMillis();
			List<Stock> stocks = DailyDao.getStocks(code, lastDay);
			
			List<Extreme> shortExtremes = findExtremes(stocks);
			List<Extreme> midExtremes = findExtremes(shortExtremes, Extreme.MIDDLE);
			List<Extreme> longExtremes = findExtremes(midExtremes, Extreme.LONG);
			
			String shortLastDay = ExtremeDao.getLastDate(code, Extreme.SHORT);
			shortExtremes = "".equals(shortLastDay) ? shortExtremes : after(shortLastDay, shortExtremes);
			
			String middleLastDay = ExtremeDao.getLastDate(code, Extreme.MIDDLE);
			midExtremes = "".equals(middleLastDay) ? midExtremes : after(middleLastDay, midExtremes);
			
			String longLastDay = ExtremeDao.getLastDate(code, Extreme.MIDDLE);
			longExtremes = "".equals(longLastDay) ? longExtremes : after(longLastDay, longExtremes);
			
			ExtremeDao.save(shortExtremes);
			ExtremeDao.save(midExtremes);
			ExtremeDao.save(longExtremes);
			long end = System.currentTimeMillis();
			LOG.info("耗时 {}毫秒！", end - start);
		}
		LOG.info("结束寻找极值！");
	}
	
	public static List<Extreme> after(String standard, List<Extreme> extremes){
		List<Extreme> result = new LinkedList<Extreme>();
		if(!"".equals(standard)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Extreme extreme : extremes) {
				try {
					if(sdf.parse(extreme.getDate()).after(sdf.parse(standard))){
						result.add(extreme);
					}
				} catch (ParseException e) {
					LOG.error("", e);
				}
			}
		}
		return result;
	}
	
	private static List<Extreme> findExtremes(List<Stock> stocks){
		List<Extreme> extremes = new LinkedList<Extreme>();
		if(stocks.size() >= 3){
			int len = stocks.size() - 1;
			for (int i = 1; i < len; i++) {
				Stock left = stocks.get(i - 1);
				Stock mid = stocks.get(i);
				Stock right = stocks.get(i + 1);
				
				Extreme extreme = genExtreme(left, mid, right);
				if(extreme != null){
					extremes.add(extreme);
				}
			}
			
		}
		return extremes;
	}
	
	private static Extreme genExtreme(Stock left, Stock mid, Stock right){
		Extreme extreme = null;
		if(mid.getHigh() > left.getHigh() && mid.getHigh() > right.getHigh()){
			extreme = new Extreme();
			extreme.setCode(mid.getCode());
			extreme.setDate(mid.getDate());
			extreme.setValue(mid.getHigh());
			extreme.setLevel(Extreme.SHORT);
			extreme.setType(Extreme.TYPE_HIGHEST);
			
		} else if(mid.getLow() < left.getLow() && mid.getLow() < right.getLow()){
			extreme = new Extreme();
			extreme.setCode(mid.getCode());
			extreme.setDate(mid.getDate());
			extreme.setValue(mid.getLow());
			extreme.setLevel(Extreme.SHORT);
			extreme.setType(Extreme.TYPE_LOWEST);
		}
		return extreme;
	}
	
	private static List<Extreme> findExtremes(List<Extreme> extremes, int level){
		List<Extreme> result = new LinkedList<Extreme>();
		if(extremes.size() >= 3){
			int len = extremes.size() - 1;
			for (int i = 1; i < len; i++) {
				Extreme left = extremes.get(i - 1);
				Extreme mid = extremes.get(i);
				Extreme right = extremes.get(i + 1);
				
				Extreme extreme = genExtreme(left, mid, right, level);
				if(extreme != null){
					result.add(extreme);
				}
			}
			
		}
		return result;
	}
	
	private static Extreme genExtreme(Extreme left, Extreme mid, Extreme right, int level){
		Extreme extreme = null;
		if(mid.getValue() > left.getValue() && mid.getValue() > right.getValue()){
			extreme = new Extreme();
			extreme.setCode(mid.getCode());
			extreme.setDate(mid.getDate());
			extreme.setValue(mid.getValue());
			extreme.setLevel(level);
			extreme.setType(Extreme.TYPE_HIGHEST);
			
		} else if(mid.getValue() < left.getValue() && mid.getValue() < right.getValue()){
			extreme = new Extreme();
			extreme.setCode(mid.getCode());
			extreme.setDate(mid.getDate());
			extreme.setValue(mid.getValue());
			extreme.setLevel(level);
			extreme.setType(Extreme.TYPE_LOWEST);
		}
		return extreme;
	}
}
