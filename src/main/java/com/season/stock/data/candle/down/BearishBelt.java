package com.season.stock.data.candle.down;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.CoreCalculation;

/**
 * 
 * <PRE>
 * 看跌捉腰带线
 * </PRE>
 * <B>项    目：</B> 

 * @since     jdk版本：jdk1.6
 */
public class BearishBelt {
	
	/**
	 * 看跌捉腰带线
	 * @param stock
	 * @return
	 */
	public static boolean isBearishBelt(Stock stock){
		boolean result = false;
		double entitySize = CoreCalculation.entitySize(stock);
		double upperShadowSize = CoreCalculation.upperShadowSize(stock);
		double open = Double.valueOf(stock.getOpen());
		double close = Double.valueOf(stock.getClose());
		if(close < open && upperShadowSize == 0 && entitySize > 0.6 && stock.getPrecentChange() <= -3){
			result = true;
		}
		return result;
	}
}
