//package com.season.stock.data.candle;
//
//import org.junit.Test;
//
//import com.season.stock.data.bean.Stock;
//import com.season.stock.data.candle.CoreCalculation;
//
//public class TestCoreCalculation {
//
//	@Test
//	public void testEntitySize(){
//		Stock stock = new Stock();
//		
//		stock.setOpen("16.00");
//		stock.setClose("16.01");
//		stock.setHigh("16.16");
//		stock.setLow("15.87");
//		
//		System.out.println("------------------长腿十字线-----------------------");
//		System.out.println("实体大小    ：" + CoreCalculation.entitySize(stock));
//		System.out.println("上影线大小：" + CoreCalculation.upperShadowSize(stock));
//		System.out.println("下影线大小：" + CoreCalculation.lowShadowSize(stock));
//		System.out.println("影线占比：" + CoreCalculation.shadowSize(stock));
//		System.out.println(CrossLine.isLongLegCrossLine(stock));
//		System.out.println("------------------------------------------------\r\n");
//
//		stock.setOpen("16.00");
//		stock.setClose("16.00");
//		stock.setHigh("16.10");
//		stock.setLow("16.00");
//		
//		System.out.println("------------------墓碑十字线-----------------------");
//		System.out.println("实体大小    ：" + CoreCalculation.entitySize(stock));
//		System.out.println("上影线大小：" + CoreCalculation.upperShadowSize(stock));
//		System.out.println("下影线大小：" + CoreCalculation.lowShadowSize(stock));
//		System.out.println("影线占比：" + CoreCalculation.shadowSize(stock));
//		System.out.println(CrossLine.isTombstoneCrossLine(stock));
//		System.out.println("------------------------------------------------\r\n");
//		
//		stock.setOpen("15.29");
//		stock.setClose("15.30");
//		stock.setHigh("15.33");
//		stock.setLow("15.10");
//		
//		System.out.println("------------------蜻蜓十字线-----------------------");
//		System.out.println("实体大小    ：" + CoreCalculation.entitySize(stock));
//		System.out.println("上影线大小：" + CoreCalculation.upperShadowSize(stock));
//		System.out.println("下影线大小：" + CoreCalculation.lowShadowSize(stock));
//		System.out.println("影线占比：" + CoreCalculation.shadowSize(stock));
//		System.out.println(CrossLine.isDragonflyCrossLine(stock));
//		System.out.println("------------------------------------------------\r\n");
//		
//		stock.setOpen("15.58");
//		stock.setClose("15.38");
//		stock.setHigh("15.58");
//		stock.setLow("15.30");
//		
//		System.out.println("------------------看跌捉腰带线-----------------------");
//		System.out.println("实体大小    ：" + CoreCalculation.entitySize(stock));
//		System.out.println("上影线大小：" + CoreCalculation.upperShadowSize(stock));
//		System.out.println("下影线大小：" + CoreCalculation.lowShadowSize(stock));
//		System.out.println("影线占比：" + CoreCalculation.shadowSize(stock));
//		System.out.println("------------------------------------------------\r\n");
//		
//		
//		stock.setOpen("14.91");
//		stock.setClose("15.17");
//		stock.setHigh("15.32");
//		stock.setLow("14.91");
//		
//		System.out.println("------------------看涨捉腰带线-----------------------");
//		System.out.println("实体大小    ：" + CoreCalculation.entitySize(stock));
//		System.out.println("上影线大小：" + CoreCalculation.upperShadowSize(stock));
//		System.out.println("下影线大小：" + CoreCalculation.lowShadowSize(stock));
//		System.out.println("影线占比：" + CoreCalculation.shadowSize(stock));
//		System.out.println(BullishBelt.isBullishBelt(stock));
//		System.out.println("------------------------------------------------\r\n");
//		
//		
//		stock.setOpen("15.15");
//		stock.setClose("15.21");
//		stock.setHigh("15.33");
//		stock.setLow("15.15");
//		System.out.println("------------------锤子线-----------------------");
//		System.out.println("实体大小    ：" + CoreCalculation.entitySize(stock));
//		System.out.println("上影线大小：" + CoreCalculation.upperShadowSize(stock));
//		System.out.println("下影线大小：" + CoreCalculation.lowShadowSize(stock));
//		System.out.println("影线占比：" + CoreCalculation.shadowSize(stock));
//		System.out.println("------------------------------------------------\r\n");
//	}
//}
