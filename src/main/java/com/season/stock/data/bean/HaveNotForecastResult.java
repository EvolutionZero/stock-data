package com.season.stock.data.bean;

public class HaveNotForecastResult {
	
	private String id;
	private String code;
	private String forecastDate;

	public String getCode() {
		return code;
	}

	public String getForecastDate() {
		return forecastDate;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setForecastDate(String forecastDate) {
		this.forecastDate = forecastDate;
	}

	@Override
	public String toString() {
		return "HaveNotForecastResult [id=" + id + ", code=" + code
				+ ", forecastDate=" + forecastDate + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
