package com.season.stock.data.bean;

public class Category {

	private String code;
	private String category;

	public String getCode() {
		return code;
	}

	public String getCategory() {
		return category;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Category [code=" + code + ", category=" + category + "]";
	}
}
