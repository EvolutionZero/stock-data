package com.season.stock.data.app;

import com.season.stock.data.collect.IFengAnalyseThread;
import com.season.stock.data.collect.IFengCollectThread;
import com.season.stock.data.collect.IFengStorageThread;
import com.season.stock.data.envm.Exchange;
import com.season.stock.data.utils.LogUtils;

public class IFengCollectController {

	public static void main(String[] args) {
		LogUtils.loadLogBackConfig();
		collectSH();
		collectSZ();
		
		int keep = 30;
		new IFengAnalyseThread(keep).start();
		new IFengStorageThread().start();
	}
	
	
	private static void collectSH(){
		int start = 140000;
		int end = 999999;
		int count = 10000;
		int threadNum = 4;
		
		for (int i = 0; i < threadNum; i++) {
			new IFengCollectThread(start, end, count/threadNum , threadNum, i, Exchange.SH).start();
		}
	}
	
	private static void collectSZ(){
		int start = 140000;
		int end = 999999;
		int count = 10000;
		int threadNum = 4;
		
		for (int i = 0; i < threadNum; i++) {
			new IFengCollectThread(start, end, count/threadNum , threadNum, i, Exchange.SZ).start();
		}
	}
}
