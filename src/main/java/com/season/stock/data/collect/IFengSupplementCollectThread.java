package com.season.stock.data.collect;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.season.stock.data.bean.IFengData;
import com.season.stock.data.utils.HttpUtils;

public class IFengSupplementCollectThread extends Thread{

	private static final Logger LOG = LoggerFactory.getLogger(IFengSupplementCollectThread.class);
	
	private List<String> codes;
	
	// 分母
	private int denominator;
	
	// 余数
	private int remainder;
	
	private boolean finish = false;
	
	public IFengSupplementCollectThread(List<String> codes, int denominator,
			int remainder) {
		super();
		this.codes = codes;
		this.denominator = denominator;
		this.remainder = remainder;
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		new IFengSupplementCollectThread(new LinkedList<String>(), 5, 1).run();;
		Thread.sleep(5 * 1000);
	}
	
	@Override
	public void run() {
		try {
			for (int i = 0; i < codes.size(); i++) {
				if(i % denominator == remainder){
					String code = codes.get(i);
					String url = "http://api.finance.ifeng.com/akdaily/?code="+ code + "&type=last" ;
					String text = null;
					try {
						text = HttpUtils.getText(url);
						IFengData iFengData = new IFengData();
						iFengData.setCode(code);
						iFengData.setData(text);
						LOG.info("[{}]线程采集[{}]", super.getName(), code);
						String path  = System.getProperty("user.dir") + "/file/" + code  + ".txt";
						FileUtils.writeStringToFile(new File(path), text);
					} catch (Exception e1) {
						e1.printStackTrace();
						continue;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finish = true;
	}
	
	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public int getRemainder() {
		return remainder;
	}

	public void setRemainder(int remainder) {
		this.remainder = remainder;
	}

	public boolean isFinish() {
		return finish;
	}

}
