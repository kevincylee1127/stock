package com.kevincylee.stock.entity;

import org.springframework.data.annotation.Id;

public class ConfigProperty {

	@Id
	public String id;
	public String code;
	public String value;
	public String description;

	public ConfigProperty() {
		super();
	}

	public ConfigProperty(String code, String value) {
		super();
		this.code = code;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
