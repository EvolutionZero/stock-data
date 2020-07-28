package com.season.stock.data.bean;

/**
 * 
 * <PRE>
 * 布林带
 * </PRE>
 */
public class Boll {

	private double upper;
	private double middle;
	private double lower;
	private double percentB;

	public double getUpper() {
		return upper;
	}

	public double getMiddle() {
		return middle;
	}

	public double getLower() {
		return lower;
	}

	public void setUpper(double upper) {
		this.upper = upper;
	}

	public void setMiddle(double middle) {
		this.middle = middle;
	}

	public void setLower(double lower) {
		this.lower = lower;
	}

	@Override
	public String toString() {
		return "Boll [upper=" + upper + ", middle=" + middle + ", lower="
				+ lower + ", percentB=" + percentB + "]";
	}

	public double getPercentB() {
		return percentB;
	}

	public void setPercentB(double percentB) {
		this.percentB = percentB;
	}

}
