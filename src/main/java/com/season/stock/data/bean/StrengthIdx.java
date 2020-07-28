package com.season.stock.data.bean;

public class StrengthIdx {

	private String name;
	private String date;
	private int riseNum;
	private String riseTotalRate;
	private String riseProbabily;
	private String strength;

	public String getName() {
		return name;
	}

	public String getDate() {
		return date;
	}

	public int getRiseNum() {
		return riseNum;
	}

	public String getRiseTotalRate() {
		return riseTotalRate;
	}

	public String getRiseProbabily() {
		return riseProbabily;
	}

	public String getStrength() {
		return strength;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setRiseNum(int riseNum) {
		this.riseNum = riseNum;
	}

	public void setRiseTotalRate(String riseTotalRate) {
		this.riseTotalRate = riseTotalRate;
	}

	public void setRiseProbabily(String riseProbabily) {
		this.riseProbabily = riseProbabily;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	@Override
	public String toString() {
		return "StrengthIdx [name=" + name + ", date=" + date + ", riseNum="
				+ riseNum + ", riseTotalRate=" + riseTotalRate
				+ ", riseProbabily=" + riseProbabily + ", strength=" + strength
				+ "]";
	}

}
