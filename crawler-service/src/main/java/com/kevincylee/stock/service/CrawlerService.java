package com.kevincylee.stock.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevincylee.stock.bean.StockInfoRequest;
import com.kevincylee.stock.bean.StockRequest;
import com.kevincylee.stock.bean.TwseStockInfoRequest;
import com.kevincylee.stock.bean.TwseStockInfoResponse;
import com.kevincylee.stock.bean.TwseStockInfoResponse.StockInfoArray;
import com.kevincylee.stock.entity.Stock;
import com.kevincylee.stock.entity.StockInfoPiece;
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
	private final String conjunction = "%7C";

	// 台灣證交所 上市證券國際證券辨識號碼
	final String isinUrl = "http://isin.twse.com.tw/isin/C_public.jsp?strMode=2";
	// 台灣證交所 取得session
	final String twseUrl = "http://mis.twse.com.tw/stock/index.jsp";
	// 台灣證交所 個股成交資訊API
	final String stockInfoUrl = "http://mis.twse.com.tw/stock/api/getStockInfo.jsp";

	public String getStock() throws IOException, ParseException {
		URL url = new URL(isinUrl);
		logger.info("== Start getStock from " + url + " ==");
		Document htmlDoc = Jsoup.parse(url, 10000); // 使用Jsoup解析網頁
		Element htmlBody = htmlDoc.body();
		Elements stock = htmlBody.select(".h4 tr:nth-child(2) ~ tr");
		Integer totally = 0;
		for (Element info : stock) {
			String[] infoData = info.text().trim().replaceAll("\\s*　\\s*", " ").split("\\s");
			if (infoData.length < 6) {
				logger.info("== End getStock from " + url + " ==");
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

	public String getStockInfo(String targetDate) throws Exception {

		// Long stocksCount = stockRepository.count();
		Long stocksCount = (long) 12;
		Integer indexStart = 0;
		Integer pageSize = 5;

		while (indexStart * pageSize <= stocksCount.intValue()
				|| ((indexStart - 1) * pageSize < stocksCount && stocksCount < (indexStart) * pageSize)) {
			Pageable pageable = new PageRequest(indexStart, pageSize);
			Page<Stock> stocksForPage = stockRepository.findAll(pageable);
			List<Stock> stocks = stocksForPage.getContent();

			TwseStockInfoRequest twseStockInfoRequest = new TwseStockInfoRequest();
			twseStockInfoRequest.setTransactionDate(targetDate);
			String stockTargets = getStockTargets(stocks);
			twseStockInfoRequest.setStockCode(stockTargets);
			ResponseEntity<?> response = doGet(twseStockInfoRequest.toParamString(), String.class);

			System.out.println("==> Date " + targetDate);
			System.out.println("==> Start from " + indexStart * pageSize);
			System.out.println("==>" + stockTargets);

			ObjectMapper mapper = new ObjectMapper();
			try {
				TwseStockInfoResponse stockInfos = mapper.readValue(response.getBody().toString().trim(),
						TwseStockInfoResponse.class);
				System.out.println(stockInfos.toString());

				for (StockInfoArray stockInfo : stockInfos.getStockInfoArray()) {
					StockInfoRequest infoReq = new StockInfoRequest();
					List<StockInfoPiece> stockInfoPieces = new ArrayList<StockInfoPiece>();
					infoReq.setStockNumber(checkNullForInteger(stockInfo.getStockNumber()));
					infoReq.setTransactionDate(stockInfo.getTransactionDate());
					infoReq.setTransactionTime(stockInfo.getTransactionTime());
					infoReq.setPriceOfOpen(checkNullForBigDecimal(stockInfo.getPriceOfOpen()));
					infoReq.setPriceOfYesterday(checkNullForBigDecimal(stockInfo.getPriceOfYesterday()));
					infoReq.setPriceOfLowest(checkNullForBigDecimal(stockInfo.getPriceOfLowest()));
					infoReq.setPriceOfHighest(checkNullForBigDecimal(stockInfo.getPriceOfHighest()));
					infoReq.setPriceOfLimitDown(checkNullForBigDecimal(stockInfo.getPriceOfLimitDown()));
					infoReq.setPriceOfLimitUp(checkNullForBigDecimal(stockInfo.getPriceOfLimitUp()));
					infoReq.setPrice(checkNullForBigDecimal(stockInfo.getPrice()));
					infoReq.setTurnover(checkNullForInteger(stockInfo.getTurnover()));
					infoReq.setTotalTurnover(checkNullForInteger(stockInfo.getTotalTurnover()));

					String[] buyPrice = stockInfo.getFivePiecesOfBuyPrice().split("_");
					String[] buyQuantity = stockInfo.getFivePiecesOfBuyQuantity().split("_");
					String[] sellPrice = stockInfo.getFivePiecesOfSellPrice().split("_");
					String[] sellQuantity = stockInfo.getFivePiecesOfSellQuantity().split("_");

					DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
					DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
					for (int i = 0; i < buyPrice.length; i++) {
						stockInfoPieces.add(new StockInfoPiece(checkNullForInteger(stockInfo.getStockNumber()),
								dateFormat.parse(stockInfo.getTransactionDate()),
								dateTimeFormat
										.parse(stockInfo.getTransactionDate() + " " + stockInfo.getTransactionTime()),
								"BUY", checkNullForBigDecimal(buyPrice[i]), checkNullForInteger(buyQuantity[i])));

						stockInfoPieces.add(new StockInfoPiece(checkNullForInteger(stockInfo.getStockNumber()),
								dateFormat.parse(stockInfo.getTransactionDate()),
								dateTimeFormat
										.parse(stockInfo.getTransactionDate() + " " + stockInfo.getTransactionTime()),
								"SELL", checkNullForBigDecimal(sellPrice[i]), checkNullForInteger(sellQuantity[i])));
					}
					infoReq.setStockInfoPieces(stockInfoPieces);

					stockService.addStockInfo(infoReq);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

			indexStart++;
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Done...");

		return "SUCCESS!!";
	}

	private static BigDecimal checkNullForBigDecimal(String x) {
		if (null != x) {
			BigDecimal z = BigDecimal.ZERO;
			try {
				z = new BigDecimal(x);
			} catch (NumberFormatException e) {
				System.out.println("Wrong number");
			}
			return z;
		}
		return null;
	}

	private Integer checkNullForInteger(String x) {
		if (null != x) {
			Integer z = 0;
			try {
				z = Integer.parseInt(x);
			} catch (NumberFormatException e) {
				System.out.println("Wrong number");
			}
			return z;
		}
		return null;
	}

	public String getStockHistoryInfo(String targetDate) throws Exception {

		Calendar nowCal = Calendar.getInstance();
		nowCal.add(Calendar.DAY_OF_WEEK, -1);

		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar targetCal = Calendar.getInstance();
		targetCal.setTime(df.parse(targetDate));

		while (nowCal.before(targetCal)) {
			getStockInfo(df.format(nowCal));
		}

		return "SUCCESS!!";
	}

	private String getStockTargets(List<Stock> stocks) {
		StringBuilder sb = new StringBuilder();
		for (Stock stock : stocks) {
			sb.append(prefix);
			sb.append(stock.getStockNumber());
			sb.append(suffix);
			sb.append(conjunction);
		}
		return sb.toString();
	}

	private ResponseEntity<?> doGet(String paramString, Class<?> beanClass) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", getSessionId());
		headers.add("Accept-Language", "zh-TW");
		headers.add("User-Agent", "Mozilla/5.0");
		headers.add("Content-Type", "application/json");

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
