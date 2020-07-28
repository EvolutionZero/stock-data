package com.season.stock.data.converter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.season.stock.data.bean.Stock;

public class JsonToStock {

	private static final Logger LOG = LoggerFactory.getLogger(JsonToStock.class);
	
	@SuppressWarnings("unchecked")
	public static Stock parseShuorel(String json) {
		// String CallStack=getCallStack();
		Map<?, ?> map = null;
		try {
			org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
			map = mapper.readValue(json, LinkedHashMap.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Stock stock = new Stock();
		if(map != null){
			List<Object> data = map.get("data") == null ? new LinkedList<Object>() : (List<Object>)map.get("data");
			if(!data.isEmpty()){
				Map<String, Object> kvs = (Map<String, Object>)data.get(0);
				String code = kvs.get("code") == null ? null : (String)kvs.get("code");
				String date = kvs.get("date") == null ? null : (String)kvs.get("date");
				Double open = kvs.get("open") == null ? null : (Double)kvs.get("open");
				Double high = kvs.get("high") == null ? null : (Double)kvs.get("high");
				Double close = kvs.get("close") == null ? null : (Double)kvs.get("close");
				Double low = kvs.get("low") == null ? null : (Double)kvs.get("low");
				Integer volume = kvs.get("volume") == null ? null : (Integer)kvs.get("volume");
//				Object amount = kvs.get("amount") == null ? null : kvs.get("amount");
				
				stock.setCode(code);
				stock.setOpen(open);
				stock.setDate(date);
				stock.setHigh(high);
				stock.setLow(low);
				stock.setVolume(volume);
				stock.setClose(close);
//				stock.setAmount(amount);
			}
		}
		System.out.println(stock);
		return stock;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Stock> parseIFeng(String code, String json){
		List<Stock> stocks = new LinkedList<Stock>();
		try {
			Map<?, ?> map = null;
			try {
				org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
				map = mapper.readValue(json, LinkedHashMap.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Object record = map.get("record");
			if(record == null || "{}".equals(record.toString())){
				return stocks;
			}
			List<List<Object>> datas = (List<List<Object>> )record;
			for (List<Object> data : datas) {
				Stock stock = new Stock();
				
				String date = (String)data.get(0);
				String open = (String)data.get(1);
				String high = (String)data.get(2);
				String close = (String)data.get(3);
				String low = (String)data.get(4);
				String change = (String)data.get(5);
				String valueChange = (String)data.get(6);
				String precentChange = (String)data.get(7);
				String ma5 = (String)data.get(8);
				String ma10 = (String)data.get(9);
				String ma20 = (String)data.get(10);
				String vma5 = (String)data.get(11);
				String vma10 = (String)data.get(12);
				String vma20 = (String)data.get(13);
				
				String turnover = "-1";
				if(data.size() > 14){
					turnover = (String)data.get(14);
					
				}
				
				stock.setCode(code.replace(",", ""));
				stock.setDate(date.replace(",", ""));
				stock.setOpen(Double.valueOf(open.replace(",", "")));
				stock.setHigh(Double.valueOf(high.replace(",", "")));
				stock.setClose(Double.valueOf(close.replace(",", "")));
				stock.setLow(Double.valueOf(low.replace(",", "")));
				stock.setChange(Double.valueOf(change.replace(",", "")));
				stock.setValueChange(Double.valueOf(valueChange.replace(",", "")));
				stock.setPrecentChange(Double.valueOf(precentChange.replace(",", "")));
				stock.setMa5(Double.valueOf(ma5.replace(",", "")));
				stock.setMa10(Double.valueOf(ma10.replace(",", "")));
				stock.setMa20(Double.valueOf(ma20.replace(",", "")));
				stock.setVma5(Double.valueOf(vma5.replace(",", "")));
				stock.setVma10(Double.valueOf(vma10.replace(",", "")));
				stock.setVma20(Double.valueOf(vma20.replace(",", "")));
				stock.setTurnover(Double.valueOf(turnover.replace(",", "")));
				
				stocks.add(stock);
			}
		} catch (Exception e) {
			LOG.error("code: {}", code);
			LOG.error("json: {}", json);
			LOG.error("", e);
		}
		return stocks;
	}
//	
//	public static void main(String[] args){
//		try {
//			String url = "http://api.finance.ifeng.com/akdaily/?code=sh601989&type=last";
//			String json = FileUtils.readFileToString(new File("./600600.txt"));
//			List<Stock> parseIFeng = parseIFeng("600600", json);
//			System.out.println(parseIFeng);
//			DailyDao.insert(parseIFeng);
//			System.out.println(parseIFeng.size());
//			System.out.println("结束");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
