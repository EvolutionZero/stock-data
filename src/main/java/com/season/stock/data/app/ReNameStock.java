package com.season.stock.data.app;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.utils.HttpUtils;
import com.season.stock.data.utils.LogUtils;

public class ReNameStock {

	private static final Logger LOG = LoggerFactory.getLogger(ReNameStock.class);
	
	public static void main(String[] args) {
		LogUtils.loadLogBackConfig();
		LOG.info("更新名字开始！");
		Map<String, String> kvs = CodeDao.queryAllCodeAndName();
		Set<String> codes = kvs.keySet();
		for (String code : codes) {
			String url = "http://finance.ifeng.com/app/hq/stock/"+ code + "/" ;
			String text = null;
			try {
				text = HttpUtils.getText(url);
				if(!text.contains("404-页面不存在")){
					int start = text.indexOf("<title>") + "<title>".length();
					int end = text.indexOf("</title>");
					String name = text.substring(start, end);
					int idx = name.indexOf("(" + code.replace("sh", "").replace("sz", "") + ")");
					name = name.substring(0, idx);
					
					String oName = kvs.get(code);
					if(!oName.equals(name)){
						CodeDao.updateName(code, name);
						LOG.info("更新{}的名字, {} -> {}", code, oName, name);
					}
					
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
		LOG.info("更新名字结束！");
	}
}
