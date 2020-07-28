package com.season.stock.data.app;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.dao.DailyDao;
import com.season.stock.data.utils.LogUtils;
import com.season.stock.data.utils.MAUtils;

public class CompleteMAx {
	
	private static final Logger LOG = LoggerFactory.getLogger(CompleteMAx.class);
	
	public static void main(String[] args) {
		LogUtils.loadLogBackConfig();
		CompleteMAx.done();
	}
	
	public static void done(){
		Date start = new Date();
		int batch = 1;
		LOG.info("更新MA30指标开始！");
		while(true){
			LOG.info("执行第[{}]批次更新！", batch);
			List<Stock> haveNotCalculateMa30 = DailyDao.queryHaveNotCalculateMA30();
			if(haveNotCalculateMa30.isEmpty()){
				break;
			}
			Object[][] params = new Object[haveNotCalculateMa30.size()][];
			int idx = 0;
			for (Stock stock : haveNotCalculateMa30) {
				List<Stock> stocks = DailyDao.getStocks(stock.getCode(), stock.getDate(), 42);
				double ma30 = MAUtils.getMAx(stocks, 30);
				params[idx] = new Object[]{ma30, stock.getId()};
				
				idx++;
			}
			DailyDao.updateMa30(params);
			batch++;
		}
		Date end = new Date();
		LOG.info("更新MA30完成！耗时[{}]秒", (end.getTime() - start.getTime()) / 1000);
	}
	
}
