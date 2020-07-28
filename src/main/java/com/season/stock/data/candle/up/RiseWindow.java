package com.season.stock.data.candle.up;

import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.utils.MathUtils;
import com.season.stock.data.utils.StockUtils;

public class RiseWindow {

	private static final int SHORT_LEVEL = 5;
	
	public static boolean isWinodw(final List<Stock> stocks){
		boolean isWindow = false;
		if(stocks.size() > 1){
			double[] avgs = StockUtils.getAvgPrice(stocks);
			boolean decrease = MathUtils.isDecrease(avgs);
			
			List<Stock> lastShortLevelStocks = StockUtils.getLastShortLevelStocks(stocks, SHORT_LEVEL);
			double[] slAvgs = StockUtils.getAvgPrice(lastShortLevelStocks);
			boolean slDecrease = MathUtils.isDecrease(slAvgs);
			if(decrease && slDecrease){
				Stock before = stocks.get(stocks.size() - 2);
				Stock cur = stocks.get(stocks.size() - 1);
				if(before.getClose() > before.getOpen() && cur.getClose() > before.getOpen() && 
						cur.getLow() > before.getHigh()){
					double rate = (cur.getLow() - before.getHigh()) * 1.0 /  before.getHigh();
					//  跳空比率大于0.5% -> 77%  , 1% -> 82.72% (5日内超2%收益)
					if(rate >= 0.01){
						isWindow = true;
					}
				}
			}
		}
		return isWindow;
	}
	
}
