package com.season.stock.data.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayUtils {

	private static final Logger LOG = LoggerFactory.getLogger(DayUtils.class);
	
	public static List<String> after(String standard, List<String> days){
		List<String> needFcstTradeDay = new LinkedList<String>();
		if(!"".equals(standard)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (String day : days) {
				try {
					if(sdf.parse(day).after(sdf.parse(standard))){
						needFcstTradeDay.add(day);
					}
				} catch (ParseException e) {
					LOG.error("", e);
				}
			}
		}
		return needFcstTradeDay;
	}
	
}
