package com.kevincylee.crawler.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class StockInfo {

	@Id
	public String id;
	public Integer stockNumber; // 股票代號
	@DateTimeFormat(iso = ISO.DATE)
	public Date transactionDate; // 交易日期(yyyy-MM-dd)
	@DateTimeFormat(iso = ISO.DATE_TIME)
	public Date transactionDateTime; // 交易日期(yyyy-MM-dd HH:mm:ss.SSSZ)
	public BigDecimal priceOfOpen; // 開盤
	public BigDecimal priceOfYesterday; // 昨收
	public BigDecimal priceOfLowest; // 最低
	public BigDecimal priceOfHighest; // 最高
	public BigDecimal priceOfLimitDown; // 跌停價
	public BigDecimal priceOfLimitUp; // 漲停價
	public BigDecimal price; // 當盤成交價
	public Integer turnover; // 當盤成交量
	public Integer totalTurnover; // 累積成交量
	public List<StockInfoPiece> stockInfoPieces; // 五檔價格與數量
	@CreatedDate
	public Date createDateTime; // 建立時間
	@LastModifiedDate
	public Date updateDateTime; // 修改時間

	public StockInfo() {
		super();
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

	public BigDecimal getPriceOfOpen() {
		return priceOfOpen;
	}

	public void setPriceOfOpen(BigDecimal priceOfOpen) {
		this.priceOfOpen = priceOfOpen;
	}

	public BigDecimal getPriceOfYesterday() {
		return priceOfYesterday;
	}

	public void setPriceOfYesterday(BigDecimal priceOfYesterday) {
		this.priceOfYesterday = priceOfYesterday;
	}

	public BigDecimal getPriceOfLowest() {
		return priceOfLowest;
	}

	public void setPriceOfLowest(BigDecimal priceOfLowest) {
		this.priceOfLowest = priceOfLowest;
	}

	public BigDecimal getPriceOfHighest() {
		return priceOfHighest;
	}

	public void setPriceOfHighest(BigDecimal priceOfHighest) {
		this.priceOfHighest = priceOfHighest;
	}

	public BigDecimal getPriceOfLimitDown() {
		return priceOfLimitDown;
	}

	public void setPriceOfLimitDown(BigDecimal priceOfLimitDown) {
		this.priceOfLimitDown = priceOfLimitDown;
	}

	public BigDecimal getPriceOfLimitUp() {
		return priceOfLimitUp;
	}

	public void setPriceOfLimitUp(BigDecimal priceOfLimitUp) {
		this.priceOfLimitUp = priceOfLimitUp;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getTurnover() {
		return turnover;
	}

	public void setTurnover(Integer turnover) {
		this.turnover = turnover;
	}

	public Integer getTotalTurnover() {
		return totalTurnover;
	}

	public void setTotalTurnover(Integer totalTurnover) {
		this.totalTurnover = totalTurnover;
	}

	public List<StockInfoPiece> getStockInfoPieces() {
		return stockInfoPieces;
	}

	public void setStockInfoPieces(List<StockInfoPiece> stockInfoPieces) {
		this.stockInfoPieces = stockInfoPieces;
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
