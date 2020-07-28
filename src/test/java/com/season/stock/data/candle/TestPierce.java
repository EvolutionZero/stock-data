package com.season.stock.data.candle;

import java.util.List;

import org.junit.Test;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.up.Pierce;
import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.dao.DailyDao;

public class TestPierce {
	
	@Test
	public void testPierce(){
		List<String> codes = CodeDao.queryAllCodeOnExchange();
		for (String code : codes) {
			List<Stock> stocks = DailyDao.getStocks(code);
			if(stocks.isEmpty()){
				continue;
			}
			boolean pierce = Pierce.isPierce(stocks);
			if(pierce){
				System.out.println("刺透：" + code);
			}
		}
	}
}
