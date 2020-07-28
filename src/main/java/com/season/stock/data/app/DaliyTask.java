package com.season.stock.data.app;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.season.stock.data.Main;
import com.season.stock.data.bean.IFengData;
import com.season.stock.data.bean.Stock;
import com.season.stock.data.collect.IFengAnalyseThread;
import com.season.stock.data.collect.IFengSupplementCollectThread;
import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.dao.DailyDao;
import com.season.stock.data.utils.LogUtils;

public class DaliyTask  extends TimerTask{

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		LogUtils.loadLogBackConfig();
		new DaliyTask().run();
	}
	
	@Override
	public void run() {
		Date start = new Date();
		LOG.info("开始执行每日任务！");
		List<IFengSupplementCollectThread> collectThreads = new LinkedList<IFengSupplementCollectThread>();
		List<String> codes = CodeDao.queryAllCodeOnExchange();
		int threadNum = 2;

		for (int i = 0; i < threadNum; i++) {
			IFengSupplementCollectThread collect = new IFengSupplementCollectThread(codes, threadNum, i);
			collect.start();
			
			collectThreads.add(collect);
		}
		LOG.info("等待采集完成！");
		while(true){
			if(isFinish(collectThreads)){
				break;
			}
		}
		LOG.info("采集完成！");
		DailyDao.removeRepeatDaily();
		DailyDao.removeRepeatForecast();
		LOG.info("去重完成！");
		try {
			File dir = new File(System.getProperty("user.dir")+"/file");
			LOG.info("开始处理文件！[{}]", dir.getName());
			File[] files = dir.listFiles();
			int keep = 365 * 5;
			IFengAnalyseThread iFengAnalyseThread = new IFengAnalyseThread(keep);
			for (File file : files) {
				try {
					LOG.info("处理文件[{}]!", file.getName());
					String code = file.getName().replace(".txt", "");
					String data = FileUtils.readFileToString(file);
					
					IFengData iFengData = new IFengData();
					iFengData.setCode(code);
					iFengData.setData(data);
					
					List<Stock> stocks = iFengAnalyseThread.anaylse(iFengData);
					DailyDao.insert(stocks);
					
				} catch (IOException e) {
					LOG.error("", e);
				}
			}
			CompleteWestenIndex.done();
			LOG.info("开始分析！");
			CandleAnalysist.done();
			LOG.info("分析完成！");
			
			CollectIndustryAndCategory.execute();
			StrengthStat.execute();
			FindExtreme.findAreaExtreme(30);
			
			Date end = new Date();
			LOG.info("完成每日任务！耗时[{}]秒", (end.getTime() - start.getTime()) / 1000);
		} catch (Exception e) {
			LOG.error("", e);
		}
	}
	
	private boolean isFinish(List<IFengSupplementCollectThread> threads){
		boolean finish = true;
		for (IFengSupplementCollectThread collect : threads) {
			finish &= collect.isFinish();
		}
		return finish;
	}

}
