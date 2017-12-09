package com.kevincylee.stock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kevincylee.stock.entity.ConfigProperty;

public interface ConfigPropertyRepository extends MongoRepository<ConfigProperty, String> {
	ConfigProperty findByCode(String code);
}
