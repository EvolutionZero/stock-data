package com.season.stock.data.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.season.stock.data.bean.Extreme;
import com.season.stock.data.bean.Stock;
import com.season.stock.data.dao.DailyDao;
import com.season.stock.data.dao.ExtremeDao;

public class ExtremeUtils {

	private static final Logger LOG = LoggerFactory.getLogger(ExtremeUtils.class);
	
	public static void main(String[] args) {
		List<Extreme> extremeLine = getExtremeLine("sh600000", Extreme.SHORT);
		System.out.println("线段：" + extremeLine);
	}
	
	public static List<Extreme> getExtremeLine(String code, int level){
		List<Stock> stocks = DailyDao.getStocks(code, Integer.MAX_VALUE);
		
		List<Extreme> extremes = ExtremeDao.query(code, level);
		
		List<Extreme> extremeLine = genExtremeTemplate(stocks);
		
		String start = extremes.get(0).getDate();
		
		if(extremes.size() > 1){
			int tempCurIdx = findTempCurIdx(extremeLine, start);
			for (int i = 0; i < extremes.size() - 1; i++) {
				Extreme curExt = extremes.get(i);
				Extreme nextExt = extremes.get(i + 1);
				
				int step = step(extremeLine, tempCurIdx, nextExt.getDate());
				double distance = (nextExt.getValue() - curExt.getValue()) / step;
				fill(extremeLine, curExt, nextExt, tempCurIdx, step, distance);
				
				// 模版指针跳转
				tempCurIdx += step;
			}
		}
		return extremeLine;
	}
	
	private static List<Extreme> genExtremeTemplate(List<Stock> stocks){
		List<Extreme> extremes = new LinkedList<Extreme>();
		for (Stock stock : stocks) {
			Extreme extreme = new Extreme();
			extreme.setCode(stock.getCode());
			extreme.setDate(stock.getDate());
			
			extremes.add(extreme);
		}
		
		return extremes;
	}
	
	
	private static int findTempCurIdx(List<Extreme> template, String start){
		int tempCurIdx = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date startDate = sdf.parse(start);
			for (Extreme extreme : template) {
				String date = extreme.getDate();
				Date extremeDate = sdf.parse(date);
				if(extremeDate.getTime() == startDate.getTime()){
					break;
				}
				tempCurIdx++;
			}
		} catch (ParseException e) {
			LOG.error("", e);
		}
		return tempCurIdx;
	}
	
	private static int step(List<Extreme> template, int cur, String date){
		int step = 0;
		for (int i = cur; i < template.size(); i++) {
			if(template.get(i).getDate().equals(date)){
				break;
			}
			step++;
		}
		return step;
	}
	
	private static void fill(List<Extreme> template, Extreme curExt, Extreme nextExt, int tempCurIdx, int step, double distance){
		try {
			if(tempCurIdx == template.size()){
				template.get(tempCurIdx - 1).setValue(curExt.getValue());
				
			} else if (tempCurIdx + step == template.size()){
				step--;
				// 一头一尾天填充固定值
				template.get(tempCurIdx).setValue(curExt.getValue());
				template.get(tempCurIdx + step).setValue(nextExt.getValue());
				
				// 填充中间缺少的值
				DecimalFormat df = new DecimalFormat("0.##");
				double head = curExt.getValue();
				for (int i = 1; i < step; i++) {
					double value = head + i * distance;
					template.get(tempCurIdx + i).setValue(Double.valueOf(df.format(value)));
				}
				
			} else {
				// 一头一尾天填充固定值
				template.get(tempCurIdx).setValue(curExt.getValue());
				template.get(tempCurIdx + step).setValue(nextExt.getValue());
				
				// 填充中间缺少的值
				DecimalFormat df = new DecimalFormat("0.##");
				double head = curExt.getValue();
				for (int i = 1; i < step; i++) {
					double value = head + i * distance;
					template.get(tempCurIdx + i).setValue(Double.valueOf(df.format(value)));
				}
			}
				
		} catch (Exception e) {
			LOG.error("", e);
		}
	}
}
