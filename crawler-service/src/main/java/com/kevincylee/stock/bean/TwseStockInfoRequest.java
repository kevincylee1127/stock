package com.kevincylee.stock.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TwseStockInfoRequest {

	// request ex: ex_ch=tse_1101.tw&json=1&d=20171201&delay=5000&_=1512359242924
	@JsonProperty(value = "ex_ch")
	public String stockCode; // 股票代號 ex: tse_1101.tw
	@JsonProperty(value = "json")
	public Integer responseType = 1; // 回覆內容格式
	@JsonProperty(value = "d")
	public String transactionDate;// 交易時間(YYYYMMDD)
	@JsonProperty(value = "delay")
	public Integer delaySeconds = 3000; // 1s = 1000
	@JsonProperty(value = "_")
	public Long timeStamp = new Date().getTime();

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public Integer getResponseType() {
		return responseType;
	}

	public void setResponseType(Integer responseType) {
		this.responseType = responseType;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Integer getDelaySeconds() {
		return delaySeconds;
	}

	public void setDelaySeconds(Integer delaySeconds) {
		this.delaySeconds = delaySeconds;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String toParamString() {
		return "?ex_ch=" + stockCode + "&json=" + responseType + "&d=" + transactionDate + "&delay=" + delaySeconds
				+ "&_=" + timeStamp;
	}

}
