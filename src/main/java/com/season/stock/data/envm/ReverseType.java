package com.season.stock.data.envm;

public enum ReverseType {
	
	TOP(1),BOTTOM(-1);
	
	private int type;
	ReverseType(int type){
		this.type = type;
	}
	public int getType() {
		return type;
	}
}
