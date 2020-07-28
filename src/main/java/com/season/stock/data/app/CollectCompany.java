package com.season.stock.data.app;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.utils.HttpUtils;
import com.season.stock.data.utils.LogUtils;

public class CollectCompany {

	private static final Logger LOG = LoggerFactory.getLogger(CollectCompany.class);
	
	public static void main(String[] args) {
		LogUtils.loadLogBackConfig();
		List<String> codes = CodeDao.queryAllCodeOnExchange();
		for (String code : codes) {
			LOG.info("{}采集公司信息！", code);
			collect(code);
		}
//		collect("600012");
	}

	private static void collect(String code) {
		String url = "http://stockpage.10jqka.com.cn/" + code.replace("sh", "").replace("sz", "") + "/";
		String html = null;
		try {
			html = HttpUtils.getText(url);
			Document doc = Jsoup.parse(html);
			Element companyDetails = doc.select(".company_details").get(0);
			Map<String, String> map = new HashMap<String, String>();
			Elements dts = companyDetails.select("dt");
			Elements dds = companyDetails.select("dd");
			List<String> keys = new LinkedList<String>();
			List<String> values = new LinkedList<String>();
			for (int i = 0; i < dts.size(); i++) {
				String key = dts.get(i).ownText();
				keys.add(key);
			}
			for (int i = 0; i < dds.size(); i++) {
				String value = dds.get(i).ownText();
				if(i == 3){
					value = dds.get(i).attr("title").trim();
				}
				values.add(value);
			}
			values.remove(2);
			for (int i = 0; i < keys.size(); i++) {
				map.put(keys.get(i).replace("：", ""), values.get(i));
			}
			String listDate = map.get("上市日期");
			String location = map.get("所属地域");
			String business = map.get("主营业务");
			CodeDao.updateCompany(code, listDate, location, business);
		} catch (Exception e) {
			e.printStackTrace();
			return ;
		}
	}
}
