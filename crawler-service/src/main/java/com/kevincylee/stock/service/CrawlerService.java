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
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevincylee.stock.bean.StockInfoRequest;
import com.kevincylee.stock.bean.StockRequest;
import com.kevincylee.stock.bean.TwseStockInfoRequest;
import com.kevincylee.stock.bean.TwseStockInfoResponse;
import com.kevincylee.stock.bean.TwseStockInfoResponse.StockInfoArray;
import com.kevincylee.stock.entity.ConfigProperty;
import com.kevincylee.stock.entity.Stock;
import com.kevincylee.stock.entity.StockInfoPiece;
import com.kevincylee.stock.repository.ConfigPropertyRepository;
import com.kevincylee.stock.repository.StockRepository;

@Service
public class CrawlerService {

	@Autowired
	private StockService stockService;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ConfigPropertyRepository configPropertyRepository;

	private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

	private final String prefix = "tse_";
	private final String suffix = ".tw";
	private final String conjunction = "%7C";

	// 台灣證交所 上市證券國際證券辨識號碼 http://isin.twse.com.tw
	final String isinPage = "/isin/C_public.jsp?strMode=2";
	// 台灣證交所 取得session http://mis.twse.com.tw
	final String twseUrlPage = "/stock/index.jsp";
	// 台灣證交所 個股成交資訊API
	final String stockInfoPage = "/stock/api/getStockInfo.jsp";

	public String getStock() throws IOException, ParseException {
		URL url = new URL(configPropertyRepository.findByCode("isinUrl").getValue() + isinPage);
		logger.info("== Start getStock from " + url + " ==");
		Document htmlDoc = Jsoup.parse(url,
				Integer.parseInt(configPropertyRepository.findByCode("timeoutMillis").getValue())); // 使用Jsoup解析網頁
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
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar targetDateForHistory = Calendar.getInstance();
		if (targetDate == null) {
			targetDate = df.format(targetDateForHistory.getTime());
		}

		targetDateForHistory.setTime(df.parse(targetDate));
		if (targetDateForHistory.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| targetDateForHistory.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return targetDate + " is holiday";
		}

		Long stocksCount = stockRepository.count();
		// Long stocksCount = (long) 12;
		Integer pageStart = 0;
		Integer pageSize = Integer.parseInt(configPropertyRepository.findByCode("pageSize").getValue());

		while (pageStart * pageSize <= stocksCount.intValue()) {
			Pageable pageable = new PageRequest(pageStart, pageSize);
			Page<Stock> stocksForPage = stockRepository.findAll(pageable);
			List<Stock> stocks = stocksForPage.getContent();

			TwseStockInfoRequest twseStockInfoRequest = new TwseStockInfoRequest();
			twseStockInfoRequest.setTransactionDate(targetDate);
			String stockTargets = getStockTargets(stocks);
			twseStockInfoRequest.setStockCode(stockTargets);
			ResponseEntity<?> response = doGet(twseStockInfoRequest.toParamString(), String.class);

			logger.info("==> Date " + targetDate);
			logger.info("==> Start from " + pageStart * pageSize);
			logger.info("==>" + stockTargets);

			ObjectMapper mapper = new ObjectMapper();
			try {
				TwseStockInfoResponse stockInfos = mapper.readValue(response.getBody().toString().trim(),
						TwseStockInfoResponse.class);
				logger.info(mapper.writeValueAsString(stockInfos));

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

					String[] buyPrice = checkIsNull(stockInfo.getFivePiecesOfBuyPrice()).split("_");
					String[] buyQuantity = checkIsNull(stockInfo.getFivePiecesOfBuyQuantity()).split("_");
					String[] sellPrice = checkIsNull(stockInfo.getFivePiecesOfSellPrice()).split("_");
					String[] sellQuantity = checkIsNull(stockInfo.getFivePiecesOfSellQuantity()).split("_");

					DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
					DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
					for (int i = 0; i < buyPrice.length; i++) {
						stockInfoPieces.add(new StockInfoPiece(checkNullForInteger(stockInfo.getStockNumber()),
								dateFormat.parse(stockInfo.getTransactionDate()),
								dateTimeFormat
										.parse(stockInfo.getTransactionDate() + " " + stockInfo.getTransactionTime()),
								"BUY", checkNullForBigDecimal(buyPrice[i]), checkNullForInteger(buyQuantity[i])));

					}

					for (int i = 0; i < sellPrice.length; i++) {
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
			}

			pageStart++;
			try {
				Thread.sleep(Integer.parseInt(configPropertyRepository.findByCode("sleepMilliSeconds").getValue()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.info("Done...");

		return "SUCCESS!!";
	}

	public String getStockHistoryInfo(String startDate, String targetDate) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar startDateForHistory = Calendar.getInstance();
		Calendar targetDateForHistory = Calendar.getInstance();
		if (startDate == null) {
			try {
				startDate = configPropertyRepository.findByCode("startDateForHistory").getValue();
				startDateForHistory.setTime(df.parse(startDate));
			} catch (Exception e) {
				configPropertyRepository
						.save(new ConfigProperty("startDateForHistory", df.format(startDateForHistory.getTime())));
			}
		}

		if (targetDate == null) {
			try {
				targetDate = configPropertyRepository.findByCode("targetDateForHistory").getValue();
				targetDateForHistory.setTime(df.parse(targetDate));
			} catch (Exception e) {
				targetDateForHistory.add(Calendar.YEAR, -5);
				configPropertyRepository
						.save(new ConfigProperty("targetDateForHistory", df.format(targetDateForHistory.getTime())));
			}
		}

		while (startDateForHistory.after(targetDateForHistory)) {
			getStockInfo(df.format(startDateForHistory.getTime()));
			startDateForHistory.add(Calendar.DATE, -1);
			ConfigProperty startDateForHistoryConfig = configPropertyRepository.findByCode("startDateForHistory");
			startDateForHistoryConfig.setValue(df.format(startDateForHistory.getTime()));
			configPropertyRepository.save(startDateForHistoryConfig);
		}

		return "SUCCESS!!";
	}

	private static String checkIsNull(String x) {
		if (null != x) {
			return x;
		}
		return "";
	}

	private static BigDecimal checkNullForBigDecimal(String x) {
		if (null != x) {
			BigDecimal z = BigDecimal.ZERO;
			try {
				z = new BigDecimal(x);
			} catch (NumberFormatException e) {
				logger.info(x + " it's not a BigDecimal type");
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
				logger.info(x + " it's not a Integer type");
			}
			return z;
		}
		return null;
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
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", getSessionId());
		headers.add("Accept-Language", "zh-TW");
		headers.add("User-Agent", "Mozilla/5.0");
		headers.add("Content-Type", "application/json");

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		ResponseEntity<?> twseStockInfoResponse = restTemplate.exchange(
				configPropertyRepository.findByCode("twseUrl").getValue() + stockInfoPage + paramString, HttpMethod.GET,
				httpEntity, beanClass);
		return twseStockInfoResponse;
	}

	private String getSessionId() {
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
		ResponseEntity<String> twseEntity = restTemplate
				.getForEntity(configPropertyRepository.findByCode("twseUrl").getValue() + twseUrlPage, String.class);
		List<String> session = twseEntity.getHeaders().get("Set-Cookie");
		return session.get(0).split(";")[0];
	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(Integer.parseInt(configPropertyRepository.findByCode("readTimeout").getValue()));
		factory.setConnectTimeout(Integer.parseInt(configPropertyRepository.findByCode("connectTimeout").getValue()));
		return factory;
	}

}
