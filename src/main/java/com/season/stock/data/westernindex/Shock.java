package com.season.stock.data.westernindex;

import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.utils.MathUtils;
import com.season.stock.data.utils.StockUtils;

public class Shock {

	private static final int SHORT_LEVEL = 30;
	
	public static boolean isShock(final List<Stock> stocks){
		boolean isShock = false;
		List<Stock> temp = StockUtils.getLastShortLevelStocks(stocks, SHORT_LEVEL);
		double[] closeLine = StockUtils.getCloseLine(temp);
		int[] reverseLine = MathUtils.reverseLine(closeLine);
		double maxValue = StockUtils.getMaxValue(temp);
		double minValue = StockUtils.getMinValue(temp);
		double percentChange = (maxValue - minValue) / minValue * 100;
		
		int cnt = 0;
		for (int status : reverseLine) {
			if(status != 0){
				cnt++;
			}
		}
		if(percentChange <= 10 && cnt >= (int)(temp.size() * 0.3)){
			isShock = true;
		}
		return isShock;
	}
	
}
