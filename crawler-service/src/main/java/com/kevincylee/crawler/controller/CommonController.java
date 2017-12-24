package com.kevincylee.crawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kevincylee.crawler.bean.ConfingPropertyRequest;
import com.kevincylee.crawler.bean.StockInfoRequest;
import com.kevincylee.crawler.bean.StockRequest;
import com.kevincylee.crawler.service.CommonService;

@RestController
public class CommonController {

	@Autowired
	private CommonService stockService;

	@RequestMapping(value = "/crawler/addStock")
	public @ResponseBody String addStock(@RequestBody StockRequest requestBody) throws Exception {
		return stockService.addStock(requestBody);
	}

	@RequestMapping(value = "/crawler/addStockInfo")
	public @ResponseBody String addStockInfo(@RequestBody StockInfoRequest requestBody) throws Exception {
		return stockService.addStockInfo(requestBody);
	}

	@RequestMapping(value = "/configProperty/addConfingProperty")
	public @ResponseBody String addConfingProperty(@RequestBody ConfingPropertyRequest requestBody)
			throws Exception {
		return stockService.addConfingProperty(requestBody);
	}
}
