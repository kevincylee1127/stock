package com.kevincylee.stock.entity;

import org.springframework.data.annotation.Id;

public class StockIndustryType {

	@Id
	public String id;
	public String industryName; // 產業別名稱

	public StockIndustryType() {
		super();
	}

	public StockIndustryType(String industryName) {
		super();
		this.industryName = industryName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

}
