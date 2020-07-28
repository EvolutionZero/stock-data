package com.season.stock.data.app;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.season.stock.data.bean.HaveNotForecastResult;
import com.season.stock.data.bean.Stock;
import com.season.stock.data.bean.StockAnalyseResult;
import com.season.stock.data.candle.up.BullishBelt;
import com.season.stock.data.candle.up.BullishSwallowedUp;
import com.season.stock.data.candle.up.Counterattack;
import com.season.stock.data.candle.up.CrossLine;
import com.season.stock.data.candle.up.ForwardThreeSoldiers;
import com.season.stock.data.candle.up.HammerLine;
import com.season.stock.data.candle.up.Pierce;
import com.season.stock.data.candle.up.PreVenus;
import com.season.stock.data.candle.up.PregnantRise;
import com.season.stock.data.candle.up.RiseWindow;
import com.season.stock.data.candle.up.RisingThreeMethods;
import com.season.stock.data.candle.up.Venus;
import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.dao.DailyDao;
import com.season.stock.data.dao.ForecastDao;
import com.season.stock.data.envm.Score;
import com.season.stock.data.utils.DayUtils;
import com.season.stock.data.utils.LogUtils;
import com.season.stock.data.utils.StatUtils;
import com.season.stock.data.utils.StockUtils;
import com.season.stock.data.westernindex.BollAnalyse;
import com.season.stock.data.westernindex.MAGoldenCross;
import com.season.stock.data.westernindex.Shock;
import com.season.stock.data.westernindex.WRAnalyse;

public class CandleAnalysist {

	private static final Logger LOG = LoggerFactory.getLogger(CandleAnalysist.class);
	
	private static final int LEVEL = 42;
	
	public static void main(String[] args) {
		done();
	}
	
	public static void done() {
		LogUtils.loadLogBackConfig();
		if(ForecastDao.getCnt() == 0){
			LOG.info("进行历史分析！");
			historyCandleAnalyse();
			LOG.info("历史分析完成！");
		}
		LOG.info("进行明天预测！");
		forecast();
//		forecastTomorrow();
		LOG.info("补全预测一个交易日结果！");
		completeNextDayForecastResult();
		LOG.info("补全最近5个交易日结果！");
		complete5DayForecastResult();
		LOG.info("预测分析完成！");
	}
	
	private static void forecast(){
		String lastForecastDay = ForecastDao.getLastForecastDay();
		List<String> allTradeDay = DailyDao.getAllTradeDay();
		List<String> needFcstTradeDay = allTradeDay;
		if(!"".equals(lastForecastDay)){
			needFcstTradeDay = DayUtils.after(lastForecastDay, allTradeDay);
		}
		List<String> codes = CodeDao.queryAllCodeOnExchange();
		String lastTradeDay = DailyDao.getTradeDayDesc(1).get(0);
		for (String code : codes) {
			List<StockAnalyseResult> results = new LinkedList<StockAnalyseResult>();
			for (String fcstTradeDay : needFcstTradeDay) {
				LOG.info("预测[{}][{}]！", code, fcstTradeDay);
				List<Stock> stocks = DailyDao.getStocksBeforeSomeDay(code, fcstTradeDay, LEVEL);
				StockAnalyseResult result = analyse(code, stocks);
				if(result.getScore() > 0) {
					Stock lastStock = stocks.get(stocks.size() - 1);
					String forecastDate = lastStock.getDate();
					result.setForecastDate(forecastDate);
					
					if(lastTradeDay.equals(forecastDate)){
						results.add(result);
						
					}
					
				}
			}
			ForecastDao.insert(results);
		}
	}
	
	public static void forecastTomorrow(){
		List<String> codes = CodeDao.queryAllCodeOnExchange();
		for (String code : codes) {
			LOG.info("预测明天:" +  code);
			List<Stock> stocks = DailyDao.getStocks(code, LEVEL);
			StockAnalyseResult result = analyse(code, stocks);
			if(result.getScore() > 0) {
				Stock lastStock = stocks.get(stocks.size() - 1);
				String forecastDate = lastStock.getDate();
				result.setForecastDate(forecastDate);
				
				ForecastDao.insert(result);
			}
		}
	}
	
