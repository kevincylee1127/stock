package com.kevincylee.crawler.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kevincylee.crawler.entity.Stock;

public interface StockRepository extends MongoRepository<Stock, String> {

	Stock findByStockNumber(Integer stockNumber);
}
