package com.season.stock.data.app;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.season.stock.data.bean.Code;
import com.season.stock.data.dao.CodeDao;
import com.season.stock.data.envm.Exchange;
import com.season.stock.data.envm.Plate;

public class AllStockCollect {

	public static void main(String[] args) throws IOException {
		List<Code> codes = new LinkedList<Code>();
		
		String shA = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "/script/上证A股.txt"));
		codes = getCode(shA, Exchange.SH, Plate.SHA);
		for (Code code : codes) {
			CodeDao.insert(code);
		}
		
		String shB = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "/script/上证B股.txt"));
		codes = getCode(shB, Exchange.SH, Plate.SHB);
		for (Code code : codes) {
			CodeDao.insert(code);
		}
		
		String szA = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "/script/深证A股.txt"));
		codes = getCode(szA, Exchange.SZ, Plate.SZA);
		for (Code code : codes) {
			CodeDao.insert(code);
		}
		
		String szB = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "/script/深证B股.txt"));
		codes = getCode(szB, Exchange.SZ, Plate.SZB);
		for (Code code : codes) {
			CodeDao.insert(code);
		}
		
		System.out.println("完成");
	}
	
	
	private static List<Code> getCode(String text, String exchange, String plate){
		String[] lines = text.split("\r\n");
		List<Code> attributes = new LinkedList<Code>();
		for (int i = 0; i < lines.length; i++) {
			Code attribute = new Code();
			String[] parts = lines[i].split("</a></span>");
			String codePart = parts[0];
			
			String namePart = parts.length > 1 ? parts[1] : "";
			
			int codeIdx = codePart.lastIndexOf(">");
			String code = codePart.substring(codeIdx + 1, codePart.length());
			code = exchange + code;
			
			namePart = namePart.replaceAll("</a></li>", "");
			int nameIdx = namePart.lastIndexOf(">");
			String name = namePart.substring(nameIdx + 1, namePart.length());
			
			attribute.setCode(code);
			attribute.setIsStock(Code.YES);
			attribute.setName(name);
			attribute.setPlate(plate);
			
			attributes.add(attribute);
		}
		return attributes;
	}
}
