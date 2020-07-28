package com.season.stock.data.candle;

import java.util.List;

import org.junit.Test;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.up.Venus;
import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.dao.DailyDao;

public class TestVenus {
	
	@Test
	public void testVenus(){
		List<String> codes = CodeDao.queryAllCodeOnExchange();
		for (String code : codes) {
			List<Stock> stocks = DailyDao.getStocks(code);
			if(stocks.isEmpty()){
				continue;
			}
			boolean venus = Venus.isVenus(stocks);
			if(venus){
				System.out.println("启明星：" + code);
			}
		}
	}
}
