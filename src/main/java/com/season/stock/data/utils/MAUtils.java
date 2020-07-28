package com.season.stock.data.utils;

import java.text.DecimalFormat;
import java.util.List;

import com.season.stock.data.bean.Stock;

public class MAUtils {

	public static double getMAx(List<Stock> stocks, int n){
		DecimalFormat df = new DecimalFormat("0.###");
		double MAx = 0;
		List<Stock> temp = stocks;
		if(temp.size() >= n){
			temp = StockUtils.getLastShortLevelStocks(stocks, n);
			double total = 0;
			for (Stock stock : temp) {
				total += stock.getClose();
			}
			MAx = Double.valueOf(df.format(total / n));
		}
		return MAx;
	}
	
}
