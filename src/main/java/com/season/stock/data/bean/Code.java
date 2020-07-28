package com.season.stock.data.bean;

public class Code {

	public static final String YES = "Y";
	public static final String NO = "N";

	private String code;
	private String isStock;
	private String name;
	private String plate;

	public String getCode() {
		return code;
	}

	public String getIsStock() {
		return isStock;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setIsStock(String isStock) {
		this.isStock = isStock;
	}

	public String getName() {
		return name;
	}

	public String getPlate() {
		return plate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	@Override
	public String toString() {
		return "Code [code=" + code + ", isStock=" + isStock + ", name=" + name
				+ ", plate=" + plate + "]";
	}

}
