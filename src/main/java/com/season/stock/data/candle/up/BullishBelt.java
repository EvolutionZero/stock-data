package com.season.stock.data.candle.up;

import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.CoreCalculation;
import com.season.stock.data.utils.MathUtils;
import com.season.stock.data.utils.StockUtils;

/**
 * 
 * <PRE>
 * 看涨捉腰带线
 * </PRE>
 * <B>项    目：</B> 
 * @since     jdk版本：jdk1.6
 */
public class BullishBelt {
	
	private static final int SHORT_LEVEL = 5;
	
	public static boolean isBullishBelt(final List<Stock> stocks){
		boolean result = false;
		if(!stocks.isEmpty()){
			double[] avgs = StockUtils.getAvgPrice(stocks);
			boolean decrease = MathUtils.isDecrease(avgs);
			
			List<Stock> lastShortLevelStocks = StockUtils.getLastShortLevelStocks(stocks, SHORT_LEVEL);
			double[] slAvgs = StockUtils.getAvgPrice(lastShortLevelStocks);
			boolean slDecrease = MathUtils.isDrabDecrease(slAvgs);
			if(decrease && slDecrease){
				Stock stock = lastShortLevelStocks.get(lastShortLevelStocks.size() - 1);
				double entitySize = CoreCalculation.entitySize(stock);
				double lowShadowSize = CoreCalculation.lowShadowSize(stock);
				double open = Double.valueOf(stock.getOpen());
				double close = Double.valueOf(stock.getClose());
				if(close > open && lowShadowSize == 0 && entitySize > 0.97 && stock.getPrecentChange() >= 3){
					result = true;
				}
				
			}
		}
		return result;
	}
	
}
