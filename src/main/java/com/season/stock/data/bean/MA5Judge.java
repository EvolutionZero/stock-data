//package com.season.stock.data.bean;
//
//import java.util.Arrays;
//
//import com.season.stock.data.utils.MathUtils;
//
//public class MA5Judge {
//	
//	private String code;
//	private double[] ma5s = new double[5];
//	private double[] diffs = new double[5];
//	private double[] lows = new double[5];
//	
//	public boolean isHaveButtomReverse(){
//		
//		boolean firstIncrease = MathUtils.isIncrease(ma5s[0], ma5s[1], ma5s[2]);
//		
//		boolean isHaveBottomReverse = false;
//		int reverseCnt = 0;
//		if(!firstIncrease){
//			
//			for (int i = 1; i < ma5s.length - 1; i++) {
//				boolean bottomReverse = MathUtils.isBottomReverse(ma5s[i - 1], ma5s[i], ma5s[i + 1]);
//				isHaveBottomReverse |= bottomReverse;
//				
//				if(bottomReverse){
//					reverseCnt++;
//				}
//				boolean topReverse = MathUtils.isTopReverse(ma5s[i - 1], ma5s[i], ma5s[i + 1]);
//				if(topReverse){
//					reverseCnt++;
//				}
//				
//			}
//			
//		}
//		return isHaveBottomReverse && reverseCnt == 1;
//	}
//	
//	
//	
//	public boolean isProbablyIncreate(int pre){
//		return  isMA5Increase(pre) && isLowBiggerThanMA5(pre);
//	}
//	
//	public boolean isDiffAllPositiveNumberAndIncrease(int pre){
//		boolean positive = true;
//		int start = 1;
//		if(pre + 1 < diffs.length){
//			start = pre + 1;
//		}
//		for (int i = start; i < diffs.length; i++) {
//			double cur = diffs[i];
//			double before = diffs[i - 1];
//			if(cur < 0 || before < 0 || cur < before){
//				positive = false;
//				break;
//			}
//		}
//		return positive;
//	}
//	
//	public boolean isMA5Increase(int pre){
//		boolean increase = true;
//		int start = 1;
//		if(pre + 1 < ma5s.length){
//			start = pre + 1;
//		}
//		for (int i = start; i < ma5s.length; i++) {
//			double cur = ma5s[i];
//			double before = ma5s[i - 1];
//			if(cur <= before){
//				increase = false;
//				break;
//			}
//		}
//		return increase;
//	}
//	
//	public boolean isLowBiggerThanMA5(int pre){
//		boolean bigger = true;
//		int start = 1;
//		if(pre + 1 < ma5s.length){
//			start = ma5s.length - pre;
//		}
//		for (int i = start; i < ma5s.length; i++) {
//			if(lows[i] < ma5s[i]){
//				bigger = false;
//				break;
//			}
//		}
//		return bigger;
//	}
//	
//	public static void main(String[] args) {
//		double[] ma5s = new double[]{6,2,3,2,5};
//		double[] lows = new double[]{-0.33399999999999963, -0.16000000000000014, -0.07600000000000051, 0.1479999999999997, 0.4280000000000008};
//		double[] diffs = new double[]{-0.33399999999999963, -0.16000000000000014, -0.07600000000000051, 0.1479999999999997, 0.4280000000000008};
//		
//		MA5Judge ma5Judge = new MA5Judge();
//		ma5Judge.setMa5s(ma5s);
//		ma5Judge.setDiffs(diffs);
//		ma5Judge.setLows(lows);
//		
//		System.out.println(ma5Judge.isLowBiggerThanMA5(3));
//		System.out.println(ma5Judge.isMA5Increase(3));
//		System.out.println(ma5Judge.isDiffAllPositiveNumberAndIncrease(3));
//		System.out.println(ma5Judge.isProbablyIncreate(3));
//		
//		System.out.println(ma5Judge.isHaveButtomReverse());
//		
//	}
//	
//	public double[] getMa5s() {
//		return ma5s;
//	}
//
//	public double[] getDiffs() {
//		return diffs;
//	}
//
//	public void setMa5s(double[] ma5s) {
//		this.ma5s = ma5s;
//	}
//
//	public void setDiffs(double[] diffs) {
//		this.diffs = diffs;
//	}
//
//	@Override
//	public String toString() {
//		return "MA5Judge [code=" + code + ", ma5s=" + Arrays.toString(ma5s)
//				+ ", diffs=" + Arrays.toString(diffs) + ", lows="
//				+ Arrays.toString(lows) + "]";
//	}
//
//	public String getCode() {
//		return code;
//	}
//
//	public double[] getLows() {
//		return lows;
//	}
//
//	public void setCode(String code) {
//		this.code = code;
//	}
//
//	public void setLows(double[] lows) {
//		this.lows = lows;
//	}
//
//}
