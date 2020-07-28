package com.season.stock.data.candle.up;

import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.candle.down.BearishBelt;
import com.season.stock.data.utils.MathUtils;
import com.season.stock.data.utils.StockUtils;

/**
 * 
 * <PRE>
 * 启明星
 * </PRE>
 * <B>项    目：</B> 
 */
public class Venus {
	
	public static boolean isVenus(final List<Stock> stocks){
		boolean result = false;
		if(stocks.size() > 3){
			double[] avgs = StockUtils.getAvgPrice(stocks);
			boolean decrease = MathUtils.isDecrease(avgs);
			if (decrease) {
				Stock down = stocks.get(stocks.size() - 3);
				Stock cross = stocks.get(stocks.size() - 2);
				Stock yang = stocks.get(stocks.size() - 1);
				
				boolean bearishBelt = BearishBelt.isBearishBelt(down);
				boolean crossLine = CrossLine.isCrossLine(cross);
				double max = Math.max(cross.getOpen(), cross.getClose());
				if(max < down.getClose()){
					if(yang.getClose() > yang.getOpen() && yang.getClose() > down.getClose()){
						result = bearishBelt && crossLine;
					}
					
				}
			}
		}
		return result;
	}
}
