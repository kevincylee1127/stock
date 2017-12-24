package com.kevincylee.crawler.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kevincylee.crawler.entity.ConfigProperty;

public interface ConfigPropertyRepository extends MongoRepository<ConfigProperty, String> {
	ConfigProperty findByGroupAndCode(String group, String code);
}
