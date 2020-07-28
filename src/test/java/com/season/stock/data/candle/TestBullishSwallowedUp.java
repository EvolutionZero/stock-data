package com.season.stock.data.candle;

import java.util.List;

import org.junit.Test;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.up.BullishSwallowedUp;
import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.dao.DailyDao;

public class TestBullishSwallowedUp {
	
	@Test
	public void testBullishSwallowedUp(){
		List<String> codes = CodeDao.queryAllCodeOnExchange();
		for (String code : codes) {
			List<Stock> stocks = DailyDao.getStocks(code);
			if(stocks.isEmpty()){
				continue;
			}
			boolean bullishSwallowedUp = BullishSwallowedUp.isBullishSwallowedUp(stocks);
			if(bullishSwallowedUp){
				System.out.println("涨吞：" + code);
			}
		}
	}
}
