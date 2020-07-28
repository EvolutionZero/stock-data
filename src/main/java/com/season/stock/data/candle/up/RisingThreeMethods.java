package com.season.stock.data.candle.up;

import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.utils.StockUtils;

/**
 * 
 * <PRE>
 * 上升三法
 * </PRE>
 * 
 * <B>项 目：</B>
 */
public class RisingThreeMethods {

	public static boolean isRisingThreeMethods(final List<Stock> stocks) {
		boolean result = false;
		if (stocks.size() > 5) {
			Stock yang = stocks.get(stocks.size() - 5);
			Stock d1 = stocks.get(stocks.size() - 4);
			Stock d2 = stocks.get(stocks.size() - 3);
			Stock d3 = stocks.get(stocks.size() - 2);
			Stock bigYang = stocks.get(stocks.size() - 1);

			boolean bullishBelt = BullishBelt.isBullishBelt(StockUtils
					.convertToList(yang));
			if (bullishBelt) {
				if (d1.getClose() < d1.getOpen() 
						|| (CrossLine.isCrossLine(d1) && Math.max(d1.getOpen(), d1.getClose()) <= yang.getHigh())) {
					if (d2.getClose() < d2.getOpen()
							|| (CrossLine.isCrossLine(d2) && Math.max(d2.getOpen(), d2.getClose()) <= yang.getHigh())) {
						if (d3.getClose() < d3.getOpen()
								|| (CrossLine.isCrossLine(d3) && Math.max(d3.getOpen(), d3.getClose()) <= yang.getHigh())) {
							if (Math.min(d3.getOpen(), d3.getClose()) >= yang.getLow()) {
								if(bigYang.getClose() > bigYang.getOpen() && bigYang.getClose() > yang.getClose()){
									result = true;
									
								}
							}
						}
					}
				}
			}

		}
		return result;
	}

}
