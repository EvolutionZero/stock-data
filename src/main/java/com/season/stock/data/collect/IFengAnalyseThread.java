package com.season.stock.data.collect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.season.stock.data.bean.IFengData;
import com.season.stock.data.bean.Stock;
import com.season.stock.data.converter.JsonToStock;
import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.dao.DailyDao;
import com.season.stock.data.envm.IsStock;
import com.season.stock.data.envm.Status;

public class IFengAnalyseThread extends Thread{
	
	private static final Logger LOG = LoggerFactory.getLogger(IFengAnalyseThread.class);
	
	private boolean running = true;
	
	// 保留最新多少天
	private int keep;
	
	public IFengAnalyseThread(){
		
	}
	
	public IFengAnalyseThread(int keep) {
		this.keep = keep;
	}
	
	public void _stop(){
		running = false;
	}
	
	@Override
	public void run() {
		while(running){
			try {
				int cacheSize = ResourcePool.STOCKS.size();
				if(cacheSize > 20){
					Thread.sleep(2 * 1000);
					LOG.info("入库缓存数量为[{}],暂停2秒", cacheSize);
				}
				IFengData iFengData = null;
				try {
					iFengData = ResourcePool.DATAS.poll(10, TimeUnit.MILLISECONDS);
					if(iFengData != null){
						List<Stock> stocks = anaylse(iFengData);
						ResourcePool.STOCKS.add(stocks);
					} else {
						Thread.sleep(100);
					}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} catch (Exception e) {
				LOG.error("", e);
			}
		}
	}
	
	public List<Stock> anaylse(IFengData iFengData){
		List<Stock>  stocks = new LinkedList<Stock>();
		if(iFengData != null){
			stocks = JsonToStock.parseIFeng(iFengData.getCode(), iFengData.getData());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if(stocks.size() > 0){
				Stock stock = stocks.get(stocks.size() - 1);
				String date = stock.getDate() + " 15:00";
				
				Date closeDate = null;
				try {
					closeDate = sdf.parse(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(new Date().before(closeDate)){
					stocks.remove(stocks.size() -1);
				}
				
			} else {
				CodeDao.updateIsStock(iFengData.getCode(), IsStock.NO);
				
			}
			
			SimpleDateFormat iFengSdf = new SimpleDateFormat("yyyy-MM-dd");
			String lastUpdateTime = DailyDao.getLastUpdateTime(iFengData.getCode());
			LOG.info("code = " + iFengData.getCode() + " , " + "lastUpdateTime = " + lastUpdateTime);
			if(!"".equals(lastUpdateTime)){
				List<Stock> completStocks = new LinkedList<Stock>();
				for (Stock stock : stocks) {
					try {
						if(iFengSdf.parse(stock.getDate()).after(iFengSdf.parse(lastUpdateTime))){
							completStocks.add(stock);
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				stocks = completStocks;
			} 
			try {
				if(iFengSdf.parse(lastUpdateTime).before(getStopTime())){
					CodeDao.updateStatus(iFengData.getCode(), Status.STOP);
					LOG.info("更新[{}]为停牌", iFengData.getCode());
				} else {
					CodeDao.updateStatus(iFengData.getCode(), Status.EXCHANGE);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stocks = getKeeps(stocks);
		}
		return stocks;
	}
	
	/**
	 * 停牌检测时间
	 * @return
	 */
	private static Date getStopTime() {
		Calendar cal = Calendar.getInstance();

		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
		return cal.getTime();
	}
	
	private List<Stock> getKeeps(List<Stock> stocks){
		List<Stock> keeps = new LinkedList<Stock>();
		int start = 0;
		if(keep > 0 ){
			start = stocks.size() - keep;
			if(start < 0){
				start = 0;
			}
			
		}
		for (int idx = start ; idx < stocks.size(); idx++) {
			Stock stock = stocks.get(idx);
			keeps.add(stock);
		}
		return keeps;
	}
	
	public int getKeep() {
		return keep;
	}

	public void setKeep(int keep) {
		this.keep = keep;
	}

}
