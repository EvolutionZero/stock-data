package com.season.stock.data.candle.up;

import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.CoreCalculation;
import com.season.stock.data.utils.MathUtils;
import com.season.stock.data.utils.StockUtils;

/**
 * 
 * <PRE>
 * 十字线
 * </PRE>
 * <B>项    目：</B> 
 * @since     jdk版本：jdk1.6
 */
public class CrossLine {
	
	private static final int SHORT_LEVEL = 5;
	
	public static boolean isBullishCrossLine(final List<Stock> stocks){
		boolean result = false;
		double[] avgs = StockUtils.getAvgPrice(stocks);
		boolean decrease = MathUtils.isDecrease(avgs);
		
		List<Stock> lastShortLevelStocks = StockUtils.getLastShortLevelStocks(stocks, SHORT_LEVEL);
		double[] slAvgs = StockUtils.getAvgPrice(lastShortLevelStocks);
		boolean slDecrease = MathUtils.isDrabDecrease(slAvgs);
		if(decrease && slDecrease){
			Stock stock = stocks.get(stocks.size() - 1);
			if(isDragonflyCrossLine(stock)){
				result = true;
			}
		}
		return result;
	}
	
	
	public static boolean isCrossLine(Stock stock){
		boolean result = false;
		double entitySize = CoreCalculation.entitySize(stock);
		if(entitySize <= 0.05){
			result = true;
		}
		return result;
	}
	
	public static boolean isLongLegCrossLine(Stock stock){
		boolean result = false;
		if(isCrossLine(stock)){
			double lowShadowSize = CoreCalculation.lowShadowSize(stock);
			double upperShadowSize = CoreCalculation.upperShadowSize(stock);
			if(lowShadowSize >= 0.4 && upperShadowSize >= 0.4){
				result = true;
			}
		}
		return result;
	}
	
	public static boolean isTombstoneCrossLine(Stock stock){
		boolean result = false;
		if(isCrossLine(stock)){
			double upperShadowSize = CoreCalculation.upperShadowSize(stock);
			if(upperShadowSize >= 0.8){
				result = true;
			}
		}
		return result;
	}
	
	public static boolean isDragonflyCrossLine(Stock stock){
		boolean result = false;
		if(isCrossLine(stock)){
			double lowShadowSize = CoreCalculation.lowShadowSize(stock);
			if(lowShadowSize >= 0.9){
				result = true;
			}
		}
		return result;
	}
}
