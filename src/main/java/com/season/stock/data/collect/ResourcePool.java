package com.season.stock.data.collect;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.season.stock.data.bean.IFengData;
import com.season.stock.data.bean.Stock;

public class ResourcePool {

	public static final LinkedBlockingQueue<IFengData> DATAS = new LinkedBlockingQueue<IFengData>();
	public static final LinkedBlockingQueue<List<Stock>> STOCKS = new LinkedBlockingQueue<List<Stock>>();
	
}
