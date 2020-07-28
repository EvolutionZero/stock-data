package com.season.stock.data.utils;

import java.io.File;

/**
 * log配置文件路径设置工具类
 * 
 * @project
 * @copyright 广东凯通软件开发技术有限公司 综合网管接口组 (c) 2013
 * @version 1.0 2013-11-15
 * @author 黄坚：huangjian@gdcattsoft.com
 * @since jdk版本：jdk1.6
 */
public class LogUtils {

	/** logback.xml路径 */
	public final static String LOGBACK_PATH = System.getProperty("user.dir") + "/conf/logback.xml";

	/** 加载LogBack自定义日志配置文件 */
	public static void loadLogBackConfig() {
		loadLogBackConfig(LOGBACK_PATH);
	}

	/**
	 * 加载LogBack自定义日志配置文件
	 * 
	 * @param path
	 *            文件路径
	 */
	public static void loadLogBackConfig(String path) {
		ch.qos.logback.classic.LoggerContext lc = 
				(ch.qos.logback.classic.LoggerContext) org.slf4j.LoggerFactory
				.getILoggerFactory();
		ch.qos.logback.classic.joran.JoranConfigurator configurator = 
				new ch.qos.logback.classic.joran.JoranConfigurator();
		configurator.setContext(lc);
		lc.reset();
		try {
			configurator.doConfigure(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}