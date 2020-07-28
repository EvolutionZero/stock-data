package com.season.stock.data.envm;

public enum CrossType {
	
	GOLDEN(1),DEATH(-1);
	
	private int type;
	CrossType(int type){
		this.type = type;
	}
	public int getType() {
		return type;
	}
}
