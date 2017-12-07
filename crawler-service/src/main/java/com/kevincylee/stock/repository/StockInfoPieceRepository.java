package com.kevincylee.stock.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kevincylee.stock.entity.StockInfoPiece;

public interface StockInfoPieceRepository extends MongoRepository<StockInfoPiece, String> {

	List<StockInfoPiece> findByStockNumberAndTransactionDateTime(Integer stockNumber, Date transactionDateTime);
}
