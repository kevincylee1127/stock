package com.kevincylee.stock.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kevincylee.stock.bean.ConfingPropertyRequest;
import com.kevincylee.stock.bean.StockInfoRequest;
import com.kevincylee.stock.bean.StockRequest;
import com.kevincylee.stock.entity.ConfigProperty;
import com.kevincylee.stock.entity.Stock;
import com.kevincylee.stock.entity.StockIndustryType;
import com.kevincylee.stock.entity.StockInfo;
import com.kevincylee.stock.entity.StockInfoPiece;
import com.kevincylee.stock.repository.ConfigPropertyRepository;
import com.kevincylee.stock.repository.IndustryTypeRepository;
import com.kevincylee.stock.repository.StockInfoPieceRepository;
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

	@Autowired
	StockInfoPieceRepository stockInfoPieceRepository;

	@Autowired
	private ConfigPropertyRepository configPropertyRepository;

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
			industryTypeRepository.save(new StockIndustryType(requestBody.getIndustryType()));
		}

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date onMarketDate = dateFormat.parse(requestBody.getOnMarketDate());
		stock = new Stock();
		stock.setMarketType(requestBody.getMarketType());
		stock.setStockNumber(stockNumber);
		stock.setStockName(requestBody.getStockName());
		stock.setIsinCode(requestBody.getIsinCode());
		stock.setOnMarketDate(onMarketDate);
		stock.setIndustryType(requestBody.getIndustryType());
		stockRepository.save(stock);

		return "SUCCESS";
	}

	public @ResponseBody String addStockInfo(StockInfoRequest requestBody) throws ParseException {

		List<StockInfoPiece> stockInfoPieces = new ArrayList<StockInfoPiece>();
		List<StockInfoPiece> stockInfoPiecesToDB = new ArrayList<StockInfoPiece>();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

		Date transactionDate = dateFormat.parse(requestBody.getTransactionDate());
		Date transactionDateTime = dateTimeFormat
				.parse(requestBody.getTransactionDate() + " " + requestBody.getTransactionTime());

		StockInfo stockInfo = stockInfoRepository.findByStockNumberAndTransactionDate(requestBody.getStockNumber(),
				transactionDate);
		if (stockInfo == null) {
			stockInfo = new StockInfo();
		}

		stockInfo.setStockNumber(requestBody.getStockNumber());
		stockInfo.setTransactionDate(transactionDate);
		stockInfo.setTransactionDateTime(transactionDateTime);
		stockInfo.setPriceOfOpen(requestBody.getPriceOfOpen());
		stockInfo.setPriceOfYesterday(requestBody.getPriceOfYesterday());
		stockInfo.setPriceOfLowest(requestBody.getPriceOfLowest());
		stockInfo.setPriceOfHighest(requestBody.getPriceOfHighest());
		stockInfo.setPriceOfLimitDown(requestBody.getPriceOfLimitDown());
		stockInfo.setPriceOfLimitUp(requestBody.getPriceOfLimitUp());
		stockInfo.setPrice(requestBody.getPrice());
		stockInfo.setTurnover(requestBody.getTurnover());
		stockInfo.setTotalTurnover(requestBody.getTotalTurnover());

		// 儲存五檔紀錄
		List<StockInfoPiece> inDBInfoPieces = stockInfoPieceRepository
				.findByStockNumberAndTransactionDateTime(requestBody.getStockNumber(), transactionDateTime);
		if (inDBInfoPieces.isEmpty()) {
			for (StockInfoPiece stockInfoPiece : requestBody.getStockInfoPieces()) {
				stockInfoPieces.add(new StockInfoPiece(requestBody.getStockNumber(), transactionDate,
						transactionDateTime, stockInfoPiece.getTransactionType(), stockInfoPiece.getPrice(),
						stockInfoPiece.getQuantity()));
			}
			stockInfoPiecesToDB = stockInfoPieceRepository.save(stockInfoPieces);
		}
		stockInfo.setStockInfoPieces(stockInfoPiecesToDB);

		stockInfoRepository.save(stockInfo);

		return "SUCCESS";
	}

	public String addConfingProperty(ConfingPropertyRequest requestBody) {
		ConfigProperty configProperty = configPropertyRepository.findByCode(requestBody.getCode());
		if (configProperty == null) {
			configProperty = new ConfigProperty(requestBody.getCode(), requestBody.getValue());
		} else {
			configProperty.setValue(requestBody.getValue());
		}
		configPropertyRepository.save(configProperty);
		return "SUCCESS";
	}

}
