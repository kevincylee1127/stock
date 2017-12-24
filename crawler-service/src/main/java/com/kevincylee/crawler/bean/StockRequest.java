package com.kevincylee.crawler.bean;

public class StockRequest {

	public String marketType; // 市場別 1:上市 2:上櫃
	public String stockNumber; // 股票代號
	public String stockName; // 股票名稱
	public String isinCode; // 國際證券辨識號碼(ISIN Code)
	public String onMarketDate; // 上市日
	public String industryType; // 產業別

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public String getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(String stockNumber) {
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

	public String getOnMarketDate() {
		return onMarketDate;
	}

	public void setOnMarketDate(String onMarketDate) {
		this.onMarketDate = onMarketDate;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

}
