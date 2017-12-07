package com.kevincylee.stock.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kevincylee.stock.bean.StockRequest;
import com.kevincylee.stock.bean.TwseStockInfoRequest;
import com.kevincylee.stock.entity.Stock;
import com.kevincylee.stock.repository.StockRepository;

@Service
public class CrawlerService {

	@Autowired
	private StockService stockService;

	@Autowired
	private StockRepository stockRepository;

	private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

	private final String prefix = "tse_";
	private final String suffix = ".tw";
	private final Integer processRows = 1;

	// 台灣證交所 上市證券國際證券辨識號碼
	final String isinUrl = "http://isin.twse.com.tw/isin/C_public.jsp?strMode=2";
	// 台灣證交所 取得session
	final String twseUrl = "http://mis.twse.com.tw/stock/index.jsp";
	// 台灣證交所 個股成交資訊API
	final String stockInfoUrl = "http://mis.twse.com.tw/stock/api/getStockInfo.jsp";

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

			StockRequest req = new StockRequest();
			req.setStockNumber(infoData[0]);
			req.setStockName(infoData[1]);
			req.setIsinCode(infoData[2]);
			req.setOnMarketDate(infoData[3]);
			req.setMarketType(infoData[4]);
			req.setIndustryType(infoData[5]);
			stockService.addStock(req);
			totally++;
		}
		return "SUCCESS!! Totally " + totally + " datas.";
	}

	public String getStockInfo() throws MalformedURLException {
		TwseStockInfoRequest twseStockInfoRequest = new TwseStockInfoRequest();

		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.DAY_OF_WEEK, -1);
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		twseStockInfoRequest.setTransactionDate(df.format(rightNow.getTime()));

		List<Stock> stocks = stockRepository.findAll();
		Integer index = 0;
		String stockTargets = getStockTargets(stocks, index);
		twseStockInfoRequest.setStockCode(stockTargets);
		System.out.println("===>" + stockTargets + "===>");
		System.out.println("===>" + stockInfoUrl + twseStockInfoRequest.toParamString() + "===>");
		ResponseEntity<?> response = doGet(twseStockInfoRequest.toParamString(), String.class);
		// StockInfoRequest infoReq = new StockInfoRequest();
		// stockService.addStockInfo(infoReq);
		System.out.println(response.getBody().toString().trim());
		return response.getBody().toString();
	}

	private String getStockTargets(List<Stock> stocks, Integer index) {
		StringBuilder sb = new StringBuilder();
		for (int i = index; i < index + processRows; i++) {
			sb.append(prefix);
			sb.append(stocks.get(i).getStockNumber());
			sb.append(suffix);
			sb.append("|");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	private ResponseEntity<?> doGet(String paramString, Class<?> beanClass) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", getSessionId());
		headers.add("Accept-Language", "zh-TW");
		headers.add("User-Agent", "Mozilla/5.0");

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		ResponseEntity<?> twseStockInfoResponse = restTemplate.exchange(stockInfoUrl + paramString, HttpMethod.GET,
				httpEntity, beanClass);
		return twseStockInfoResponse;
	}

	private String getSessionId() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> twseEntity = restTemplate.getForEntity(twseUrl, String.class);
		List<String> session = twseEntity.getHeaders().get("Set-Cookie");
		return session.get(0).split(";")[0];
	}

}
