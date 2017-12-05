package com.kevincylee.stock.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class StockInfoPiece {

	@Id
	public Integer id;
	public Integer stockNumber; // 股票代號
	@DateTimeFormat(iso = ISO.DATE)
	public Date transactionDate; // 交易日期(yyyy-MM-dd)
	public BigDecimal buyPrice; // 五檔價量 - 買進價格
	public Integer buyQuantity; // 五檔價量 - 買進數量
	public BigDecimal sellPrice; // 五檔價量 - 賣出價格
	public Integer sellQuantity; // 五檔價量 - 賣出數量
	@CreatedDate
	public Date createDateTime; // 建立時間

	public StockInfoPiece() {
		super();
	}

	public StockInfoPiece(Integer stockNumber, Date transactionDate, BigDecimal buyPrice, Integer buyQuantity,
			BigDecimal sellPrice, Integer sellQuantity) {
		super();
		this.stockNumber = stockNumber;
		this.transactionDate = transactionDate;
		this.buyPrice = buyPrice;
		this.buyQuantity = buyQuantity;
		this.sellPrice = sellPrice;
		this.sellQuantity = sellQuantity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Integer getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Integer buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Integer getSellQuantity() {
		return sellQuantity;
	}

	public void setSellQuantity(Integer sellQuantity) {
		this.sellQuantity = sellQuantity;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

}
