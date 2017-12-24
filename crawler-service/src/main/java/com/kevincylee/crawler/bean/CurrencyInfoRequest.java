package com.kevincylee.crawler.bean;

import java.math.BigDecimal;

public class CurrencyInfoRequest {

	public String currencyType; // 幣別代號
	public String currencyName; // 幣別名稱
	public String transactionDate; // 交易日期(yyyy-MM-dd)
	public BigDecimal priceOfCashBuying; // 現金買入
	public BigDecimal priceOfCashSelling; // 現金賣出
	public BigDecimal priceOfSpotBuying; // 即期買入
	public BigDecimal priceOfSpotSelling; // 即期賣出

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public BigDecimal getPriceOfCashBuying() {
		return priceOfCashBuying;
	}

	public void setPriceOfCashBuying(BigDecimal priceOfCashBuying) {
		this.priceOfCashBuying = priceOfCashBuying;
	}

	public BigDecimal getPriceOfCashSelling() {
		return priceOfCashSelling;
	}

	public void setPriceOfCashSelling(BigDecimal priceOfCashSelling) {
		this.priceOfCashSelling = priceOfCashSelling;
	}

	public BigDecimal getPriceOfSpotBuying() {
		return priceOfSpotBuying;
	}

	public void setPriceOfSpotBuying(BigDecimal priceOfSpotBuying) {
		this.priceOfSpotBuying = priceOfSpotBuying;
	}

	public BigDecimal getPriceOfSpotSelling() {
		return priceOfSpotSelling;
	}

	public void setPriceOfSpotSelling(BigDecimal priceOfSpotSelling) {
		this.priceOfSpotSelling = priceOfSpotSelling;
	}

}
