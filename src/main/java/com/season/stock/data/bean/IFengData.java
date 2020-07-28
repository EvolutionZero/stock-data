package com.season.stock.data.bean;

public class IFengData {

	private String code;
	private String data;

	public String getCode() {
		return code;
	}

	public String getData() {
		return data;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "IFengData [code=" + code + ", data=" + data + "]";
	}
}
