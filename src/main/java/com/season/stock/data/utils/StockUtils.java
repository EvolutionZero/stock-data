package com.season.stock.data.utils;

import java.util.LinkedList;
import java.util.List;

import com.season.stock.data.bean.Stock;

public class StockUtils {

	public static double getMaxValue(List<Stock> stocks){
		double max = Double.MIN_VALUE;
		for (Stock stock : stocks) {
			double temp = Math.max(stock.getOpen(), stock.getClose());
			max = temp > max ? temp : max;
		}
		return max;
	}
	
	public static double getMinValue(List<Stock> stocks){
		double min = Double.MAX_VALUE;
		for (Stock stock : stocks) {
			double temp = Math.min(stock.getOpen(), stock.getClose());
			min = temp < min ? temp : min;
		}
		return min;
	}
	
	public static double[] getAvgPrice(List<Stock> stocks){
		double[] avgs = new double[stocks.size()];
		int idx = 0;
		for (Stock stock : stocks) {
			double close = stock.getClose();
			double open = stock.getOpen();
			
			double avg = (open + close) / 2;
			avgs[idx] = avg;
			idx++;
		}
		return avgs;
	}
	
	public static double[] getMA5Line(List<Stock> stocks){
		double[] ma5s = new double[stocks.size()];
		for (int i = 0; i < ma5s.length; i++) {
			ma5s[i] = stocks.get(i).getMa5();
		}
		return ma5s;
	}
	
	public static double[] getMA10Line(List<Stock> stocks){
		double[] ma10s = new double[stocks.size()];
		for (int i = 0; i < ma10s.length; i++) {
			ma10s[i] = stocks.get(i).getMa10();
		}
		return ma10s;
	}
	
	public static double[] getMA20Line(List<Stock> stocks){
		double[] ma20s = new double[stocks.size()];
		for (int i = 0; i < ma20s.length; i++) {
			ma20s[i] = stocks.get(i).getMa20();
		}
		return ma20s;
	}
	
	public static double[] getMA30Line(List<Stock> stocks){
		double[] ma30s = new double[stocks.size()];
		for (int i = 0; i < ma30s.length; i++) {
			ma30s[i] = stocks.get(i).getMa30();
		}
		return ma30s;
	}
	
	public static double[] getCloseLine(List<Stock> stocks){
		double[] closes = new double[stocks.size()];
		for (int i = 0; i < closes.length; i++) {
			closes[i] = stocks.get(i).getClose();
		}
		return closes;
	}
	
	public static double[] getHighLine(List<Stock> stocks){
		double[] highs = new double[stocks.size()];
		for (int i = 0; i < highs.length; i++) {
			highs[i] = stocks.get(i).getHigh();
		}
		return highs;
	}
	
	public static double[] getLowLine(List<Stock> stocks){
		double[] lows = new double[stocks.size()];
		for (int i = 0; i < lows.length; i++) {
			lows[i] = stocks.get(i).getLow();
		}
		return lows;
	}
	
	public static List<Stock> convertToList(Stock stock){
		LinkedList<Stock> stocks = new LinkedList<Stock>();
		if(stock != null){
			stocks.add(stock);
		}
		return stocks;
	}
	
	/**
	 * 不够level个数则返回空集
	 * @param stocks
	 * @param level
	 * @return
	 */
	public static List<Stock> getLastShortLevelStocks(List<Stock> stocks ,int level){
		List<Stock> result = new LinkedList<Stock>();
		if(stocks.size() >= level){
			for (int i = stocks.size() - level; i < stocks.size(); i++) {
				result.add(stocks.get(i));
			}
		}
		return result;
	}
	
	public static List<Stock> getLastStocks(List<Stock> stocks ,int cnt){
		List<Stock> result = new LinkedList<Stock>();
		if(stocks.size() >= cnt){
			for (int i = stocks.size() - cnt; i < stocks.size(); i++) {
				result.add(stocks.get(i));
			}
		} else {
			result = stocks;
		}
		return result;
	}
}
