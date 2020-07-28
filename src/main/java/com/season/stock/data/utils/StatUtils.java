package com.season.stock.data.utils;

import java.text.DecimalFormat;
import java.util.List;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.bean.StrengthIdx;

public class StatUtils {

	public static StrengthIdx strength(List<Stock> stocks){
		StrengthIdx idx = new StrengthIdx();
		int riseNum = 0;
		double riseTotalRate = 0;
		double downTotalRate = 0;
		
		for (Stock stock : stocks) {
			if(stock.getPrecentChange() > 0){
				riseNum++;
				riseTotalRate += stock.getPrecentChange();
			} else {
				downTotalRate += stock.getPrecentChange();
			}
		}
		
		double riseProbabily = stocks.size() == 0 ? 0 : riseNum * 1.0 / stocks.size() * 100;
		double strength = downTotalRate == 0 ? 10000 : riseTotalRate / Math.abs(downTotalRate);
		
		new Double((new DecimalFormat("0.##").format(riseProbabily)));
		idx.setRiseNum(riseNum);
		idx.setRiseProbabily((new DecimalFormat("0.##").format(riseProbabily)));
		idx.setRiseTotalRate((new DecimalFormat("0.##").format(riseTotalRate)));
		idx.setStrength((new DecimalFormat("0.###").format(strength)));
		
		return idx;
	}
	
	/**
	 * 最大回撤
	 * @param line
	 * @return
	 */
	public static double drawdown(double[] line){
		double drawdown = 0;
		for (int i = 0; i < line.length - 1; i++) {
			double max = Double.MIN_VALUE;
			for (int j = i; j < line.length; j++) {
				max = line[i] - line[j] > max ? line[i] - line[j] : max;
			}
			drawdown = max / line[i] > drawdown ? max / line[i] : drawdown;
		}
		return drawdown;
	}
	
	public static void main(String[] args) {
		System.out.println(drawdown(new double[]{1, 10 , 9 , 12 , 10}));
	}
}
