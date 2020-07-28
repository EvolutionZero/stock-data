package com.season.stock.data.bean;

public class StockAnalyseResult implements Comparable<StockAnalyseResult> {

	private String code;
	private String candle;
	private int score;
	
	private boolean preVenus;
	private boolean venus;
	private boolean risingThreeMethods;
	private boolean bullishSwallowedUp;
	private boolean pregnantRise;
	private boolean pierce;
	private boolean counterAttack;
	private boolean bullishBelt;
	private boolean bullishCrossLine;
	private boolean riseWindow;
	private boolean hammerLine;
	private boolean forwardThreeSoldiers;
	private boolean MA5MA10GoldenCross;
	private boolean MA5MA20GoldenCross;
	private boolean MA5MA30GoldenCross;
	private boolean passThroughMiddle;
	private boolean downThroughLower;
	private boolean wrMTop;
	private boolean shock;
	
	// 预测日期
	private String forecastDate;
	// 检测日期
	private String checkDate;
	private double precentChange;
	private double _5DayPercent;
	
	// 最大回撤
	private double drawdown;
	private double _5DayHighest;
	private double _5DayLowest;

	@Override
	public int compareTo(StockAnalyseResult o) {
		if (this.getScore() < o.getScore()) {
			return 1;
		} else {
			return -1;
		}
	}

	public String getCode() {
		return code;
	}

	public String getCandle() {
		return candle;
	}

