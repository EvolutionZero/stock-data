package com.season.stock.data.candle;

import java.util.List;

import org.junit.Test;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.up.BullishBelt;
import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.dao.DailyDao;
import com.season.stock.data.utils.MathUtils;

public class TestBullishBelt {
	
	@Test
	public void testBullishBelt(){
		List<String> codes = CodeDao.queryAllCodeOnExchange();
		for (String code : codes) {
			List<Stock> stocks = DailyDao.getStocks(code);
			if(stocks.isEmpty()){
				continue;
			}
			
			double[] line = getLine(stocks, 5);
			double dropStrength = MathUtils.dropStrength(line);
			Stock stock = stocks.get(stocks.size() - 1);
			boolean bullishBelt = BullishBelt.isBullishBelt(stocks);
			if(bullishBelt && dropStrength > 0.75){
				System.out.println(code + "看涨捉腰带线");
				MathUtils.dropStrength(line);
			}
		}
	}
	
	private double[] getLine(List<Stock> stocks, int number){
		double[] line = new double[stocks.size()];
		for (int i = 0; i < stocks.size(); i++) {
			Stock stock = stocks.get(i);
			double middle = (Double.valueOf(stock.getOpen()) + Double.valueOf(stock.getClose())) / 2 ;
			line[i] = middle;
		}
		return line;
	}
	
}