	public static void completeNextDayForecastResult(){
		List<HaveNotForecastResult> notNextDayForecasts = ForecastDao.queryHaveNotNextDayForecast();
		for (HaveNotForecastResult haveNotForecastResult : notNextDayForecasts) {
			List<Stock> stocks = ForecastDao.getStocks(haveNotForecastResult);
			if(stocks.size() > 1){
				Stock stock = stocks.get(1);
				double precentChange = stock.getPrecentChange();
				String date = stock.getDate();
				String id = haveNotForecastResult.getId();
				ForecastDao.updateNextDayForecast(id, date, precentChange);
				
			}
			
		}
	}
	
	public static void complete5DayForecastResult(){
		DecimalFormat df = new DecimalFormat("0.##");
		List<HaveNotForecastResult> notNextDayForecasts = ForecastDao.queryHaveNot5DayForecast();
		for (HaveNotForecastResult haveNotForecastResult : notNextDayForecasts) {
			List<Stock> stocks = ForecastDao.getStocks(haveNotForecastResult);
			if(stocks.size() >= 5){
				Stock forecast = stocks.get(0);
				List<Stock> _5Days = getAfter5Day(stocks, 1);
				if(_5Days.size() == 5){
					double _5DayPrecent = get5DayPrecent(forecast, _5Days);
					
					double[] closeLine = StockUtils.getCloseLine(_5Days);
					double drawdown = StatUtils.drawdown(closeLine);
					
					drawdown = Double.valueOf(new DecimalFormat("0.##").format(drawdown * 100));
					double maxValue = StockUtils.getMaxValue(_5Days);
					double minValue = StockUtils.getMinValue(_5Days);
					
					maxValue = (maxValue - forecast.getOpen()) / forecast.getOpen();
					minValue = (minValue - forecast.getOpen()) / forecast.getOpen();
					maxValue = Double.valueOf(df.format(maxValue * 100));
					minValue = Double.valueOf(df.format(minValue * 100));
					
					String id = haveNotForecastResult.getId();
					if(_5DayPrecent != 0){
						ForecastDao.update5DayForecast(id, _5DayPrecent, drawdown, maxValue, minValue);
						
					}
					
				}
			}
			
		}
	}
	
	public static void historyCandleAnalyse(){
		List<String> codes = CodeDao.queryAllCodeOnExchange();
		for (String code : codes) {
			List<StockAnalyseResult> stockAnalyseResults = getStockAnalyseResults(code);
			if(!stockAnalyseResults.isEmpty()){
				LOG.info("分析{}完成!", code);
				ForecastDao.insert(stockAnalyseResults);
			}
		}
		LOG.info("历史分析完成");
	}

	public static List<StockAnalyseResult> getStockAnalyseResults(String code){
		List<StockAnalyseResult> analyseResults = new LinkedList<StockAnalyseResult>();
		DecimalFormat df = new DecimalFormat("0.##");
		List<Stock> stocks = DailyDao.getStocks(code, -1);
		if(stocks.size() > LEVEL ){
			SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat min = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			Date curDate = new Date();
			String judgeDate = day.format(curDate) + " 15:00";
			
			int size = stocks.size() - 1;
			try {
				if(curDate.after(min.parse(judgeDate))){
					size = stocks.size();
				}
			} catch (ParseException e) {
				LOG.error("", e);
			}
			for (int i = LEVEL; i <= size; i++) {
				List<Stock> _15Days = getBeforeDay(stocks, i);
				StockAnalyseResult stockAnalyseResult = analyse(code, _15Days);
				if(stockAnalyseResult.getScore() > 0){
					Stock forecast = stocks.get(i - 1);
					if(i < stocks.size()){
						Stock check = stocks.get(i);
						if(stocks.size() - (i - 1) > 5 ){
							List<Stock> after5Day = getAfter5Day(stocks, i);
							double _5DayPercent = get5DayPrecent(forecast, after5Day);
							stockAnalyseResult.set_5DayPercent(Double.valueOf(df.format(_5DayPercent)));
							
							double[] closeLine = StockUtils.getCloseLine(after5Day);
							double drawdown = StatUtils.drawdown(closeLine);
							stockAnalyseResult.setDrawdown(Double.valueOf(df.format(drawdown * 100)));
							
							double maxValue = StockUtils.getMaxValue(after5Day);
							double minValue = StockUtils.getMinValue(after5Day);
							
							maxValue = (maxValue - forecast.getOpen()) / forecast.getOpen();
							minValue = (minValue - forecast.getOpen()) / forecast.getOpen();
							stockAnalyseResult.set_5DayHighest(Double.valueOf(df.format(maxValue * 100)));
							stockAnalyseResult.set_5DayLowest(Double.valueOf(df.format(minValue * 100)));
						}
						stockAnalyseResult.setForecastDate(forecast.getDate());
						stockAnalyseResult.setCheckDate(check.getDate());
						stockAnalyseResult.setPrecentChange(check.getPrecentChange());
						
						analyseResults.add(stockAnalyseResult);
						
					}
				}
			}
		}
		return analyseResults;
	}
	
