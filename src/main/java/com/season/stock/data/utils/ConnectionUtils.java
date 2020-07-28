//package com.season.stock.data.utils;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//import org.logicalcobwebs.proxool.ProxoolException;
//import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;
//import org.xml.sax.InputSource;
//
//public class ConnectionUtils {
//	
//	
//	private final static String SYS_PATH = System.getProperty("user.dir");
//	
//	private final static String PROXOOL_PATH = SYS_PATH + "/conf/proxool.xml";
//	
//	static {
//		try {  
//            JAXPConfigurator.configure(new InputSource(PROXOOL_PATH),  
//                    false);  
//        } catch (ProxoolException e) {  
//            e.printStackTrace();  
//        }  
//	}
//	
//	 public static Connection getConnection()  {  
//	        Connection conn = null;  
//	        try {  
//	            Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");  
//	            conn = DriverManager.getConnection("proxool.code-generator"); 
//	        } catch (ClassNotFoundException e) {  
//	            e.printStackTrace();  
//	        } catch (SQLException e) {  
//	            e.printStackTrace();  
//	        }  
//	        return conn;  
//	    }  
//}
