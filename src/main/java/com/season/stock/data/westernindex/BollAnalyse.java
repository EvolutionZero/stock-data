package com.season.stock.data.westernindex;

import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.utils.BollUtils;
import com.season.stock.data.utils.MathUtils;
import com.season.stock.data.utils.StockUtils;

public class BollAnalyse {

	private static final int SHORT_LEVEL = 9;
	
	public static boolean isPassThroughMiddle(final List<Stock> stocks){
		boolean isPassThroughMiddle = false;
		List<Stock> temp = StockUtils.getLastShortLevelStocks(stocks, SHORT_LEVEL);
		
		double[] avgPrice = StockUtils.getAvgPrice(temp);
		
		double[] upperLine = BollUtils.getUpperLine(temp);
		double[] middleLine = BollUtils.getMiddleLine(temp);
		
		if(MathUtils.isDrabIncrease(avgPrice) && MathUtils.isDrabIncrease(middleLine)
				&& MathUtils.isGoldenCross(avgPrice, middleLine)){
			boolean isAllBelowUpper = true;
			for (int i = 0; i < upperLine.length; i++) {
				if(avgPrice[i] > upperLine[i]){
					isAllBelowUpper = false;
					break;
				}
			}
			if(isAllBelowUpper){
				isPassThroughMiddle = true;
			}
		}
		return isPassThroughMiddle;
	}
	
	public static boolean isDownThroughLower(final List<Stock> stocks){
		boolean isDownThroughLower = false;
		List<Stock> temp = StockUtils.getLastShortLevelStocks(stocks, SHORT_LEVEL);
		
		double[] avgPrice = StockUtils.getAvgPrice(temp);
		
		double[] lowerLine = BollUtils.getLowerLine(temp);
		
		if(MathUtils.isDrabDecrease(avgPrice) && MathUtils.isDrabDecrease(lowerLine)
				&& MathUtils.isDeathCross(avgPrice, lowerLine)){
			if(temp.get(temp.size() - 1).getBollPercentB() < 0){
				isDownThroughLower = true;
			}
		}
		return isDownThroughLower;
	}
}
