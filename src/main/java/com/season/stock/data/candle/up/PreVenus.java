package com.season.stock.data.candle.up;

import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.down.BearishBelt;
import com.season.stock.data.utils.MathUtils;
import com.season.stock.data.utils.StockUtils;

/**
 * 
 * <PRE>
 * 启明星前态
 * </PRE>
 * 
 * <B>项 目：</B>
 */
public class PreVenus {

	public static boolean isPreVenus(final List<Stock> stocks) {
		boolean result = false;
		if (stocks.size() > 2) {
			Stock down = stocks.get(stocks.size() - 2);
			Stock cross = stocks.get(stocks.size() - 1);
			double[] avgs = StockUtils.getAvgPrice(stocks);
			boolean decrease = MathUtils.isDecrease(avgs);
			if (decrease) {
				boolean bearishBelt = BearishBelt.isBearishBelt(down);
				boolean crossLine = CrossLine.isCrossLine(cross);
				double max = Math.max(cross.getOpen(), cross.getClose());
				if (max < down.getClose()) {
					result = bearishBelt && crossLine;

				}
			}
		}
		return result;
	}
}
