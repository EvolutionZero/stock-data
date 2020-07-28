package com.season.stock.data.bean;

import java.sql.ResultSet;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Stock {
	
	private String id;
	private String code;
	private String date;
	private double open;
	private double high;
	private double close;
	private double low;
	private double volume;
	private double amount;
	private double change;
	private double valueChange;
	private double precentChange;
	private double ma5;
	private double ma10;
	private double ma20;
	private double ma30;
	private double vma5;
	private double vma10;
	private double vma20;
	private double turnover;
	
	private double bollUpper;
	private double bollLower;
	private double bollPercentB;
	
	private double wr10;
	private double wr21;
	private double wr42;

	public Stock() {
		// TODO Auto-generated constructor stub
	}
	
	public Stock(ResultSet rs){
		try {
			String code = rs.getString("i_code");
			double open = rs.getDouble("d_open");
			double close = rs.getDouble("d_close");
			double high = rs.getDouble("high");
			double low = rs.getDouble("low");
			double precentChange = rs.getDouble("precentChange");
			String date = rs.getString("date");
			
			this.code = code;
			this.open = open;
			this.close = close;
			this.high = high;
			this.low = low;
			this.date = date;
			this.precentChange = precentChange;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Stock(SqlRowSet rs){
		try {
			String code = rs.getString("i_code");
			double open = rs.getDouble("d_open");
			double close = rs.getDouble("d_close");
			double high = rs.getDouble("high");
			double low = rs.getDouble("low");
			double precentChange = rs.getDouble("precentChange");
			String date = rs.getString("date");
			
			this.code = code;
			this.open = open;
			this.close = close;
			this.high = high;
			this.low = low;
			this.date = date;
			this.precentChange = precentChange;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getDate() {
		return date;
	}

	public double getOpen() {
		return open;
	}

	public double getHigh() {
		return high;
	}

	public double getClose() {
		return close;
	}

	public double getLow() {
		return low;
	}

	public double getVolume() {
		return volume;
	}

	public double getAmount() {
		return amount;
	}

	public double getChange() {
		return change;
	}

	public double getValueChange() {
		return valueChange;
	}

	public double getPrecentChange() {
		return precentChange;
	}

	public double getMa5() {
		return ma5;
	}

	public double getMa10() {
		return ma10;
	}

	public double getMa20() {
		return ma20;
	}

	public double getVma5() {
		return vma5;
	}

	public double getVma10() {
		return vma10;
	}

	public double getVma20() {
		return vma20;
	}

	public double getTurnover() {
		return turnover;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setChange(double change) {
		this.change = change;
	}

	public void setValueChange(double valueChange) {
		this.valueChange = valueChange;
	}

	public void setPrecentChange(double precentChange) {
		this.precentChange = precentChange;
	}

	public void setMa5(double ma5) {
		this.ma5 = ma5;
	}

	public void setMa10(double ma10) {
		this.ma10 = ma10;
	}

	public void setMa20(double ma20) {
		this.ma20 = ma20;
	}

	public void setVma5(double vma5) {
		this.vma5 = vma5;
	}

	public void setVma10(double vma10) {
		this.vma10 = vma10;
	}

	public void setVma20(double vma20) {
		this.vma20 = vma20;
	}

	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}

	public double getBollUpper() {
		return bollUpper;
	}

	public double getBollLower() {
		return bollLower;
	}

	public double getBollPercentB() {
		return bollPercentB;
	}

	public double getWr10() {
		return wr10;
	}

	public double getWr21() {
		return wr21;
	}

	public double getWr42() {
		return wr42;
	}

	public void setBollUpper(double bollUpper) {
		this.bollUpper = bollUpper;
	}

	public void setBollLower(double bollLower) {
		this.bollLower = bollLower;
	}

	public void setBollPercentB(double bollPercentB) {
		this.bollPercentB = bollPercentB;
	}

	public void setWr10(double wr10) {
		this.wr10 = wr10;
	}

	public void setWr21(double wr21) {
		this.wr21 = wr21;
	}

	public void setWr42(double wr42) {
		this.wr42 = wr42;
	}

	public double getMa30() {
		return ma30;
	}

	public void setMa30(double ma30) {
		this.ma30 = ma30;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", code=" + code + ", date=" + date
				+ ", open=" + open + ", high=" + high + ", close=" + close
				+ ", low=" + low + ", volume=" + volume + ", amount=" + amount
				+ ", change=" + change + ", valueChange=" + valueChange
				+ ", precentChange=" + precentChange + ", ma5=" + ma5
				+ ", ma10=" + ma10 + ", ma20=" + ma20 + ", ma30=" + ma30
				+ ", vma5=" + vma5 + ", vma10=" + vma10 + ", vma20=" + vma20
				+ ", turnover=" + turnover + ", bollUpper=" + bollUpper
				+ ", bollLower=" + bollLower + ", bollPercentB=" + bollPercentB
				+ ", wr10=" + wr10 + ", wr21=" + wr21 + ", wr42=" + wr42 + "]";
	}

}
