package com.season.stock.data.app;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.season.stock.data.bean.Category;
import com.season.stock.data.dao.CategoryDao;
import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.utils.HttpUtils;
import com.season.stock.data.utils.LogUtils;

public class CollectIndustryAndCategory {

	private static final Logger LOG = LoggerFactory.getLogger(CollectIndustryAndCategory.class);
	
	public static void main(String[] args) {
		LogUtils.loadLogBackConfig();
		execute();
	}
	
	public static void execute() {
		LOG.info("开始采集板块概念信息！");
		List<String> codes = CodeDao.queryAllCodeOnExchange();
		for (String code : codes) {
			if(code != null && !"".equals(code)){
				LOG.info("{}采集板块概念信息！", code);
				collect(code);
			}
		}
		LOG.info("结束采集板块概念信息！");
	}

	private static void collect(String code) {
		String url = "http://finance.ifeng.com/app/hq/stock/" + code + "/";
		String html = null;
		try {
			html = HttpUtils.getText(url);
			Document doc = Jsoup.parse(html);
			Elements elements = doc.select(".lastBot");
			Elements spans = elements.select("span");
			String industry = spans.get(0).ownText();
			industry = industry.replace("所属行业：", "").replace(" ", "").trim().replace("　　", "");
			CodeDao.updateIndustry(code, industry);
			
			CategoryDao.deleteByCode(code);
			List<Category> categories = new LinkedList<Category>();
			Elements as = spans.get(1).select("a");
			int size = as.size();
			for (int i = 0; i < size; i++) {
				org.jsoup.nodes.Element element = as.get(i);
				String ownText = element.ownText();
				
				Category category = new Category();
				category.setCode(code);
				category.setCategory(ownText);
				
				categories.add(category);
			}
			CategoryDao.save(categories);
		} catch (Exception e) {
			LOG.error("{}采集板块概念信息有误！", code);
			LOG.error("", e);
			return ;
		}
		
	}
}
