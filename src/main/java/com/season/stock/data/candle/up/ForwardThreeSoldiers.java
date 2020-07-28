package com.season.stock.data.candle.up;

import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.CoreCalculation;
import com.season.stock.data.utils.MathUtils;
import com.season.stock.data.utils.StockUtils;

public class ForwardThreeSoldiers {
	
	private static final int SHORT_LEVEL = 5;
	
	public static boolean isForwardThreeSoldiers(final List<Stock> stocks){
		boolean isForwardThreeSoldiers = false;
		if(stocks.size() > 3){
			List<Stock> lastShortLevelStocks = StockUtils.getLastShortLevelStocks(stocks, SHORT_LEVEL);
			double[] slAvgs = StockUtils.getAvgPrice(lastShortLevelStocks);
			boolean increase = MathUtils.isDrabIncrease(slAvgs);
			if(increase){
				Stock one = stocks.get(stocks.size() - 3);
				Stock two = stocks.get(stocks.size() - 2);
				Stock three = stocks.get(stocks.size() - 1);
				if(one.getClose() > one.getOpen() && two.getClose() > two.getOpen() 
						&& three.getClose() > three.getOpen()){
					if(two.getOpen() < one.getClose() && two.getClose() > one.getClose()){
						if(three.getOpen() < two.getClose() && three.getClose() > two.getClose()){
							double onelowShadowSize = CoreCalculation.lowShadowSize(one);
							double twolowShadowSize = CoreCalculation.lowShadowSize(two);
							double threelowShadowSize = CoreCalculation.lowShadowSize(three);
							if(onelowShadowSize + twolowShadowSize + threelowShadowSize <= 0.1){
								if(one.getPrecentChange() >= 0.5 && two.getPrecentChange() >= 0.5
										&& three.getPrecentChange() >= 0.5){
									isForwardThreeSoldiers = true;
									
								}
								
							}
						}
					}
				}
			}
		}
		return isForwardThreeSoldiers;
	}
}