	private static double get5DayPrecent(Stock forecast, List<Stock> after5Day) {
		double forecastMax = Math.max(forecast.getOpen(), forecast.getClose());
		double maxValue = StockUtils.getMaxValue(after5Day);
		double _5DayPercent = 0;
		if(maxValue >= forecastMax){
			_5DayPercent = (maxValue - forecast.getClose()) / forecast.getClose() * 100;
			
		} else {
			double minValue = StockUtils.getMinValue(after5Day);
			_5DayPercent = (minValue - forecast.getClose()) / forecast.getClose() * 100;
		}
		return _5DayPercent;
	}
	
	private static List<Stock> getBeforeDay(List<Stock> stocks , int cur){
		LinkedList<Stock> result = new LinkedList<Stock>();
		for (int i = cur - LEVEL ; i < cur; i++) {
			Stock stock = stocks.get(i);
			result.add(stock);
		}
		return result;
	}
	
	private static List<Stock> getAfter5Day(List<Stock> stocks , int cur){
		LinkedList<Stock> result = new LinkedList<Stock>();
		int end = stocks.size();
		if(cur + 5 < stocks.size()){
			end = cur + 5;
		}
		for (int i = cur ; i < end; i++) {
			Stock stock = stocks.get(i);
			result.add(stock);
		}
		return result;
	}
	