	public int getScore() {
		return score;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCandle(String candle) {
		this.candle = candle;
	}

	public void setScore(int score) {
		this.score = score;
	}


	public boolean isPreVenus() {
		return preVenus;
	}

	public boolean isVenus() {
		return venus;
	}

	public boolean isRisingThreeMethods() {
		return risingThreeMethods;
	}

	public boolean isBullishSwallowedUp() {
		return bullishSwallowedUp;
	}

	public boolean isPregnantRise() {
		return pregnantRise;
	}

	public boolean isPierce() {
		return pierce;
	}

	public boolean isCounterAttack() {
		return counterAttack;
	}

	public boolean isBullishBelt() {
		return bullishBelt;
	}

	public boolean isBullishCrossLine() {
		return bullishCrossLine;
	}

	public void setPreVenus(boolean preVenus) {
		this.preVenus = preVenus;
	}

	public void setVenus(boolean venus) {
		this.venus = venus;
	}

	public void setRisingThreeMethods(boolean risingThreeMethods) {
		this.risingThreeMethods = risingThreeMethods;
	}

	public void setBullishSwallowedUp(boolean bullishSwallowedUp) {
		this.bullishSwallowedUp = bullishSwallowedUp;
	}

	public void setPregnantRise(boolean pregnantRise) {
		this.pregnantRise = pregnantRise;
	}

	public void setPierce(boolean pierce) {
		this.pierce = pierce;
	}

	public void setCounterAttack(boolean counterAttack) {
		this.counterAttack = counterAttack;
	}

	public void setBullishBelt(boolean bullishBelt) {
		this.bullishBelt = bullishBelt;
	}

	public boolean isRiseWindow() {
		return riseWindow;
	}

	public void setRiseWindow(boolean riseWindow) {
		this.riseWindow = riseWindow;
	}

	public void setBullishCrossLine(boolean bullishCrossLine) {
		this.bullishCrossLine = bullishCrossLine;
	}

	public String getForecastDate() {
		return forecastDate;
	}

	public double getPrecentChange() {
		return precentChange;
	}

	public void setForecastDate(String forecastDate) {
		this.forecastDate = forecastDate;
	}

	public void setPrecentChange(double precentChange) {
		this.precentChange = precentChange;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public double get_5DayPercent() {
		return _5DayPercent;
	}

	public void set_5DayPercent(double _5DayPercent) {
		this._5DayPercent = _5DayPercent;
	}

	public boolean isHammerLine() {
		return hammerLine;
	}

	public void setHammerLine(boolean hammerLine) {
		this.hammerLine = hammerLine;
	}

	public boolean isForwardThreeSoldiers() {
		return forwardThreeSoldiers;
	}

	public void setForwardThreeSoldiers(boolean forwardThreeSoldiers) {
		this.forwardThreeSoldiers = forwardThreeSoldiers;
	}

	public boolean isMA5MA10GoldenCross() {
		return MA5MA10GoldenCross;
	}

	public void setMA5MA10GoldenCross(boolean mA5MA10GoldenCross) {
		MA5MA10GoldenCross = mA5MA10GoldenCross;
	}

	public boolean isMA5MA20GoldenCross() {
		return MA5MA20GoldenCross;
	}

	public void setMA5MA20GoldenCross(boolean mA5MA20GoldenCross) {
		MA5MA20GoldenCross = mA5MA20GoldenCross;
	}


	public boolean isPassThroughMiddle() {
		return passThroughMiddle;
	}

	public void setPassThroughMiddle(boolean passThroughMiddle) {
		this.passThroughMiddle = passThroughMiddle;
	}

	public boolean isDownThroughLower() {
		return downThroughLower;
	}

	public void setDownThroughLower(boolean downThroughLower) {
		this.downThroughLower = downThroughLower;
	}

	public boolean isWrMTop() {
		return wrMTop;
	}

	public void setWrMTop(boolean wrMTop) {
		this.wrMTop = wrMTop;
	}

	public boolean isMA5MA30GoldenCross() {
		return MA5MA30GoldenCross;
	}

	public void setMA5MA30GoldenCross(boolean mA5MA30GoldenCross) {
		MA5MA30GoldenCross = mA5MA30GoldenCross;
	}

	@Override
	public String toString() {
		return "StockAnalyseResult [code=" + code + ", candle=" + candle
				+ ", score=" + score + ", preVenus=" + preVenus + ", venus="
				+ venus + ", risingThreeMethods=" + risingThreeMethods
				+ ", bullishSwallowedUp=" + bullishSwallowedUp
				+ ", pregnantRise=" + pregnantRise + ", pierce=" + pierce
				+ ", counterAttack=" + counterAttack + ", bullishBelt="
				+ bullishBelt + ", bullishCrossLine=" + bullishCrossLine
				+ ", riseWindow=" + riseWindow + ", hammerLine=" + hammerLine
				+ ", forwardThreeSoldiers=" + forwardThreeSoldiers
				+ ", MA5MA10GoldenCross=" + MA5MA10GoldenCross
				+ ", MA5MA20GoldenCross=" + MA5MA20GoldenCross
				+ ", MA5MA30GoldenCross=" + MA5MA30GoldenCross
				+ ", passThroughMiddle=" + passThroughMiddle
				+ ", downThroughLower=" + downThroughLower + ", wrMTop="
				+ wrMTop + ", shock=" + shock + ", forecastDate="
				+ forecastDate + ", checkDate=" + checkDate
				+ ", precentChange=" + precentChange + ", _5DayPercent="
				+ _5DayPercent + ", drawdown=" + drawdown + ", _5DayHighest="
				+ _5DayHighest + ", _5DayLowest=" + _5DayLowest + "]";
	}

	public double getDrawdown() {
		return drawdown;
	}

	public void setDrawdown(double drawdown) {
		this.drawdown = drawdown;
	}

	public double get_5DayHighest() {
		return _5DayHighest;
	}

	public double get_5DayLowest() {
		return _5DayLowest;
	}

	public void set_5DayHighest(double _5DayHighest) {
		this._5DayHighest = _5DayHighest;
	}

	public void set_5DayLowest(double _5DayLowest) {
		this._5DayLowest = _5DayLowest;
	}

	public boolean isShock() {
		return shock;
	}

	public void setShock(boolean shock) {
		this.shock = shock;
	}

}
