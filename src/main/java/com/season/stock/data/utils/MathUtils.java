package com.season.stock.data.utils;

import com.season.stock.data.envm.CrossType;
import com.season.stock.data.envm.ReverseType;


public class MathUtils {

	
	/**
	 * 总体上升
	 * @param datas
	 * @return
	 */
	public static boolean isIncrease(double... datas){
		double deta = 0;
		for (int i = 1; i < datas.length; i++) {
			deta += datas[i] - datas[i -1];
		}
		return deta > 0 ? true : false;
	}
	
	/**
	 * 总体下降
	 * @param datas
	 * @return
	 */
	public static boolean isDecrease(double... datas){
		double deta = 0;
		for (int i = 1; i < datas.length; i++) {
			deta += datas[i] - datas[i -1];
		}
		return deta < 0 ? true : false;
	}
	
	/**
	 * 下降力度, 1 - 上升力度
	 * @param datas
	 * @return
	 */
	public static double dropStrength(double... datas){
		return 1 - riseStrength(datas);
	}
	
	/**
	 * 上升力度, 上升路程/总路程
	 * @param datas
	 * @return
	 */
	public static double riseStrength(double... datas){
		double strength = 0;
		double deta = 0;
		double totalDistance = 0;
		double riseDistance = 0;
		for (int i = 1; i < datas.length; i++) {
			deta += datas[i] - datas[i -1];
			totalDistance += Math.abs(deta);
			riseDistance += deta > 0 ? deta : 0	;
		}
		strength = riseDistance / totalDistance;
		return strength;
	}
	
	/**
	 * 取最大值
	 * @param values
	 * @return
	 */
	public static double getMaxValue(double... values){
		double max = Double.MIN_VALUE;
		for (double value : values) {
			max = value > max ? value : max;
		}
		return max;
	}
	
	/**
	 * 取最小值
	 * @param values
	 * @return
	 */
	public static double getMinValue(double... values){
		double min = Double.MAX_VALUE;
		for (double value : values) {
			min = value < min ? value : min;
		}
		return min;
	}
	
	/**
	 * 取差值线
	 * @param x
	 * @param y
	 * @return
	 */
	public static double[] getDetaLine(double[] x, double[] y){
		int size = Math.min(x.length, y.length);
		double[] detaLine = new double[size];
		for (int i = 0; i < size; i++) {
			detaLine[i] = x[i] - y[i]; 
		}
		return detaLine;
	}
	
	/**
	 * 取差值线
	 * @param x
	 * @param y
	 * @return
	 */
	public static double[] getDetaLine(double[] x){
		int size = x.length - 1 ;
		if(size < 0){
			size = 0;
		}
		double[] detaLine = new double[size];
		for (int i = 1; i < size; i++) {
			detaLine[i - 1] = x[i] - x[i - 1]; 
		}
		return detaLine;
	}
	
	/**
	 * 单调递减
	 * @param datas
	 * @return
	 */
	public static boolean isDrabDecrease(double... datas){
		double deta = 0;
		boolean drabDecrease = true;
		for (int i = 1; i < datas.length; i++) {
			deta = datas[i] - datas[i -1];
			if(deta > 0){
				drabDecrease = false;
				break;
			}
		}
		return drabDecrease;
	}
	
	/**
	 * 单调递增
	 * @param datas
	 * @return
	 */
	public static boolean isDrabIncrease(double... datas){
		double deta = 0;
		boolean drabIncrease = true;
		for (int i = 1; i < datas.length; i++) {
			deta = datas[i] - datas[i -1];
			if(deta < 0){
				drabIncrease = false;
				break;
			}
		}
		return drabIncrease;
	}
	
	/**
	 * A线是否全部在B线之上
	 * @param up
	 * @param down
	 * @return
	 */
	public static boolean above(double[] up ,double[] down){
		boolean isAbove = true;
		int size = Math.min(up.length, down.length);
		for (int i = 0; i < size; i++) {
			if(up[i] < down[i]){
				isAbove = false;
				break;
			}
		}
		return isAbove;
	}
	
	/**
	 * 获取最近的数据
	 * @param datas
	 * @param level
	 * @return
	 */
	public static double[] getLastShortLevelData(double[] datas ,int level){
		double[] result = new double[level];
		for (int i = datas.length - level, cnt = 0; i < datas.length; i++, cnt++) {
			result[cnt] = datas[i];
		}
		return result;
	}
	
	/**
	 * 计算标准差
	 * @param avg
	 * @param datas
	 * @return
	 */
	public static double getStandardDeviation(double avg , double[] datas){
		double standardDeviation = 0;
		if(datas.length > 0){
			double total = 0;
			for (double data : datas) {
				total += Math.pow(data - avg, 2);
			}
			standardDeviation = Math.sqrt(total / datas.length);
		}
		return standardDeviation;
	}
	
