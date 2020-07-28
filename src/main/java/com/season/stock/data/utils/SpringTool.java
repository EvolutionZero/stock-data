package com.season.stock.data.utils;

import java.io.File;

import org.quartz.impl.StdScheduler;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class SpringTool {
	
	private static final String PATH = "/" + System.getProperty("user.dir") + "/conf/spring-servlet.xml";
	
	private static JdbcTemplate jdbcTemplate = null;
	
	private static StdScheduler scheduler = null;
	
	public static JdbcTemplate getJdbcTemplate(){
		if(jdbcTemplate == null){
			FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(PATH);
			jdbcTemplate = (JdbcTemplate)context.getBean("jdbcTemplate");
		}
		return jdbcTemplate;
	}
	
	public static StdScheduler getSchedulerFactoryBean() {
		if(scheduler == null){
			FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(PATH);
			scheduler = (org.quartz.impl.StdScheduler)context.getBean("startQuertz");
		}
		return scheduler;
	}
	
	public static void main(String[] args) {
		File file=new File("");
		String absolutePath = file.getAbsolutePath();
		System.out.println(absolutePath);
	}
}
