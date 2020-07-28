package com.season.stock.data.westernindex;

import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.utils.MathUtils;
import com.season.stock.data.utils.StockUtils;

/**
 * 
 * <PRE>
 * MA线金交叉
 * </PRE>
 * <B>项    目：</B> 
 * @since     jdk版本：jdk1.6
 */
public class MAGoldenCross {

	private static final int SHORT_LEVEL = 15;
	
	public static boolean isMA5MA10GoldenCross(final List<Stock> stocks){
		boolean goldenCross = false;
		List<Stock> temp = StockUtils.getLastShortLevelStocks(stocks, SHORT_LEVEL);
		
		double[] ma5Line = StockUtils.getMA5Line(temp);
		double[] ma10Line = StockUtils.getMA10Line(temp);
		
		if(MathUtils.isDrabIncrease(ma5Line) && MathUtils.isDrabIncrease(ma10Line) 
				&& MathUtils.isGoldenCross(ma5Line, ma10Line)){
			
			int idx = 0;
			for (int i = 0; i < ma10Line.length; i++) {
				if(ma5Line[i] > ma10Line[i]){
					idx = i;
					break;
				}
			}
			double[] avgPrice = StockUtils.getAvgPrice(temp);
			if(idx >= SHORT_LEVEL * 0.8 &&  MathUtils.above(avgPrice, ma5Line)){
				goldenCross = true;
				
			}
		}
		return goldenCross;
	}
	
	/**
	 * 
	 * 
		
	
	
	 * @param stocks
	 * @return
	 */
	public static boolean isMA5MA20GoldenCross(final List<Stock> stocks){
		boolean goldenCross = false;
		List<Stock> temp = StockUtils.getLastShortLevelStocks(stocks, SHORT_LEVEL);
		
		double[] ma5Line = StockUtils.getMA5Line(temp);
		double[] ma20Line = StockUtils.getMA20Line(temp);
		
		if( MathUtils.isDrabIncrease(ma5Line) && MathUtils.isDrabIncrease(ma20Line)
				&& MathUtils.isGoldenCross(ma5Line, ma20Line)){
			
			int idx = 0;
			for (int i = 0; i < ma20Line.length; i++) {
				if(idx >= SHORT_LEVEL * 0.8 &&  ma5Line[i] > ma20Line[i]){
					idx = i;
					break;
				}
			}
			double[] avgPrice = StockUtils.getAvgPrice(temp);
			if(MathUtils.above(avgPrice, ma5Line)){
				goldenCross = true;
				
			}
		}
		return goldenCross;
	}
	
	public static boolean isMA5MA30GoldenCross(final List<Stock> stocks){
		boolean goldenCross = false;
		List<Stock> temp = StockUtils.getLastShortLevelStocks(stocks, 10);
		
		double[] ma5Line = StockUtils.getMA5Line(temp);
		double[] ma30Line = StockUtils.getMA30Line(temp);
		
		if(MathUtils.isDrabIncrease(ma5Line) && MathUtils.isDrabIncrease(ma30Line)
				&& MathUtils.isGoldenCross(ma5Line, ma30Line)){
			
			int idx = 0;
			for (int i = 0; i < ma30Line.length; i++) {
				if(idx >= SHORT_LEVEL * 0.8 &&  ma5Line[i] > ma30Line[i]){
					idx = i;
					break;
				}
			}
			double[] avgPrice = StockUtils.getAvgPrice(temp);
			if(MathUtils.above(avgPrice, ma5Line)){
				goldenCross = true;
				
			}
		}
		return goldenCross;
	}
	/**
	 * 

	 * @param args
	 */
	public static void main(String[] args) {
		double[] ma5Line = new double[]{-1,3,5,9,10};
		double[] ma10Line = new double[] {0 ,2,4,8,9};
		System.out.println(MathUtils.isGoldenCross(ma5Line, ma10Line));
	}
	
}
