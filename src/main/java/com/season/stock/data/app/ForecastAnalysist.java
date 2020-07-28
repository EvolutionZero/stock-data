package com.season.stock.data.app;

import java.text.DecimalFormat;

import com.season.stock.data.dao.ForecastDao;

public class ForecastAnalysist {
	
	
	public static void main(String[] args) {
		System.out.println("-------------------次日准确率(>=0%)-------------------------------");
		showPrecent("启明星前态", "preVenus");
		showPrecent("启明星", "venus");
		showPrecent("上升三法", "risingThreeMethods");
		showPrecent("涨吞", "bullishSwallowedUp");
		showPrecent("孕涨", "pregnantRise");
		showPrecent("刺透", "pierce");
		showPrecent("反击线", "counterAttack");
		showPrecent("看涨捉腰带线", "bullishBelt");
		showPrecent("看涨十字线", "bullishCrossLine");
		showPrecent("上升窗口", "riseWindow");
		showPrecent("锤子线", "hammerLine");
		showPrecent("前进三兵", "forwardThreeSoldiers");
		showPrecent("MA5-MA10金交叉", "MA5MA10GoldenCross");
		showPrecent("MA5-MA20金交叉", "MA5MA20GoldenCross");
		showPrecent("MA5-MA30金交叉", "MA5MA30GoldenCross");
		showPrecent("布林带上穿中轨", "passThroughMiddle");
		showPrecent("布林带下穿下轨", "downThroughLower");
		showPrecent("WR指数M字顶即将见底", "wrMtop");
		
		System.out.println("-------------------5日内准确率(>=0%)-------------------------------");
		
		show5DayPrecent("启明星前态", "preVenus", 0);
		show5DayPrecent("启明星", "venus", 0);
		show5DayPrecent("上升三法", "risingThreeMethods", 0);
		show5DayPrecent("涨吞", "bullishSwallowedUp", 0);
		show5DayPrecent("孕涨", "pregnantRise", 0);
		show5DayPrecent("刺透", "pierce", 0);
		show5DayPrecent("反击线", "counterAttack", 0);
		show5DayPrecent("看涨捉腰带线", "bullishBelt", 0);
		show5DayPrecent("看涨十字线", "bullishCrossLine", 0);
		show5DayPrecent("上升窗口", "riseWindow",0);
		show5DayPrecent("锤子线", "hammerLine",0);
		show5DayPrecent("前进三兵", "forwardThreeSoldiers",0);
		show5DayPrecent("MA5-MA10金交叉", "MA5MA10GoldenCross",0);
		show5DayPrecent("MA5-MA20金交叉", "MA5MA20GoldenCross",0);
		show5DayPrecent("MA5-MA30金交叉", "MA5MA30GoldenCross",0);
		show5DayPrecent("布林带上穿中轨", "passThroughMiddle",0);
		show5DayPrecent("布林带下穿下轨", "downThroughLower",0);
		show5DayPrecent("WR指数M字顶即将见底", "wrMtop",0);
		
		System.out.println("-------------------5日内准确率(>=1%)-------------------------------");
		show5DayPrecent("启明星前态", "preVenus", 1);
		show5DayPrecent("启明星", "venus", 1);
		show5DayPrecent("上升三法", "risingThreeMethods", 1);
		show5DayPrecent("涨吞", "bullishSwallowedUp", 1);
		show5DayPrecent("孕涨", "pregnantRise", 1);
		show5DayPrecent("刺透", "pierce", 1);
		show5DayPrecent("反击线", "counterAttack", 1);
		show5DayPrecent("看涨捉腰带线", "bullishBelt", 1);
		show5DayPrecent("看涨十字线", "bullishCrossLine", 1);
		show5DayPrecent("上升窗口", "riseWindow",1);
		show5DayPrecent("锤子线", "hammerLine",1);
		show5DayPrecent("前进三兵", "forwardThreeSoldiers",1);
		show5DayPrecent("MA5-MA10金交叉", "MA5MA10GoldenCross",1);
		show5DayPrecent("MA5-MA20金交叉", "MA5MA20GoldenCross",1);
		show5DayPrecent("MA5-MA30金交叉", "MA5MA30GoldenCross",1);
		show5DayPrecent("布林带上穿中轨", "passThroughMiddle",1);
		show5DayPrecent("布林带下穿下轨", "downThroughLower",1);
		show5DayPrecent("WR指数M字顶即将见底", "wrMtop",1);
		
		System.out.println("-------------------5日内准确率(>=2%)-------------------------------");
		show5DayPrecent("启明星前态", "preVenus", 2);
		show5DayPrecent("启明星", "venus", 2);
		show5DayPrecent("上升三法", "risingThreeMethods", 2);
		show5DayPrecent("涨吞", "bullishSwallowedUp", 2);
		show5DayPrecent("孕涨", "pregnantRise", 2);
		show5DayPrecent("刺透", "pierce", 2);
		show5DayPrecent("反击线", "counterAttack", 2);
		show5DayPrecent("看涨捉腰带线", "bullishBelt", 2);
		show5DayPrecent("看涨十字线", "bullishCrossLine", 2);
		show5DayPrecent("上升窗口", "riseWindow",2);
		show5DayPrecent("锤子线", "hammerLine",2);
		show5DayPrecent("前进三兵", "forwardThreeSoldiers",2);
		show5DayPrecent("MA5-MA10金交叉", "MA5MA10GoldenCross",2);
		show5DayPrecent("MA5-MA20金交叉", "MA5MA20GoldenCross",2);
		show5DayPrecent("MA5-MA30金交叉", "MA5MA30GoldenCross",2);
		show5DayPrecent("布林带上穿中轨", "passThroughMiddle",2);
		show5DayPrecent("布林带下穿下轨", "downThroughLower",2);
		show5DayPrecent("WR指数M字顶即将见底", "wrMtop",2);
	}
	
