package com.kevincylee.crawler.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kevincylee.crawler.entity.StockInfoPiece;

public interface StockInfoPieceRepository extends MongoRepository<StockInfoPiece, String> {

	List<StockInfoPiece> findByStockNumberAndTransactionDateTime(Integer stockNumber, Date transactionDateTime);
}
