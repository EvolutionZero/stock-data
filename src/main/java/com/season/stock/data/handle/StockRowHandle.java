package com.season.stock.data.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.season.stock.data.bean.Stock;

public class StockRowHandle implements ResultSetExtractor<List<Stock>> {

	@Override
	public List<Stock> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Stock> stocks = new LinkedList<Stock>();
		while (rs.next()) {
			String id = rs.getString("id");
			double open = rs.getDouble("d_open");
			double close = rs.getDouble("d_close");
			double high = rs.getDouble("high");
			double low = rs.getDouble("low");
			double precentChange = rs.getDouble("precentChange");
			String date = rs.getString("date");
			String code = rs.getString("i_code");
			double ma5 = rs.getDouble("ma5");
			double ma10 = rs.getDouble("ma10");
			double ma20 = rs.getDouble("ma20");
			double ma30 = rs.getDouble("ma30");
			
			
			double bollUpper = rs.getDouble("boll_upper");
			double bollLower = rs.getDouble("boll_lower");
			double bollPercentB = rs.getDouble("boll_percentB");

			double wr10 = rs.getDouble("wr10");
			double wr21 = rs.getDouble("wr21");
			double wr42 = rs.getDouble("wr42");
			
			Stock stock = new Stock();
			stock.setId(id);
			stock.setCode(code);
			stock.setOpen(open);
			stock.setClose(close);
			stock.setHigh(high);
			stock.setLow(low);
			stock.setDate(date);
			stock.setPrecentChange(precentChange);
			stock.setMa5(ma5);
			stock.setMa10(ma10);
			stock.setMa20(ma20);
			stock.setMa30(ma30);
			
			stock.setBollLower(bollLower);
			stock.setBollPercentB(bollPercentB);
			stock.setBollUpper(bollUpper);
			stock.setWr10(wr10);
			stock.setWr21(wr21);
			stock.setWr42(wr42);
			
			stocks.add(stock);
		}
		return stocks;
	}

}
