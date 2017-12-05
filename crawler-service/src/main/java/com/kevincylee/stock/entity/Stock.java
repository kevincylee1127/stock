package com.kevincylee.stock.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Stock {

	@Id
	public String id;
	public String marketType; // 市場別 1:上市 2:上櫃
	public Integer stockNumber; // 股票代號
	public String stockName; // 股票名稱
	public String isinCode; // 國際證券辨識號碼(ISIN Code)
	public Date onMarketDate; // 上市日
	public StockIndustryType industryType; // 產業別

	public Stock() {
		super();
	}

	public Stock(String id, String marketType, Integer stockNumber, String stockName, String isinCode,
			Date onMarketDate, StockIndustryType industryType) {
		super();
		this.id = id;
		this.marketType = marketType;
		this.stockNumber = stockNumber;
		this.stockName = stockName;
		this.isinCode = isinCode;
		this.onMarketDate = onMarketDate;
		this.industryType = industryType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public Integer getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(Integer stockNumber) {
		this.stockNumber = stockNumber;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getIsinCode() {
		return isinCode;
	}

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	public Date getOnMarketDate() {
		return onMarketDate;
	}

	public void setOnMarketDate(Date onMarketDate) {
		this.onMarketDate = onMarketDate;
	}

	public StockIndustryType getIndustryType() {
		return industryType;
	}

	public void setIndustryType(StockIndustryType industryType) {
		this.industryType = industryType;
	}

}
