package com.kevincylee.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kevincylee.stock.service.CrawlerService;

@RestController
public class CrawlerController {

	@Autowired
	private CrawlerService crawlerService;

	@RequestMapping(value = "/crawler/StockCrawler")
	public String getStock() throws Exception {
		return crawlerService.getStock();
	}
	
	@RequestMapping(value = "/crawler/StockInfoCrawler")
	public String getStockInfo() throws Exception {
		return "GOOD";
	}
	
}
