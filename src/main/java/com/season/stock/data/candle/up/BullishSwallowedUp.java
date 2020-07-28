package com.season.stock.data.candle.up;

import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.down.BearishBelt;
import com.season.stock.data.utils.MathUtils;
import com.season.stock.data.utils.StockUtils;

/**
 * 
 * <PRE>
 * 涨吞
 * </PRE>
 * 
 * <B>项 目：</B>
 */
public class BullishSwallowedUp {

	public static boolean isBullishSwallowedUp(final List<Stock> stocks) {
		boolean result = false;
		if (stocks.size() > 2) {
			double[] avgs = StockUtils.getAvgPrice(stocks);
			boolean decrease = MathUtils.isDecrease(avgs);
			if (decrease) {
				Stock down = stocks.get(stocks.size() - 2);
				Stock swallowedUp = stocks.get(stocks.size() - 1);

				boolean bullishCatch = BearishBelt.isBearishBelt(down);
				if (swallowedUp.getOpen() <= down.getClose()
						&& swallowedUp.getClose() >= down.getOpen()) {
					result = bullishCatch && true;
				}
			}
		}
		return result;
	}
}
