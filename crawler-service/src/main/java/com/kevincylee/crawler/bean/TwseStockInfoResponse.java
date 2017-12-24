package com.kevincylee.crawler.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TwseStockInfoResponse {

	@JsonProperty(value = "msgArray")
	private StockInfoArray[] stockInfoArray;

	public StockInfoArray[] getStockInfoArray() {
		return stockInfoArray;
	}

	public void setStockInfoArray(StockInfoArray[] stockInfoArray) {
		this.stockInfoArray = stockInfoArray;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class StockInfoArray {
		@JsonProperty(value = "c")
		private String stockNumber; // 股票代號

		@JsonProperty(value = "d")
		private String transactionDate; // 交易日期(YYYYMMDD)

		@JsonProperty(value = "t")
		private String transactionTime;// 交易時間(HH:MI:SS)

		@JsonProperty(value = "o")
		private String priceOfOpen; // 開盤

		@JsonProperty(value = "y")
		private String priceOfYesterday; // 昨收

		@JsonProperty(value = "l")
		private String priceOfLowest; // 最低

		@JsonProperty(value = "h")
		private String priceOfHighest; // 最高

		@JsonProperty(value = "w")
		private String priceOfLimitDown; // 跌停價

		@JsonProperty(value = "u")
		private String priceOfLimitUp; // 漲停價

		@JsonProperty(value = "z")
		private String price; // 當盤成交價

		@JsonProperty(value = "tv")
		private String turnover; // 當盤成交量

		@JsonProperty(value = "v")
		private String totalTurnover; // 累積成交量

		@JsonProperty(value = "b")
		private String fivePiecesOfBuyPrice; // 五檔價量 - 買進價格(從高到低，以_分隔資料)

		@JsonProperty(value = "g")
		private String fivePiecesOfBuyQuantity; // 五檔價量 - 買進數量(配合b，以_分隔資料)

		@JsonProperty(value = "a")
		private String fivePiecesOfSellPrice; // 五檔價量 - 賣出價格(從高到低，以_分隔資料)

		@JsonProperty(value = "f")
		private String fivePiecesOfSellQuantity; // 五檔價量 - 賣出數量(配合a，以_分隔資料)

		public String getStockNumber() {
			return stockNumber;
		}

		public String getTransactionDate() {
			return transactionDate;
		}

		public String getTransactionTime() {
			return transactionTime;
		}

		public String getPriceOfOpen() {
			return priceOfOpen;
		}

		public String getPriceOfYesterday() {
			return priceOfYesterday;
		}

		public String getPriceOfLowest() {
			return priceOfLowest;
		}

		public String getPriceOfHighest() {
			return priceOfHighest;
		}

		public String getPriceOfLimitDown() {
			return priceOfLimitDown;
		}

		public String getPriceOfLimitUp() {
			return priceOfLimitUp;
		}

		public String getPrice() {
			return price;
		}

		public String getTurnover() {
			return turnover;
		}

		public String getTotalTurnover() {
			return totalTurnover;
		}

		public String getFivePiecesOfBuyPrice() {
			return fivePiecesOfBuyPrice;
		}

		public String getFivePiecesOfBuyQuantity() {
			return fivePiecesOfBuyQuantity;
		}

		public String getFivePiecesOfSellPrice() {
			return fivePiecesOfSellPrice;
		}

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
