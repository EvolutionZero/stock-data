package com.season.stock.data;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.season.stock.data.app.DaliyTask;
import com.season.stock.data.utils.LogUtils;

public class Main {
	
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws InterruptedException {
		LogUtils.loadLogBackConfig();
		LOG.info("开始启动程序！");
		long loop = 24 * 60 * 60 * 1000;
		DaliyTask daliyTask = new DaliyTask();

		Timer timer = new Timer();
		timer.schedule(daliyTask, getExecuteTime() , loop);
		LOG.info("成功启动程序！");
		while(true){
			Thread.sleep(60 * 1000);
			LOG.info("程序运行中！");
		}
		
	}
	
	private static Date getExecuteTime() {
		Calendar cal = Calendar.getInstance();

		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0 + 15);
		cal.set(Calendar.MINUTE, 0 + 10);
		cal.set(Calendar.SECOND, 0);
		Date time = cal.getTime();
		if(time.before(new Date())){
			cal.setTime(new Date());
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
			cal.set(Calendar.HOUR_OF_DAY, 0 + 15);
			cal.set(Calendar.MINUTE, 0 + 10);
			cal.set(Calendar.SECOND, 0);
		}
		return cal.getTime();
	}
}
