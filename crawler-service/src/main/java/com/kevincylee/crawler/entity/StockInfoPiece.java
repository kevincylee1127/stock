package com.kevincylee.crawler.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class StockInfoPiece {

	@Id
	public String id;
	public Integer stockNumber; // 股票代號
	@DateTimeFormat(iso = ISO.DATE)
	public Date transactionDate; // 交易日期(yyyy-MM-dd)
	@DateTimeFormat(iso = ISO.DATE_TIME)
	public Date transactionDateTime; // 交易日期(yyyy-MM-dd HH:mm:ss.SSSZ)
	public String transactionType; // 交易類型 (BUY, SELL)
	public BigDecimal price; // 五檔價量 - 價格
	public Integer quantity; // 五檔價量 - 數量
	@CreatedDate
	public Date createDateTime; // 建立時間

	public StockInfoPiece() {
		super();
	}

	public StockInfoPiece(Integer stockNumber, Date transactionDate, Date transactionDateTime, String transactionType,
			BigDecimal price, Integer quantity) {
		super();
		this.stockNumber = stockNumber;
		this.transactionDate = transactionDate;
		this.transactionDateTime = transactionDateTime;
		this.transactionType = transactionType;
		this.price = price;
		this.quantity = quantity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(Integer stockNumber) {
		this.stockNumber = stockNumber;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(Date transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

}
