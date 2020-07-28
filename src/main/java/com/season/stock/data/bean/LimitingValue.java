package com.season.stock.data.bean;

public class LimitingValue {

	private double highest;
	private double lowest;

	public double getHighest() {
		return highest;
	}

	public double getLowest() {
		return lowest;
	}

	public void setHighest(double highest) {
		this.highest = highest;
	}

	public void setLowest(double lowest) {
		this.lowest = lowest;
	}

	@Override
	public String toString() {
		return "LimitingValue [highest=" + highest + ", lowest=" + lowest + "]";
	}

}