	public static void showPrecent(String candleChName, String candleEnName){
		DecimalFormat df = new DecimalFormat("00.##");
		ForecastResult result = getCandleSuccessPrecent(candleEnName);
		System.out.println(candleChName + ": " + df.format(result.getPrecent())
				+ "% ( " + result.getRight() + " / " + result.getTotal() + " )");
	}
	
	public static void show5DayPrecent(String candleChName, String candleEnName, int precent){
		DecimalFormat df = new DecimalFormat("00.##");
		ForecastResult result = getCandle5DaySuccessPrecent(candleEnName, precent);
		System.out.println(candleChName + ": " + df.format(result.getPrecent())
				+ "% ( " + result.getRight() + " / " + result.getTotal() + " )");
	}
	
	public static ForecastResult getCandleSuccessPrecent(String candleName){
		double precent = 0;
		double total = ForecastDao.getCandleCount(candleName);
		double upCount = ForecastDao.getCandleUpCount(candleName);
		if(total > 0){
			precent = (upCount / total) * 100;
		}
		ForecastResult forecastResult = new ForecastResult();
		forecastResult.setPrecent(precent);
		forecastResult.setTotal((int)total);
		forecastResult.setRight((int)upCount);
		return forecastResult;
	}
	
	public static double getCandle5DaySuccessPrecent(String candleName){
		double precent = 0;
		double total = ForecastDao.getCandleCount(candleName);
		double upCount = ForecastDao.getCandle5DayUpCount(candleName);
		if(total > 0){
			precent = (upCount / total) * 100;
		}
		return precent;
	}
	
	public static ForecastResult getCandle5DaySuccessPrecent(String candleName, double expect){
		double precent = 0;
		double total = ForecastDao.getCandleCount(candleName);
		double upCount = ForecastDao.getCandle5DayUpCount(candleName, expect);
		if(total > 0){
			precent = (upCount / total) * 100;
		}
		ForecastResult forecastResult = new ForecastResult();
		forecastResult.setPrecent(precent);
		forecastResult.setTotal((int)total);
		forecastResult.setRight((int)upCount);
		return forecastResult;
	}
}

class ForecastResult{
	private double precent;
	private int total;
	private int right;
	public double getPrecent() {
		return precent;
	}
	public int getTotal() {
		return total;
	}
	public int getRight() {
		return right;
	}
	public void setPrecent(double precent) {
		this.precent = precent;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public void setRight(int right) {
		this.right = right;
	}
}
