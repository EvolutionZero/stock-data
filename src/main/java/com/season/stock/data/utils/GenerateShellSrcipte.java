package com.season.stock.data.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.season.stock.data.Main;
import com.season.stock.data.app.CandleAnalysist;
import com.season.stock.data.app.CollectCompany;
import com.season.stock.data.app.CollectExchangeStatus;
import com.season.stock.data.app.CollectIndustryAndCategory;
import com.season.stock.data.app.CompleteWestenIndex;
import com.season.stock.data.app.DaliyTask;
import com.season.stock.data.app.FindExtreme;
import com.season.stock.data.app.ReNameStock;
import com.season.stock.data.app.StrengthStat;

public class GenerateShellSrcipte {

	public static void main(String[] args) throws IOException {
		String libPath = System.getProperty("user.dir") + "/target/stock-data/lib/";
		String targetPath = System.getProperty("user.dir") + "/target/stock-data/";
		String firstJar = "stock-data-0.0.1-SNAPSHOT.jar";
		
		String shell = genShell(Main.class, libPath, firstJar);
		String bat = genBat(Main.class, libPath, firstJar);
		
		FileUtils.writeStringToFile(new File(targetPath + "start.sh"), shell);
		FileUtils.writeStringToFile(new File(targetPath + "start-daily.sh"), genShell(DaliyTask.class, libPath, firstJar));
		FileUtils.writeStringToFile(new File(targetPath + "start-analyse.sh"), genShell(CandleAnalysist.class, libPath, firstJar));
		FileUtils.writeStringToFile(new File(targetPath + "start-category.sh"), genShell(CollectIndustryAndCategory.class, libPath, firstJar));
		FileUtils.writeStringToFile(new File(targetPath + "start-rename.sh"), genShell(ReNameStock.class, libPath, firstJar));
		FileUtils.writeStringToFile(new File(targetPath + "start-company.sh"), genShell(CollectCompany.class, libPath, firstJar));
		FileUtils.writeStringToFile(new File(targetPath + "start-strength.sh"), genShell(StrengthStat.class, libPath, firstJar));
		FileUtils.writeStringToFile(new File(targetPath + "start-extreme.sh"), genShell(FindExtreme.class, libPath, firstJar));
		FileUtils.writeStringToFile(new File(targetPath + "start-exchange.sh"), genShell(CollectExchangeStatus.class, libPath, firstJar));
		FileUtils.writeStringToFile(new File(targetPath + "start-westenIndex.sh"), genShell(CompleteWestenIndex.class, libPath, firstJar));
		
		FileUtils.writeStringToFile(new File(targetPath + "start.bat"), bat);
		FileUtils.writeStringToFile(new File(targetPath + "_threadname"), "");
		FileUtils.writeStringToFile(	new File(targetPath + "stop.sh"), genStop(firstJar));
	}
	
	@SuppressWarnings("rawtypes")
	private static String genShell(Class mainClass, String libPath, String firstJar) throws IOException{
		String shellTemplate = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"/template/start.sh"));
		
		File libDir = new File(libPath);
		File[] libs = libDir.listFiles();
		StringBuffer sb = new StringBuffer();
		for (File file : libs) {
			sb.append("$lib0/").append(file.getName()).append(":");
		}
		String lib = "$lib0/" + firstJar + ":" + sb.toString().replace("$lib0/" + firstJar + ":", "");
		shellTemplate = shellTemplate.replace("${lib}", lib);
		shellTemplate = shellTemplate.replace("${mainClass}", mainClass.getName());
		return shellTemplate;
	}
	
	@SuppressWarnings("rawtypes")
	private static String genBat(Class mainClass, String libPath, String firstJar) throws IOException{
		String batTemplate = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"/template/start.bat"));
		StringBuffer batSb = new StringBuffer();
		File libDir = new File(libPath);
		File[] libs = libDir.listFiles();
		for (File file : libs) {
			batSb.append("%lib0%/").append(file.getName()).append(";");
		}
		String lib = "$lib0/" + firstJar + ";" + batSb.toString().replace("$lib0/" + firstJar + ";", "");
		batTemplate = batTemplate.replace("${lib}", lib);
		batTemplate = batTemplate.replace("${mainClass}", mainClass.getName());
		return batTemplate;
	}
	
	private static String genStop(String mainJar) throws IOException{
		String template = FileUtils.readFileToString(new File(System
				.getProperty("user.dir") + "/template/stop.sh"));
		return template.replace("${mainJar}", mainJar);
	}
}
