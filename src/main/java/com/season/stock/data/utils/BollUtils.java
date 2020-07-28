package com.season.stock.data.utils;

import java.text.DecimalFormat;
import java.util.List;

import com.season.stock.data.bean.Boll;
import com.season.stock.data.bean.Stock;
import com.season.stock.data.dao.DailyDao;

/**
 * 
 * <PRE>
 * 布林带工具
 * </PRE>
 */
public class BollUtils {

	public static Boll getBoll(String code, String date, int cycle){
		List<Stock> stocks = DailyDao.getStocks(code, date, cycle);
		return getBoll(stocks, cycle);
	}
	
	public static Boll getBoll(List<Stock> params){
		return getBoll(params, 20);
	}
	
	public static Boll getBoll(List<Stock> params, int cycle){
		Boll boll = new Boll();
		List<Stock> stocks = StockUtils.getLastStocks(params, cycle);
		double[] closeLine = StockUtils.getCloseLine(stocks);
		
		double middle = stocks.get(stocks.size() - 1).getMa20();
		double standardDeviation = MathUtils.getStandardDeviation(middle, closeLine);
		
		double upper = middle + 2 * standardDeviation;
		double lower = middle - 2 * standardDeviation;
		
		DecimalFormat df = new DecimalFormat("0.###");
		upper = Double.valueOf(df.format(upper));
		lower = Double.valueOf(df.format(lower));
		
		double close = stocks.get(stocks.size() - 1).getClose();
		double percentB = 0; 
		if(upper - lower != 0){
			percentB = (close - lower) / (upper - lower); 
			
		}
		percentB = Double.valueOf(df.format(percentB));
		
		boll.setLower(lower);
		boll.setMiddle(middle);
		boll.setUpper(upper);
		boll.setPercentB(percentB);
		return boll;
	}
	
	public static double[] getUpperLine(List<Stock> params){
		double[] line = new double[params.size()];
		for (int i = 0; i < line.length; i++) {
			line[i] = params.get(i).getBollUpper();
		}
		return line;
	}
	
	public static double[] getLowerLine(List<Stock> params){
		double[] line = new double[params.size()];
		for (int i = 0; i < line.length; i++) {
			line[i] = params.get(i).getBollLower();
		}
		return line;
	}
	
	public static double[] getMiddleLine(List<Stock> params){
		double[] line = new double[params.size()];
		for (int i = 0; i < line.length; i++) {
			line[i] = params.get(i).getMa20();
		}
		return line;
	}
	public static void main(String[] args) {
		System.out.println(getBoll("sh600201", "2016-11-28", 20));
	}
	
}
