package com.season.stock.data.candle;

import java.util.List;

import org.junit.Test;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.up.Counterattack;
import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.dao.DailyDao;

public class TestCounterattack {
	
	@Test
	public void testCounterattack(){
		List<String> codes = CodeDao.queryAllCodeOnExchange();
		for (String code : codes) {
			List<Stock> stocks = DailyDao.getStocks(code);
			if(stocks.isEmpty()){
				continue;
			}
			boolean counterAttack = Counterattack.isCounterattack(stocks);
			if(counterAttack){
				System.out.println("反击：" + code);
			}
		}
	}
}
