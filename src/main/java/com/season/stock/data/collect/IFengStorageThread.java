package com.season.stock.data.collect;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.season.stock.data.bean.Code;
import com.season.stock.data.bean.Stock;
import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.dao.DailyDao;

public class IFengStorageThread extends Thread{

	private static final Logger LOG = LoggerFactory.getLogger(IFengStorageThread.class);
	
	private boolean running = true;
	
	public void _stop(){
		running = false;
	}
	
	@Override
	public void run() {
		while(running){
			try {
				List<Stock> stocks = null;
				try {
					stocks = ResourcePool.STOCKS.poll(10, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(stocks != null && !stocks.isEmpty()){
					
					Stock stock = stocks.get(0);
					Code codeObject = new Code();
					codeObject.setCode(stock.getCode());
					codeObject.setIsStock(Code.YES);
					CodeDao.insert(codeObject);
					
					DailyDao.insert(stocks);
				} else {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				LOG.error("", e);
			}
		}
	}
	
}
