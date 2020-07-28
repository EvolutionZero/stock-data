package com.season.stock.data.westernindex;

import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.utils.MathUtils;
import com.season.stock.data.utils.StockUtils;
import com.season.stock.data.utils.WRUtils;

public class WRAnalyse {

	private static final int SHORT_LEVEL = 10;
	
	public static boolean isMtop(final List<Stock> stocks){
		boolean isMtop = false;
		List<Stock> temp = StockUtils.getLastShortLevelStocks(stocks, SHORT_LEVEL);
		
		double[] wr10Line = WRUtils.getWR10Line(temp);
		if(MathUtils.above(wr10Line, 85) && MathUtils.isThreeTop(wr10Line)){
			isMtop = true;
		}
		return isMtop;
	}
}
