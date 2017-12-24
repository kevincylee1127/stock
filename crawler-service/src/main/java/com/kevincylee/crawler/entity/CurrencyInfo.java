package com.kevincylee.crawler.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class CurrencyInfo {

	@Id
	public String id;
	public String currencyType; // 幣別代號
	public String currencyName; // 幣別名稱
	@DateTimeFormat(iso = ISO.DATE)
	public Date transactionDate; // 交易日期(yyyy-MM-dd)
	public BigDecimal priceOfCashBuying; // 現金買入
	public BigDecimal priceOfCashSelling; // 現金賣出
	public BigDecimal priceOfSpotBuying; // 即期買入
	public BigDecimal priceOfSpotSelling; // 即期賣出
	@CreatedDate
	public Date createDateTime; // 建立時間
	@LastModifiedDate
	public Date updateDateTime; // 修改時間

	public CurrencyInfo() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
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

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

}
