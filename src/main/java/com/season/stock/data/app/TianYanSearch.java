package com.season.stock.data.app;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;

public class TianYanSearch {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
//		String url = "http://www.tianyancha.com/";
		//创建一个webClient,模拟浏览器
		//WebClient webClient = new WebClient();
		//使用FireFox读取网页
		//使用Chrome读取网页
		//WebClient webClient = new WebClient(BrowserVersion.CHROME);

		//打开的话，就是执行javaScript/Css
		
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		String url = "http://www.tianyancha.com/v2/search/600000.json";
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(true);
		//获取页面
		HtmlPage page = webClient.getPage(url);
	    System.out.println(page.asText());  
//	    System.out.println("fuck");
		
	}
}
