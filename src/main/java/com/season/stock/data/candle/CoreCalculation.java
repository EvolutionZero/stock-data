package com.season.stock.data.candle;

import com.season.stock.data.bean.Stock;

/**
 * 
 * <PRE>
 * 核心计算
 * </PRE>
 * <B>项    目：</B> 
 * @since     jdk版本：jdk1.6
 */
public class CoreCalculation {

	public static double entitySize(Stock stock){
		double open = Double.valueOf(stock.getOpen());
		double close = Double.valueOf(stock.getClose());
		double high = Double.valueOf(stock.getHigh());
		double low = Double.valueOf(stock.getLow());
		
		double result = 0;
		if(high - low != 0){
			result = Math.abs(open - close) / (high - low);
		}
		return result;
	}
	
	public static double upperShadowSize(Stock stock){
		double open = Double.valueOf(stock.getOpen());
		double close = Double.valueOf(stock.getClose());
		double high = Double.valueOf(stock.getHigh());
		double low = Double.valueOf(stock.getLow());
		
		double result = 0;
		if(high - low != 0){
			result = (high - Math.max(open, close)) / (high - low);
		}
		return result;
	}
	
	public static double lowShadowSize(Stock stock){
		double open = Double.valueOf(stock.getOpen());
		double close = Double.valueOf(stock.getClose());
		double high = Double.valueOf(stock.getHigh());
		double low = Double.valueOf(stock.getLow());
		
		double result = 0;
		if(high - low != 0){
			result = (Math.min(open, close) - low) / (high - low);
		}
		return result;
	}
	
	public static double shadowSize(Stock stock){
		double high = Double.valueOf(stock.getHigh());
		double low = Double.valueOf(stock.getLow());
		
		double result = 0;
		if(high != 0){
			result = 1 - (low / high);
		}
		return result;
	}
}
