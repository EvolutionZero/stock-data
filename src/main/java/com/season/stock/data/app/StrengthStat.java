package com.season.stock.data.app;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.bean.StrengthIdx;
import com.season.stock.data.dao.CategoryDao;
import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.dao.DailyDao;
import com.season.stock.data.dao.IndustryDao;
import com.season.stock.data.utils.DayUtils;
import com.season.stock.data.utils.LogUtils;
import com.season.stock.data.utils.StatUtils;

public class StrengthStat {
	
	private static final Logger LOG = LoggerFactory.getLogger(StrengthStat.class);
	
	public static void main(String[] args) {
		LogUtils.loadLogBackConfig();
		execute();
		
	}
	
	public static void execute() {
		LOG.info("开始对行业概念评分！");
		List<String> allTradeDay = DailyDao.getTradeDayDesc(30);
		Collections.reverse(allTradeDay);
		
		List<String> allIndustry = CodeDao.getAllIndustry();
		
		List<String> industryTradeDay = allTradeDay;
		String industryLastStatDate = IndustryDao.getLastStatDate();
		if(!"".equals(industryLastStatDate)){
			industryTradeDay = DayUtils.after(industryLastStatDate, allTradeDay);
		}
		for (String tradeDay : industryTradeDay) {
			LinkedList<StrengthIdx> idxs = new LinkedList<StrengthIdx>();
			for (String industry : allIndustry) {
				LOG.info("对[{}]的[{}]行业进行评分！", tradeDay, industry);
				List<Stock> stocks = DailyDao.queryIndustyOneDay(industry, tradeDay);
				StrengthIdx strength = StatUtils.strength(stocks);
				strength.setName(industry);
				strength.setDate(tradeDay);

				idxs.add(strength);
			}
			IndustryDao.insertIdx(idxs);
		}
		
		List<String> allCategory = CategoryDao.getAllCategory();
		List<String> categoryTradeDay = allTradeDay;
		String categoryLastStatDate = CategoryDao.getLastStatDate();
		if(!"".equals(categoryLastStatDate)){
			categoryTradeDay = DayUtils.after(categoryLastStatDate, allTradeDay);
		}
		for (String tradeDay : categoryTradeDay) {
			LinkedList<StrengthIdx> idxs = new LinkedList<StrengthIdx>();
			for (String category : allCategory) {
				LOG.info("对[{}]的[{}]概念进行评分！", tradeDay, category);
				List<Stock> stocks = DailyDao.queryCategoryOneDay(category, tradeDay);
				StrengthIdx strength = StatUtils.strength(stocks);
				strength.setName(category);
				strength.setDate(tradeDay);

				idxs.add(strength);
			}
			CategoryDao.insertIdx(idxs);
		}
		LOG.info("结束对行业概念评分！");
	}
	
}
