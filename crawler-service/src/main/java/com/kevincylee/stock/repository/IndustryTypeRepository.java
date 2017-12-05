package com.kevincylee.stock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kevincylee.stock.entity.StockIndustryType;

public interface IndustryTypeRepository extends MongoRepository<StockIndustryType, String> {
	
	StockIndustryType findByIndustryName(String industryName);
}
