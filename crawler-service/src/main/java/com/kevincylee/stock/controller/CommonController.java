package com.kevincylee.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kevincylee.stock.bean.ConfingPropertyRequest;
import com.kevincylee.stock.bean.StockInfoRequest;
import com.kevincylee.stock.bean.StockRequest;
import com.kevincylee.stock.service.StockService;

@RestController
public class CommonController {

	@Autowired
	private StockService stockService;

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
