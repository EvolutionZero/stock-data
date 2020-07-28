package com.season.stock.data.app;

import java.util.List;

import com.season.stock.data.collect.IFengAnalyseThread;
import com.season.stock.data.collect.IFengStorageThread;
import com.season.stock.data.collect.IFengSupplementCollectThread;
import com.season.stock.data.dao.CodeDao;

public class IFengSupplementCollectController {

	public static void main(String[] args) {
		done();
	}
	
	public static void done() {
		List<String> codes = CodeDao.queryAllCodeOnExchange();
		int threadNum = 4;

		for (int i = 0; i < threadNum; i++) {
			new IFengSupplementCollectThread(codes, threadNum, i).start();
		}
		int keep = 30;
		new IFengAnalyseThread(keep).start();
		new IFengStorageThread().start();
	}
}
