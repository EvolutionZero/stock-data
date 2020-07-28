package com.season.stock.data.candle;

import java.util.List;

import org.junit.Test;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.up.CrossLine;
import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.dao.DailyDao;
import com.season.stock.data.utils.MathUtils;
import com.season.stock.data.utils.StockUtils;

public class TestCrossLine {
	
	@Test
	public void testCrossLine(){
		List<String> codes = CodeDao.queryAllCodeOnExchange();
		for (String code : codes) {
			List<Stock> stocks = DailyDao.getStocks(code,5);
			if(stocks.isEmpty()){
				continue;
			}
			Stock stock = stocks.get(stocks.size() - 1);
			boolean crossLine = CrossLine.isCrossLine(stock);
			double[] avgs = StockUtils.getAvgPrice(stocks);
			if(crossLine && MathUtils.isDecrease(avgs)){
				System.out.println("收十字线:" + code);
				boolean longLegCrossLine = CrossLine.isLongLegCrossLine(stock);
				if(longLegCrossLine){
					System.out.println("长腿十字线:" + code);
				}
				boolean tombstoneCrossLine = CrossLine.isTombstoneCrossLine(stock);
				if(tombstoneCrossLine){
					System.out.println("墓碑十字线:" + code);
				}
				boolean dragonflyCrossLine = CrossLine.isDragonflyCrossLine(stock);
				if(dragonflyCrossLine){
					System.out.println("蜻蜓十字线:" + code);
				}
			}
		}
	}
	
}
