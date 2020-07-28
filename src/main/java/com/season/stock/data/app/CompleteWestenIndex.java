package com.season.stock.data.app;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.season.stock.data.bean.Boll;
import com.season.stock.data.bean.Stock;
import com.season.stock.data.bean.WR;
import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.dao.DailyDao;
import com.season.stock.data.utils.BollUtils;
import com.season.stock.data.utils.LogUtils;
import com.season.stock.data.utils.MAUtils;
import com.season.stock.data.utils.StockUtils;
import com.season.stock.data.utils.WRUtils;

public class CompleteWestenIndex {
	
	private static final Logger LOG = LoggerFactory.getLogger(CompleteWestenIndex.class);
	
	public static void fastCalAll(){
		Date start = new Date();
		LOG.info("更新西方指标开始！");
		List<String> codes = CodeDao.queryAllCodeOnExchange();
		for (String code : codes) {
			LOG.info("更新[{}]的西方指标！", code);
			List<Stock> stocks = DailyDao.getStocks(code, -1);
			List<Object[]> params = new LinkedList<Object[]>();
			while(stocks.size() > 0){
				List<Stock> work = StockUtils.getLastStocks(stocks, 42);
				if(work.get(work.size() - 1).getBollUpper() == 0){
					double ma30 = MAUtils.getMAx(work, 30);
					Boll boll = BollUtils.getBoll(work);
					WR wr = WRUtils.getWR(work);
					
					Object[] param = new Object[] { 
							boll.getUpper(), boll.getLower(), boll.getPercentB(), wr.getWr10(), wr.getWr21(),
							wr.getWr42(), ma30, work.get(work.size() - 1).getId() };
					params.add(param);
					
				}
				
				// 向前推
				stocks.remove(stocks.size() - 1);
			}
			DailyDao.updateBollAndWR(params);
			LOG.info("完成[{}]的西方指标！", code);
		}
		Date end = new Date();
		LOG.info("更新西方指标完成！耗时[{}]秒", (end.getTime() - start.getTime()) / 1000);
	}
	public static void done(){
		Date start = new Date();
		int batch = 1;
		LOG.info("更新西方指标开始！");
		while(true){
			LOG.info("执行第[{}]批次更新！", batch);
			List<Stock> haveNotCalculateWesternIndex = DailyDao.queryHaveNotCalculateWesternIndex();
			if(haveNotCalculateWesternIndex.isEmpty()){
				break;
			}
			for (Stock stock : haveNotCalculateWesternIndex) {
				List<Stock> stocks = DailyDao.getStocks(stock.getCode(), stock.getDate(), 42);
				double ma30 = MAUtils.getMAx(stocks, 30);
				Boll boll = BollUtils.getBoll(stocks);
				WR wr = WRUtils.getWR(stocks);
				DailyDao.updateBollAndWR(stock.getId(), boll, wr, ma30);
			}
			batch++;
		}
		Date end = new Date();
		LOG.info("更新西方指标完成！耗时[{}]秒", (end.getTime() - start.getTime()) / 1000);
	}
	
	public static void main(String[] args) {
		LogUtils.loadLogBackConfig();
		CompleteWestenIndex.done();
	}
}
