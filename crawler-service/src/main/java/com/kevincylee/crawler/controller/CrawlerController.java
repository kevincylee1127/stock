package com.kevincylee.crawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kevincylee.crawler.service.CurrencyCrawlerService;
import com.kevincylee.crawler.service.StockCrawlerService;

@RestController
public class CrawlerController {

	@Autowired
	private StockCrawlerService stockCrawlerService;

	@Autowired
	private CurrencyCrawlerService currencyCrawlerService;

	@RequestMapping(value = "/crawler/getStock")
	public String getStock() throws Exception {
		return stockCrawlerService.getStock();
	}

	@RequestMapping(value = "/crawler/getStockHistoryInfo")
	public String getStockHistoryInfo(String startDate, String targetDate) throws Exception {
		return stockCrawlerService.getStockHistoryInfo(startDate, targetDate);
	}

	// dataDate =yyyyMMdd
	@RequestMapping(value = "/crawler/getStockInfo")
	public String getStockInfo(String targetDate) throws Exception {
		return stockCrawlerService.getStockInfo(targetDate);
	}

	// dataDate =yyyyMMdd
	@RequestMapping(value = "/crawler/getCurrencyInfo")
	public String getCurrencyInfo(String targetDate) throws Exception {
		return currencyCrawlerService.getCurrencyInfo(targetDate);
	}

	@RequestMapping(value = "/crawler/getCurrencyHistoryInfo")
	public String getCurrencyHistoryInfo(String startDate, String targetDate) throws Exception {
		return currencyCrawlerService.getCurrencyHistoryInfo(startDate, targetDate);
	}
}
