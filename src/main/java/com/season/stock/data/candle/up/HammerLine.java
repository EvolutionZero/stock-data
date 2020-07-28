package com.season.stock.data.candle.up;

import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.CoreCalculation;
import com.season.stock.data.utils.MathUtils;
import com.season.stock.data.utils.StockUtils;

public class HammerLine {
	
	private static final int SHORT_LEVEL = 5;
	
	public static boolean isHammerLine(final List<Stock> stocks){
		boolean isHammerLine = false;
		if(stocks.size() > SHORT_LEVEL){
			double[] avgs = StockUtils.getAvgPrice(stocks);
			boolean decrease = MathUtils.isDecrease(avgs);
			
			List<Stock> lastShortLevelStocks = StockUtils.getLastShortLevelStocks(stocks, SHORT_LEVEL);
			double[] slAvgs = StockUtils.getAvgPrice(lastShortLevelStocks);
			boolean slDecrease = MathUtils.isDrabDecrease(slAvgs);
			if(decrease && slDecrease){ 
				Stock stock = stocks.get(stocks.size() - 1);
				double entitySize = CoreCalculation.entitySize(stock);
				double lowShadowSize = CoreCalculation.lowShadowSize(stock);
				double upperShadowSize = CoreCalculation.upperShadowSize(stock);
				// 收阳线，上影线占比小于1%，下影线是实体的2倍以上
				if(stock.getClose() > stock.getOpen() && upperShadowSize <= 0.01 && lowShadowSize >= entitySize * 2 ){
					double top = slAvgs[0];
					double low = slAvgs[SHORT_LEVEL - 1 ];
					double rate = Math.abs(low - top) / top;
					// 最近5个交易日下降超过3个点
					if(rate >= 0.03){
						double maxValue = MathUtils.getMaxValue(avgs);
						double totalRate = Math.abs(low - maxValue) / maxValue;
						// 15交易日总体下降超过10个点
						if(totalRate >= 0.1){
							isHammerLine = true;
							
						}
					}
				}
				
			}
		}
		return isHammerLine;
	}
	
}
