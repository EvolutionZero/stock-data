package com.season.stock.data.bean;

public class WR {

	private double wr10;
	private double wr21;
	private double wr42;
	public double getWr10() {
		return wr10;
	}
	public double getWr21() {
		return wr21;
	}
	public double getWr42() {
		return wr42;
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
	@Override
	public String toString() {
		return "WR [wr10=" + wr10 + ", wr21=" + wr21 + ", wr42=" + wr42 + "]";
	}
	
}
