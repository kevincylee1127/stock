package com.kevincylee.stock.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TwseStockInfoRequest {

	// request ex: ex_ch=tse_1101.tw&json=1&d=20171201&delay=5000&_=1512359242924
	@JsonProperty(value = "ex_ch")
	public String stockCode; // 股票代號 ex: tse_1101.tw
	@JsonProperty(value = "json")
	public String responseTyep; // 回覆內容格式
	@JsonProperty(value = "d")
	public String transactionDate;// 交易時間(YYYYMMDD)
	@JsonProperty(value = "delay")
	public String delaySeconds; // 1s = 1000
	@JsonProperty(value = "_")
	public String timeStamp;

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getResponseTyep() {
		return responseTyep;
	}

	public void setResponseTyep(String responseTyep) {
		this.responseTyep = responseTyep;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getDelaySeconds() {
		return delaySeconds;
	}

	public void setDelaySeconds(String delaySeconds) {
		this.delaySeconds = delaySeconds;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

}
