package com.kevincylee.stock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kevincylee.stock.entity.Stock;

public interface StockRepository extends MongoRepository<Stock, String> {

	Stock findByStockNumber(Integer stockNumber);
}
