package com.season.stock.data;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.season.stock.data.bean.Stock;
import com.season.stock.data.converter.JsonToStock;
import com.season.stock.data.dao.DailyDao;
import com.season.stock.data.utils.HttpUtils;

public class TestParseJson {
	
	@Test
	public void testShuorel(){
		Set<String> correct = new HashSet<String>();
		int count = 0;
		for (int code = 600000; code < 999999; code++) {
			String url = "http://shuorel.com/ws/share/daily/v1/" + code;
			String text = null;
			try {
				text = HttpUtils.getText(url);
			} catch (Exception e1) {
				e1.printStackTrace();
				continue;
			}
			if(!text.contains("empty")){
				Stock  stock = JsonToStock.parseShuorel(text);
				DailyDao.insert(stock);
				count ++;
				correct.add(code + "");
				System.out.println(code);
//				CodeDao.insert(code);
			}
			if(count > 1){
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(correct);
	}
	
	@Test
	public void testIFeng() throws ParseException{
//		IFengBaseDataCollect.collectData(600000, 999999, 1000);
//		IFengBaseDataCollect.updateExistStockData();
//		int count = 0;
//		for (int code = 600000; code < 999999; code++) {
//			String url = "http://api.finance.ifeng.com/akdaily/?code=sh" + code + "&type=last" ;
//			String text = null;
//			try {
//				text = HttpUtils.getText(url);
//				System.out.println(code + "返回");
//				FileUtils.writeStringToFile(new File("./file/" + code  + ".txt"), text);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//				continue;
//			}
//			List<Stock>  stocks = JsonToStock.parseIFeng(code + "", text);
//			Code codeObject = new Code();
//			codeObject.setCode(code + "");
//			if(stocks.isEmpty()){
//				codeObject.setIsStock(Code.NO);
//				CodeDao.insert(codeObject);
//			} else {
//				codeObject.setIsStock(Code.YES);
//				CodeDao.insert(codeObject);
//				
//			}
//			
//			if(stocks.isEmpty()){
//				continue;
//			}
//			
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//			if(stocks.size() > 0){
//				Stock stock = stocks.get(stocks.size() - 1);
//				String date = stock.getDate() + " 15:00";
//				
//				Date closeDate = null;
//				try {
//					closeDate = sdf.parse(date);
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				if(new Date().before(closeDate)){
//					stocks.remove(stocks.size() -1);
//				}
//			}
//			
//		
//			DailyDao.insert(stocks);
//				
//			
//			count ++;
//			if(count > 100){
//				break;
//			}
//		}
//		DailyDao.removeRepeatDaily();
	}
	
}
