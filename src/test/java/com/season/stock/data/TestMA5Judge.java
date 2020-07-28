//package com.season.stock.data;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import org.junit.Test;
//
//import com.season.stock.data.bean.MA5Judge;
//import com.season.stock.data.dao.CodeDao;
//import com.season.stock.data.dao.DailyDao;
//
//public class TestMA5Judge {
//
//	@Test
//	public void testMA5Increase(){
//		List<String> probablies = new LinkedList<String>();
//		List<String> codes = CodeDao.queryAllCode();
//		for (String code : codes) {
////			if(!DailyDao.isNormal(code, 4)){
////				continue;
////			}
//			MA5Judge MA5Judge = DailyDao.queryMA5Judge(code);
//			System.out.println(code);
//			if(MA5Judge.isProbablyIncreate(3)){
//				System.out.println(MA5Judge.toString());
//				probablies.add(code);
//			}
//		}
//		System.out.println(probablies);
//		System.out.println(probablies.size());
//	}
//	
//	@Test
//	public void testButtomReverse(){
//		List<String> probablies = new LinkedList<String>();
//		List<String> codes = CodeDao.queryAllCode();
//		for (String code : codes) {
//			if(!DailyDao.isNormal(code, 4)){
//				continue;
//			}
//			MA5Judge MA5Judge = DailyDao.queryMA5Judge(code);
//			if(MA5Judge.isHaveButtomReverse()){
//				System.out.println(MA5Judge.toString());
//				probablies.add(code);
//			}
//		}
//		System.out.println(probablies);
//		System.out.println(probablies.size());
//	}
//	
//}
