package com.kevincylee.stock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kevincylee.stock.entity.StockInfo;

public interface StockInfoRepository extends MongoRepository<StockInfo, String> {

}
