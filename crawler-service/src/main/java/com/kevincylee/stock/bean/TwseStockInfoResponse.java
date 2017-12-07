package com.kevincylee.stock.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TwseStockInfoResponse {

	public StockInfoArray[] stockInfoArray;

	@JsonProperty(value = "msgArray")
	public StockInfoArray[] getStockInfoArray() {
		return stockInfoArray;
	}

	public void setStockInfoArray(StockInfoArray[] stockInfoArray) {
		this.stockInfoArray = stockInfoArray;
	}

	public class StockInfoArray {
		public String stockNumber; // 股票代號
		public String transactionDate; // 交易日期(YYYYMMDD)
		public String transactionTime;// 交易時間(HH:MI:SS)
		public String priceOfOpen; // 開盤
		public String priceOfYesterday; // 昨收
		public String priceOfLowest; // 最低
		public String priceOfHighest; // 最高
		public String priceOfLimitDown; // 跌停價
		public String priceOfLimitUp; // 漲停價
		public String price; // 當盤成交價
		public String turnover; // 當盤成交量
		public String totalTurnover; // 累積成交量
		public String fivePiecesOfBuyPrice; // 五檔價量 - 買進價格(從高到低，以_分隔資料)
		public String fivePiecesOfBuyQuantity; // 五檔價量 - 買進數量(配合b，以_分隔資料)
		public String fivePiecesOfSellPrice; // 五檔價量 - 賣出價格(從高到低，以_分隔資料)
		public String fivePiecesOfSellQuantity; // 五檔價量 - 賣出數量(配合a，以_分隔資料)

		@JsonProperty(value = "c")
		public String getStockNumber() {
			return stockNumber;
		}

		@JsonProperty(value = "d")
		public String getTransactionDate() {
			return transactionDate;
		}

		@JsonProperty(value = "t")
		public String getTransactionTime() {
			return transactionTime;
		}

		@JsonProperty(value = "o")
		public String getPriceOfOpen() {
			return priceOfOpen;
		}

		@JsonProperty(value = "y")
		public String getPriceOfYesterday() {
			return priceOfYesterday;
		}

		@JsonProperty(value = "l")
		public String getPriceOfLowest() {
			return priceOfLowest;
		}

		@JsonProperty(value = "h")
		public String getPriceOfHighest() {
			return priceOfHighest;
		}

		@JsonProperty(value = "w")
		public String getPriceOfLimitDown() {
			return priceOfLimitDown;
		}

		@JsonProperty(value = "u")
		public String getPriceOfLimitUp() {
			return priceOfLimitUp;
		}

		@JsonProperty(value = "z")
		public String getPrice() {
			return price;
		}

		@JsonProperty(value = "tv")
		public String getTurnover() {
			return turnover;
		}

		@JsonProperty(value = "v")
		public String getTotalTurnover() {
			return totalTurnover;
		}

		@JsonProperty(value = "b")
		public String getFivePiecesOfBuyPrice() {
			return fivePiecesOfBuyPrice;
		}

		@JsonProperty(value = "g")
		public String getFivePiecesOfBuyQuantity() {
			return fivePiecesOfBuyQuantity;
		}

		@JsonProperty(value = "a")
		public String getFivePiecesOfSellPrice() {
			return fivePiecesOfSellPrice;
		}

		@JsonProperty(value = "f")
		public String getFivePiecesOfSellQuantity() {
			return fivePiecesOfSellQuantity;
		}

		public void setStockNumber(String stockNumber) {
			this.stockNumber = stockNumber;
		}

		public void setTransactionDate(String transactionDate) {
			this.transactionDate = transactionDate;
		}

		public void setTransactionTime(String transactionTime) {
			this.transactionTime = transactionTime;
		}

		public void setPriceOfOpen(String priceOfOpen) {
			this.priceOfOpen = priceOfOpen;
		}

		public void setPriceOfYesterday(String priceOfYesterday) {
			this.priceOfYesterday = priceOfYesterday;
		}

		public void setPriceOfLowest(String priceOfLowest) {
			this.priceOfLowest = priceOfLowest;
		}

		public void setPriceOfHighest(String priceOfHighest) {
			this.priceOfHighest = priceOfHighest;
		}

		public void setPriceOfLimitDown(String priceOfLimitDown) {
			this.priceOfLimitDown = priceOfLimitDown;
		}

		public void setPriceOfLimitUp(String priceOfLimitUp) {
			this.priceOfLimitUp = priceOfLimitUp;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public void setTurnover(String turnover) {
			this.turnover = turnover;
		}

		public void setTotalTurnover(String totalTurnover) {
			this.totalTurnover = totalTurnover;
		}

		public void setFivePiecesOfBuyPrice(String fivePiecesOfBuyPrice) {
			this.fivePiecesOfBuyPrice = fivePiecesOfBuyPrice;
		}

		public void setFivePiecesOfBuyQuantity(String fivePiecesOfBuyQuantity) {
			this.fivePiecesOfBuyQuantity = fivePiecesOfBuyQuantity;
		}

		public void setFivePiecesOfSellPrice(String fivePiecesOfSellPrice) {
			this.fivePiecesOfSellPrice = fivePiecesOfSellPrice;
		}

		public void setFivePiecesOfSellQuantity(String fivePiecesOfSellQuantity) {
			this.fivePiecesOfSellQuantity = fivePiecesOfSellQuantity;
		}
	}

}
