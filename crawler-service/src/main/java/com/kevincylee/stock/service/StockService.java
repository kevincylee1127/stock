package com.kevincylee.stock.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kevincylee.stock.bean.StockInfoRequest;
import com.kevincylee.stock.bean.StockRequest;
import com.kevincylee.stock.entity.Stock;
import com.kevincylee.stock.entity.StockIndustryType;
import com.kevincylee.stock.entity.StockInfo;
import com.kevincylee.stock.repository.IndustryTypeRepository;
import com.kevincylee.stock.repository.StockInfoRepository;
import com.kevincylee.stock.repository.StockRepository;

@Service
public class StockService {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	IndustryTypeRepository industryTypeRepository;

	@Autowired
	StockInfoRepository stockInfoRepository;

	public @ResponseBody String addStock(StockRequest requestBody) throws ParseException {
		// 取得股票資訊 檢查重複
		Integer stockNumber = Integer.parseInt(requestBody.getStockNumber());
		Stock stock = stockRepository.findByStockNumber(stockNumber);
		if (stock != null) {
			return "SUCCESS";
		}

		// 取得產業別編號
		StockIndustryType industryType = industryTypeRepository.findByIndustryName(requestBody.getIndustryType());
		if (industryType == null) {
			industryType = industryTypeRepository.save(new StockIndustryType(requestBody.getIndustryType()));
		}

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date onMarketDate = dateFormat.parse(requestBody.getOnMarketDate());
		stock = new Stock();
		stock.setMarketType(requestBody.getMarketType());
		stock.setStockNumber(stockNumber);
		stock.setStockName(requestBody.getStockName());
		stock.setIsinCode(requestBody.getIsinCode());
		stock.setOnMarketDate(onMarketDate);
		stock.setIndustryType(industryType);
		stockRepository.save(stock);

		return "SUCCESS";
	}

	public @ResponseBody String addStockInfo(StockInfoRequest requestBody) throws ParseException {
		StockInfo stockInfo = new StockInfo();
		stockInfo.setStockNumber(requestBody.getStockNumber());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		stockInfo.setTransactionDateTime(dateFormat.parse(requestBody.getTransactionDateTime()));
		stockInfo.setPriceOfOpen(requestBody.getPriceOfOpen());
		stockInfo.setPriceOfYesterday(requestBody.getPriceOfYesterday());
		stockInfo.setPriceOfLowest(requestBody.getPriceOfLowest());
		stockInfo.setPriceOfHighest(requestBody.getPriceOfHighest());
		stockInfo.setPriceOfLimitDown(requestBody.getPriceOfLimitDown());
		stockInfo.setPriceOfLimitUp(requestBody.getPriceOfLimitUp());
		stockInfo.setPrice(requestBody.getPrice());
		stockInfo.setTurnover(requestBody.getTurnover());
		stockInfo.setTotalTurnover(requestBody.getTotalTurnover());
		stockInfo.setStockInfoPieces(requestBody.getStockInfoPieces());
		
		stockInfoRepository.save(stockInfo);
		
		return "SUCCESS";
	}

}
