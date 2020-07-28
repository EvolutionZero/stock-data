package com.season.stock.data.utils;

import java.text.DecimalFormat;
import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.bean.WR;
import com.season.stock.data.dao.DailyDao;

public class WRUtils {
	
	public static WR getWR(String code, String date){
		List<Stock> stocks = DailyDao.getStocks(code, date, 42);
		return getWR(stocks);
	}
	
	public static WR getWR(List<Stock> parmas){
		WR wrBean = new WR();
		List<Stock> stocks = StockUtils.getLastStocks(parmas, 42);
		double wr10 = getWRx(stocks, 10);
		double wr21= getWRx(stocks, 21);
		double wr42= getWRx(stocks, 42);
		
		wrBean.setWr10(wr10);
		wrBean.setWr21(wr21);
		wrBean.setWr42(wr42);
		return wrBean;
	}
	
	public static double getWRx(String code, String date, int cycle){
		List<Stock> stocks = DailyDao.getStocks(code, date, cycle);
		return getWRx(stocks, cycle);
	}
	
	public static double getWRx(List<Stock> params, int cycle){
		double wr = 0;
		List<Stock> stocks = StockUtils.getLastStocks(params, cycle);
		double close = stocks.get(stocks.size() - 1).getClose();
		
		double[] highLine = StockUtils.getHighLine(stocks);
		double[] lowLine = StockUtils.getLowLine(stocks);
		double maxValue = MathUtils.getMaxValue(highLine);
		double minValue = MathUtils.getMinValue(lowLine);
		
		if(maxValue - minValue != 0){
			wr = (maxValue - close) / (maxValue - minValue) * 100;
		} else {
			wr = 0;
		}
		
		DecimalFormat df = new DecimalFormat("0.###");
		wr = Double.valueOf(df.format(wr));
		return wr;
	}
	
	public static double[] getWR10Line(List<Stock> params){
		double[] line = new double[params.size()];
		for (int i = 0; i < line.length; i++) {
			line[i] = params.get(i).getWr10();
		}
		return line;
	}
	public static void main(String[] args) {
		System.out.println(getWRx("sh600872", "2016-12-08", 10));
		System.out.println(getWR("sh600872", "2016-12-09"));
	}
}
