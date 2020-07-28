package com.season.stock.data.bean;

public class Extreme {

	public static final int SHORT = 1;
	public static final int MIDDLE = 2;
	public static final int LONG = 3;
	
	public static final int TYPE_LOWEST = 0;
	public static final int TYPE_HIGHEST = 1;

	private String code;
	private double value;
	private String date;
	private int level;
	private int type;

	public String getCode() {
		return code;
	}

	public double getValue() {
		return value;
	}

	public String getDate() {
		return date;
	}

	public int getLevel() {
		return level;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Extreme [code=" + code + ", value=" + value + ", date=" + date
				+ ", level=" + level + ", type=" + type + "]";
	}

}