	/**
	 * 是否黄金交叉
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean isGoldenCross(double[] x , double[] y){
		boolean isGoldenCross = false;
		double[] detaLine = getDetaLine(x, y);
		int[] crossLine = crossLine(detaLine);
		int crossScore = 0;
		for (int cross : crossLine) {
			if(cross == CrossType.GOLDEN.getType()){
				crossScore++;
			}
			if(cross == CrossType.DEATH.getType()){
				crossScore--;
			}
		}
		if(crossScore > 0){
			int[] reverseLine = reverseLine(detaLine);
			int reverseScore = 0;
			for (int reverse : reverseLine) {
				if(reverse == ReverseType.BOTTOM.getType()){
					reverseScore++;
				}
				if(reverse == ReverseType.TOP.getType()){
					reverseScore--;
				}
			}
			if(reverseScore >= 0){
				isGoldenCross = true;
			}
		}
		return isGoldenCross;
	}
	
	/**
	 * 是否死亡交叉
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean isDeathCross(double[] x , double[] y){
		boolean isDeathCross = false;
		double[] detaLine = getDetaLine(x, y);
		int[] crossLine = crossLine(detaLine);
		int crossScore = 0;
		for (int cross : crossLine) {
			if(cross == CrossType.DEATH.getType()){
				crossScore++;
			}
			if(cross == CrossType.GOLDEN.getType()){
				crossScore--;
			}
		}
		if(crossScore > 0){
			int[] reverseLine = reverseLine(detaLine);
			int reverseScore = 0;
			for (int reverse : reverseLine) {
				if(reverse == ReverseType.TOP.getType()){
					reverseScore++;
				}
				if(reverse == ReverseType.BOTTOM.getType()){
					reverseScore--;
				}
			}
			if(reverseScore >= 0){
				isDeathCross = true;
			}
		}
		return isDeathCross;
	}
	
	/**
	 * 获取交叉类型线
	 * @param detaPrices
	 * @return
	 */
	public static int[] crossLine(double[] detaPrices){
		int[] crossLine = new int[detaPrices.length]; 
		for (int i = 1; i < detaPrices.length; i++) {
			double before = detaPrices[i -1];
			double cur = detaPrices[i];
			if(before < 0 && cur > 0 ){
				crossLine[i] = CrossType.GOLDEN.getType();
				
			} else if(before > 0 && cur < 0 ){
				crossLine[i] = CrossType.DEATH.getType();
			}
		}
		return crossLine;
	}
	
	/**
	 * 获取反转线
	 * @param derivative
	 * @return
	 */
	public static int[] reverseLine(double[] line){
		double[] derivative = getDetaLine(line);
		int[] reverseLine = new int[derivative.length]; 
		for (int i = 1; i < derivative.length; i++) {
			double before = derivative[i -1];
			double cur = derivative[i];
			if(before > 0 && cur < 0){
				reverseLine[i] = ReverseType.TOP.getType();
				
			} else if(before < 0 && cur > 0 ){
				reverseLine[i] = ReverseType.BOTTOM.getType();
			}
		}
		return reverseLine;
	}
	
	public static boolean above(double[] line, double reference){
		boolean isAbove = true;
		for (double data : line) {
			if(data < reference){
				isAbove = false;
				break;
			}
		}
		return isAbove;
	}
	
	public static boolean isMtop(double[] line){
		boolean isMtop = false;
		int[] model = new int[]{ReverseType.TOP.getType(), ReverseType.BOTTOM.getType(), ReverseType.TOP.getType()};
		int[] reverseLine = reverseLine(line);
		int mIdx = 0;
		for (int i = 0; i < reverseLine.length; i++) {
			if(reverseLine[i] == model[mIdx]){
				mIdx++;
				if(mIdx == 3){
					isMtop = true;
					break;
				}
			}
		}
		return isMtop;
	}
	
	public static boolean isThreeTop(double[] line){
		boolean isMtop = false;
		int[] model = new int[]{ReverseType.TOP.getType(), ReverseType.BOTTOM.getType(), ReverseType.TOP.getType(),
				ReverseType.BOTTOM.getType(), ReverseType.TOP.getType()};
		int[] reverseLine = reverseLine(line);
		int mIdx = 0;
		for (int i = 0; i < reverseLine.length; i++) {
			if(reverseLine[i] == model[mIdx]){
				mIdx++;
				if(mIdx == 5){
					isMtop = true;
					break;
				}
			}
		}
		return isMtop;
	}
	
	public static void main(String[] args) {
		double[] x = new double[]{1,5,9,5,10,3,1};
		System.out.println(isMtop(x));
	}
}
