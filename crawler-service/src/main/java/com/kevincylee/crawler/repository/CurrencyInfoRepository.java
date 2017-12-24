package com.kevincylee.crawler.repository;

import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kevincylee.crawler.entity.CurrencyInfo;

public interface CurrencyInfoRepository extends MongoRepository<CurrencyInfo, String> {

	CurrencyInfo findByCurrencyTypeAndTransactionDate(String currencyType, Date transactionDate);
	
}
