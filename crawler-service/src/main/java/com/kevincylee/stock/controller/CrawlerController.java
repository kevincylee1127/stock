package com.kevincylee.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kevincylee.stock.service.CrawlerService;

@RestController
public class CrawlerController {

	@Autowired
	private CrawlerService crawlerService;

	@RequestMapping(value = "/crawler/getStock")
	public String getStock() throws Exception {
		return crawlerService.getStock();
	}

	@RequestMapping(value = "/crawler/getStockHistoryInfo")
	public String getStockHistoryInfo(String startDate, String targetDate) throws Exception {
		return crawlerService.getStockHistoryInfo(startDate, targetDate);
	}

	// dataDate =yyyyMMdd
	@RequestMapping(value = "/crawler/getStockInfo")
	public String getStockInfo(String targetDate) throws Exception {
		return crawlerService.getStockInfo(targetDate);
	}

}
