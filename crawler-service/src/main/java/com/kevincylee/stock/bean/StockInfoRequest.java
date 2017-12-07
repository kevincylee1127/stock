package com.kevincylee.stock.bean;

import java.math.BigDecimal;
import java.util.List;

import com.kevincylee.stock.entity.StockInfoPiece;

public class StockInfoRequest {

	public Integer stockNumber; // 股票代號
	public String transactionDate; // 交易日期(yyyy-MM-dd)
	public String transactionTime; // 揭示時間(HH:mm:ss)
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

	public Integer getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(Integer stockNumber) {
		this.stockNumber = stockNumber;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
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

}
