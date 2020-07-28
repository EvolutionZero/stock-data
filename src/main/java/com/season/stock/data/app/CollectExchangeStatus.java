package com.season.stock.data.app;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.envm.Status;
import com.season.stock.data.utils.LogUtils;

public class CollectExchangeStatus {

	private static final Logger LOG = LoggerFactory
			.getLogger(CollectExchangeStatus.class);

	public static void main(String[] args) {
		LogUtils.loadLogBackConfig();
		LOG.info("开始更新交易状态！");
		List<String> codes = CodeDao.queryAllCode();
		for (String code : codes) {
			execute(code); 
		}
		LOG.info("完成更新交易状态！");
	}

	@SuppressWarnings("resource")
	private static void execute(String code) {
		WebClient wc = new WebClient(BrowserVersion.CHROME);
		wc.getOptions().setUseInsecureSSL(false);
		wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
		wc.getOptions().setCssEnabled(false); // 禁用css支持
		wc.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
		wc.getOptions().setTimeout(100000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
		wc.getOptions().setDoNotTrackEnabled(false);
		try {
			HtmlPage page = wc.getPage("http://finance.ifeng.com/app/hq/stock/" + code);
			String html = page.asXml();
			Document doc = Jsoup.parse(html);
			Elements elements = doc.select(".Hfont .color_red span");
			String status = elements.isEmpty() ? "" : elements.get(0).ownText().trim();
			int exchgStatus = CodeDao.queryExchangeStatus(code);
			if("退市".equals(status)){
				if(exchgStatus != Status.STOP){
					CodeDao.updateStatus(code, Status.STOP);
					LOG.info("[{}]更新状态为[停牌]", code);
					
				}
			} else if("".equals(status)){
				if(exchgStatus != Status.EXCHANGE){
					CodeDao.updateStatus(code, Status.EXCHANGE);
					LOG.info("[{}]更新状态为[交易]", code);
					
				}
			} else {
				LOG.info("[{}]的未知状态为[{}]", code , status);
			}
		} catch (Exception e) {
			LOG.error(code, e);
		}
	}

}
