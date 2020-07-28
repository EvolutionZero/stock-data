package com.season.stock.data.candle.up;

import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.down.BearishBelt;
import com.season.stock.data.utils.MathUtils;
import com.season.stock.data.utils.StockUtils;

/**
 * 
 * <PRE>
 * 反击线
 * </PRE>
 * 
 * <B>项 目：</B>
 */
public class Counterattack {

	public static boolean isCounterattack(final List<Stock> stocks) {
		boolean result = false;
		if (stocks.size() > 2) {
			Stock down = stocks.get(stocks.size() - 2);
			Stock counterattack = stocks.get(stocks.size() - 1);
			double[] avgs = StockUtils.getAvgPrice(stocks);
			boolean decrease = MathUtils.isDecrease(avgs);
			if (decrease) {
				boolean bullishCatch = BearishBelt.isBearishBelt(down);
				if (counterattack.getOpen() < down.getClose()
						&& counterattack.getClose() >= down.getOpen()
						&& counterattack.getClose() < down.getOpen() * 1.01) {
					result = bullishCatch && true;
				}
			}
		}
		return result;
	}

}
