package com.season.stock.data.candle.up;

import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.CoreCalculation;
import com.season.stock.data.candle.down.BearishBelt;
import com.season.stock.data.utils.MathUtils;
import com.season.stock.data.utils.StockUtils;

/**
 * 
 * <PRE>
 * 孕涨
 * </PRE>
 */
public class PregnantRise {

	public static boolean isPregnantRise(final List<Stock> stocks) {
		boolean result = false;
		if (stocks.size() > 2) {
			double[] avgs = StockUtils.getAvgPrice(stocks);
			boolean decrease = MathUtils.isDecrease(avgs);
			if (decrease) {
				Stock down = stocks.get(stocks.size() - 2);
				Stock entry = stocks.get(stocks.size() - 1);

				boolean bearishBelt = BearishBelt.isBearishBelt(down);
				boolean crossLine = CrossLine.isCrossLine(entry);
				if (bearishBelt && crossLine) {
					double crosslineLowest = down.getClose() + (down.getOpen() - down.getClose()) * 0.45;
					
					double max = Math.max(entry.getOpen(), entry.getClose());
					double min = Math.min(entry.getOpen(), entry.getClose());
					if (min >= crosslineLowest && max <= down.getOpen() && min >= down.getClose()) {
						result = true;

					}

				} else if (bearishBelt && entry.getOpen() < entry.getClose()) {
					double entryLowest = down.getClose() + (down.getOpen() - down.getClose()) * 0.45;
					double min = Math.min(entry.getOpen(), entry.getClose());
					double entitySize = CoreCalculation.entitySize(entry);
					if (min >= entryLowest && entitySize <= 0.3 && entry.getOpen() >= down.getClose()
							&& entry.getClose() <= down.getOpen()) {
						result = true;
					}
				}
			}
		}
		return result;
	}
}
