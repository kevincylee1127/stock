package com.kevincylee.stock.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevincylee.stock.bean.StockRequest;

@Service
public class CrawlerService {

	@Autowired
	private StockService stockService;

	private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

	// 台灣證交所 上市證券國際證券辨識號碼
	String isinUrl = "http://isin.twse.com.tw/isin/C_public.jsp?strMode=2";
	// 台灣證交所 個股日成交資訊
	String stockDailyDataUrl = "http://app.twse.com.tw/ch/trading/exchange/STOCK_DAY/STOCK_DAYMAIN.php";

	public String getStock() throws IOException, ParseException {
		URL url = new URL(isinUrl);
		logger.info("====== Start getStock from " + url + " ======");
		Document htmlDoc = Jsoup.parse(url, 10000); // 使用Jsoup解析網頁
		Element htmlBody = htmlDoc.body();
		Elements stock = htmlBody.select(".h4 tr:nth-child(2) ~ tr");
		Integer totally = 0;
		for (Element info : stock) {
			String[] infoData = info.text().trim().replaceAll("\\s*　\\s*", " ").split("\\s");
			if (infoData.length < 6) {
				logger.info("====== End getStock from " + url + " ======");
				break;
			}
			logger.info(info.text());

			StockRequest infoReq = new StockRequest();
			infoReq.setStockNumber(infoData[0]);
			infoReq.setStockName(infoData[1]);
			infoReq.setIsinCode(infoData[2]);
			infoReq.setOnMarketDate(infoData[3]);
			infoReq.setMarketType(infoData[4]);
			infoReq.setIndustryType(infoData[5]);
			stockService.addStock(infoReq);
			totally++;
		}
		return "SUCCESS!! Totally " + totally + " datas.";
	}

	public String getStockInfo() throws MalformedURLException {
		URL url = new URL(stockDailyDataUrl);
		return "SUCCESS!! Totally ";
	}

}
