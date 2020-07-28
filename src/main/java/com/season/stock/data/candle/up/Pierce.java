package com.season.stock.data.candle.up;

import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.down.BearishBelt;
import com.season.stock.data.utils.MathUtils;
import com.season.stock.data.utils.StockUtils;

/**
 * 
 * <PRE>
 * 穿刺
 * </PRE>
 */
public class Pierce {

	private static final int SHORT_LEVEL = 5;
	
	public static boolean isPierce(final List<Stock> stocks) {
		boolean result = false;
		if (stocks.size() > 2) {
			Stock down = stocks.get(stocks.size() - 2);
			Stock pierce = stocks.get(stocks.size() - 1);
			List<Stock> lastShortLevelStocks = StockUtils.getLastShortLevelStocks(stocks, SHORT_LEVEL);
			double[] slAvgs = StockUtils.getAvgPrice(lastShortLevelStocks);
			boolean slDecrease = MathUtils.isDrabDecrease(slAvgs);
			if (slDecrease) {
				boolean bullishCatch = BearishBelt.isBearishBelt(down);
				double downAvg = (down.getOpen() + down.getClose()) / 2;
				if (pierce.getOpen() <= down.getClose()
						&& pierce.getClose() >= downAvg
						&& pierce.getClose() <= down.getOpen() 
						&& pierce.getPrecentChange() >= 3) {
					result = bullishCatch && true;
				}
			}
		}
		return result;
	}

}