	public static StockAnalyseResult analyse(String code, List<Stock> stocks) {
		StockAnalyseResult stockAnalyseResult = new StockAnalyseResult();
		StringBuffer candle = new StringBuffer();
		int score = 0;
		boolean preVenus = PreVenus.isPreVenus(stocks);
		if(preVenus){
			score += Score.PRE_VENUS;
			candle.append("启明星前态").append(",");
			stockAnalyseResult.setPreVenus(true);
		}
		boolean venus = Venus.isVenus(stocks);
		if(venus){
			score += Score.VENUS;
			candle.append("启明星").append(",");
			stockAnalyseResult.setVenus(true);
		}
		boolean bullishSwallowedUp = BullishSwallowedUp.isBullishSwallowedUp(stocks);
		if(bullishSwallowedUp){
			score += Score.BULLISH_SWALLOWED_UP;
			candle.append("涨吞").append(",");
			stockAnalyseResult.setBullishSwallowedUp(true);
		}
		boolean pierce = Pierce.isPierce(stocks);
		if(pierce){
			score += Score.PIERCE;
			candle.append("刺透").append(",");
			stockAnalyseResult.setPierce(true);
		}
		boolean counterattack = Counterattack.isCounterattack(stocks);
		if(counterattack){
			score += Score.COUNTER_ATTACK;
			candle.append("反击").append(",");
			stockAnalyseResult.setCounterAttack(true);
		}
		boolean bullishBelt = BullishBelt.isBullishBelt(stocks);
		if(bullishBelt){
			score += Score.BULLISH_BELT;
			candle.append("看涨捉腰带线").append(",");
			stockAnalyseResult.setBullishBelt(true);
		}
		boolean crossLine = CrossLine.isBullishCrossLine(stocks);
		if(crossLine){
			score += Score.BULLISH_CROSS_LINE;
			candle.append("看涨十字线").append(",");
			stockAnalyseResult.setBullishCrossLine(true);
		}
		boolean pregnantRise = PregnantRise.isPregnantRise(stocks);
		if(pregnantRise){
			score += Score.PREGNANT_RISE;
			candle.append("孕涨").append(",");
			stockAnalyseResult.setPregnantRise(true);
		}
		boolean risingThreeMethods = RisingThreeMethods.isRisingThreeMethods(stocks);
		if(risingThreeMethods){
			score += Score.RISIG_THREE_METHODS;
			candle.append("上升三法").append(",");
			stockAnalyseResult.setRisingThreeMethods(true);
		}
		boolean riseWindow = RiseWindow.isWinodw(stocks);
		if(riseWindow){
			score += Score.RISE_WINDOW;
			candle.append("上升窗口").append(",");
			stockAnalyseResult.setRiseWindow(true);
		}
		boolean hammerLine = HammerLine.isHammerLine(stocks);
		if(hammerLine){
			score += Score.HAMMER_LINE;
			candle.append("锤子线").append(",");
			stockAnalyseResult.setHammerLine(true);
		}
		boolean forwardThreeSoldiers = ForwardThreeSoldiers.isForwardThreeSoldiers(stocks);
		if(forwardThreeSoldiers){
			score += Score.FORWARD_THREE_SOLDIERS;
			candle.append("前进三兵").append(",");
			stockAnalyseResult.setForwardThreeSoldiers(true);
		}
		boolean ma5ma10GoldenCross = MAGoldenCross.isMA5MA10GoldenCross(stocks);
		if(ma5ma10GoldenCross){
			score += Score.MA5_MA10_GOLDEN_CROSS;
			candle.append("MA5-MA10金交叉").append(",");
			stockAnalyseResult.setMA5MA10GoldenCross(true);
		}
		boolean ma5ma20GoldenCross = MAGoldenCross.isMA5MA20GoldenCross(stocks);
		if(ma5ma20GoldenCross){
			score += Score.MA5_MA20_GOLDEN_CROSS;
			candle.append("MA5-MA20金交叉").append(",");
			stockAnalyseResult.setMA5MA20GoldenCross(true);
		}
		boolean ma5ma30GoldenCross = MAGoldenCross.isMA5MA30GoldenCross(stocks);
		if(ma5ma30GoldenCross){
			score += Score.MA5_MA30_GOLDEN_CROSS;
			candle.append("MA5-MA30金交叉").append(",");
			stockAnalyseResult.setMA5MA30GoldenCross(true);
		}
		
		boolean passThroughMiddle = BollAnalyse.isPassThroughMiddle(stocks);
		if(passThroughMiddle){
			score += Score.PASS_THROUGH_MIDDLE;
			candle.append("布林带上穿中轨").append(",");
			stockAnalyseResult.setPassThroughMiddle(true);
		}
		
		boolean downThroughLower = BollAnalyse.isDownThroughLower(stocks);
		if(downThroughLower){
			score += Score.DOWN_THROUGH_LOWER;
			candle.append("布林带下穿下轨").append(",");
			stockAnalyseResult.setDownThroughLower(true);
		}
		
		boolean mTop = WRAnalyse.isMtop(stocks);
		if(mTop){
			score += Score.WR_M_TOP;
			candle.append("WR指数M字顶即将见底").append(",");
			stockAnalyseResult.setWrMTop(true);
		}
//		boolean shock = Shock.isShock(stocks);
//		if(shock){
//			score += Score.SHOCK;
//			candle.append("震荡区间").append(",");
//			stockAnalyseResult.setShock(shock);
//			
//		}
		
		if(candle.length() > 1){
			candle.deleteCharAt(candle.length() - 1);
		}
		stockAnalyseResult.setCode(code);
		stockAnalyseResult.setCandle(candle.toString());
		stockAnalyseResult.setScore(score);
			
		return stockAnalyseResult;
	}
}
