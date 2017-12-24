package com.kevincylee.crawler.entity;

import org.springframework.data.annotation.Id;

public class ConfigProperty {

	@Id
	public String id;
	public String group;
	public String code;
	public String value;
	public String description;

	public ConfigProperty() {
		super();
	}

	public ConfigProperty(String group, String code, String value) {
		super();
		this.group = group;
		this.code = code;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
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
