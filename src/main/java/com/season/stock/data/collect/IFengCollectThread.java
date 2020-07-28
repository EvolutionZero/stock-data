package com.season.stock.data.collect;

import java.text.DecimalFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.season.stock.data.bean.IFengData;
import com.season.stock.data.utils.HttpUtils;

/**
 * 
 * <PRE>
 * 原始数据采集线程
 * </PRE>
 * <B>项    目：</B> 
 */
public class IFengCollectThread extends Thread{

	private static final Logger LOG = LoggerFactory.getLogger(IFengCollectThread.class);

	private int start;
	private int end;
	private int count;
	private String exchange;
	
	// 分母
	private int denominator;
	// 余数
	private int remainder;
	
	public IFengCollectThread() {
	}
	
	public IFengCollectThread(int start, int end, int count, int denominator,
			int remainder,String exchange) {
		super(exchange + remainder);
		this.start = start;
		this.end = end;
		this.count = count;
		this.denominator = denominator;
		this.remainder = remainder;
		this.exchange = exchange;
	}
	
	@Override
	public void run() {
		try {
			int number = 0;
			for (int code = start; code < end; code++) {
				if(code % denominator == remainder){
					String sCode = exchange + fillZero(code);
					String url = "http://api.finance.ifeng.com/akdaily/?code="+ sCode + "&type=last" ;
					String text = null;
					try {
						text = HttpUtils.getText(url);
						IFengData iFengData = new IFengData();
						iFengData.setCode(sCode);
						iFengData.setData(text);
						ResourcePool.DATAS.add(iFengData);
						System.out.println(new Date () + " - "+ super.getName() + "线程采集 " + sCode);
//					FileUtils.writeStringToFile(new File("./file/" + code  + ".txt"), text);
					} catch (Exception e1) {
						e1.printStackTrace();
						continue;
					}
					number ++;
					if(number > count){
						break;
					}
				}
			}
		} catch (Exception e) {
			LOG.error("", e);
		}
	}
	
	private String fillZero(int number){
		String code = number + "";
		int cnt = 1;
		while(number/10 != 0){
			cnt++;
			number = number / 10;
		}
		for (int i = 0; i < 6 - cnt; i++) {
			code = "0" + code;
		}
		return code;
	}
	
	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("######");
		System.out.println(df.format(1));
		
	}
	public int getStart() {
		return start;
	}
	public int getEnd() {
		return end;
	}
	public int getRemainder() {
		return remainder;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public void setRemainder(int remainder) {
		this.remainder = remainder;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public int getDenominator() {
		return denominator;
	}

	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}
	
	
}
