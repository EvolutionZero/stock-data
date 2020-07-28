package com.season.stock.data.app;

import java.text.DecimalFormat;
import java.util.List;

import com.season.stock.data.dao.DailyDao;
import com.season.stock.data.dao.ForecastDao;
import com.season.stock.data.dao.SysQuotaDao;

public class SysQuota {

	public static void main(String[] args) {
		List<String> allTradeDay = DailyDao.getAllTradeDay();
		for (String day : allTradeDay) {
			List<Double> nxDyFcstPer = ForecastDao.getNxDyFcstPer(day);
			double accuracy = calAccuracy(nxDyFcstPer);
			SysQuotaDao.insert(day, accuracy);
			System.out.println(day + ":" + accuracy + "%");
		}
	}
	
	private static double calAccuracy(List<Double> pers){
		DecimalFormat df = new DecimalFormat("0.##");
		int v = 0;
		for (Double per : pers) {
			if(per > 0){
				v++;
			}
		}
		return v == 0 ? 0 : Double.valueOf(df.format(v * 1.0 / pers.size() * 100));
	}
}
