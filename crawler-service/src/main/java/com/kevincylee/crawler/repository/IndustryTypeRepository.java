package com.kevincylee.crawler.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kevincylee.crawler.entity.StockIndustryType;

public interface IndustryTypeRepository extends MongoRepository<StockIndustryType, String> {
	
	StockIndustryType findByIndustryName(String industryName);
}
