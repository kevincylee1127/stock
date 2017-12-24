package com.kevincylee.crawler.service;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevincylee.crawler.bean.CurrencyInfoRequest;
import com.kevincylee.crawler.entity.ConfigProperty;
import com.kevincylee.crawler.repository.ConfigPropertyRepository;

@Service
public class CurrencyCrawlerService {

	@Autowired
	private CommonService commonService;

	@Autowired
	private ConfigPropertyRepository configPropertyRepository;

	private static final Logger logger = LoggerFactory.getLogger(CurrencyCrawlerService.class);

	public String getCurrencyInfo(String targetDate) throws Exception {

		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		DateFormat dfForGet = new SimpleDateFormat("yyyy-MM-dd");
		Calendar targetDateForHistory = Calendar.getInstance();
		if (targetDate == null) {
			targetDate = df.format(targetDateForHistory.getTime());
		}

		targetDateForHistory.setTime(df.parse(targetDate));
		if (targetDateForHistory.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| targetDateForHistory.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return targetDate + " is holiday";
		}

		logger.info("==> Date " + targetDate);

		URL url = new URL(configPropertyRepository.findByGroupAndCode("Currency", "twBankUrl").getValue() + "/"
				+ dfForGet.format(targetDateForHistory.getTime()));
		logger.info("== Start get getCurrencyInfo from " + url + " ==");
		Document htmlDoc = Jsoup.parse(url,
				Integer.parseInt(configPropertyRepository.findByGroupAndCode("Jsoup", "timeoutMillis").getValue())); // 使用Jsoup解析網頁
		Element htmlBody = htmlDoc.body();
		Elements currency = htmlBody.select("table tr");
		Integer totally = 0;

		if (currency.size() <= 3) {
			return targetDate + " is holiday";
		}

		for (int i = 2; i < currency.size(); i++) {
			logger.info(currency.get(i).text());
			String[] infoData = currency.get(i).text().trim().replaceAll("\\s*　\\s*", " ").split("\\s");
			CurrencyInfoRequest req = new CurrencyInfoRequest();
			req.setCurrencyName(infoData[0]);
			req.setCurrencyType(infoData[1]);
			req.setTransactionDate(targetDate);
			req.setPriceOfCashBuying(checkNullForBigDecimal(infoData[4]));
			req.setPriceOfCashSelling(checkNullForBigDecimal(infoData[5]));
			req.setPriceOfSpotBuying(checkNullForBigDecimal(infoData[6]));
			req.setPriceOfSpotSelling(checkNullForBigDecimal(infoData[7]));

			commonService.addCurrencyInfo(req);
			totally++;
		}

		return "SUCCESS!! Totally " + totally + " datas.";
	}

	public String getCurrencyHistoryInfo(String startDate, String targetDate) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar startDateForHistory = Calendar.getInstance();
		Calendar targetDateForHistory = Calendar.getInstance();
		if (startDate == null) {
			try {
				startDate = configPropertyRepository.findByGroupAndCode("Currency", "startDateForHistory").getValue();
				startDateForHistory.setTime(df.parse(startDate));
			} catch (Exception e) {
				configPropertyRepository.save(new ConfigProperty("Currency", "startDateForHistory",
						df.format(startDateForHistory.getTime())));
			}
		}

		if (targetDate == null) {
			try {
				targetDate = configPropertyRepository.findByGroupAndCode("Currency", "targetDateForHistory").getValue();
				targetDateForHistory.setTime(df.parse(targetDate));
			} catch (Exception e) {
				targetDateForHistory.add(Calendar.YEAR, -5);
				configPropertyRepository.save(new ConfigProperty("Currency", "targetDateForHistory",
						df.format(targetDateForHistory.getTime())));
			}
		}

		while (startDateForHistory.after(targetDateForHistory)) {
			getCurrencyInfo(df.format(startDateForHistory.getTime()));
			startDateForHistory.add(Calendar.DATE, -1);
			ConfigProperty startDateForHistoryConfig = configPropertyRepository.findByGroupAndCode("Currency",
					"startDateForHistory");
			startDateForHistoryConfig.setValue(df.format(startDateForHistory.getTime()));
			configPropertyRepository.save(startDateForHistoryConfig);
		}

		return "SUCCESS!!";
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

}
