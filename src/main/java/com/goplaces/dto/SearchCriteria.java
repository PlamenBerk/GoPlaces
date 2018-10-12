package com.goplaces.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class SearchCriteria {
	private String key;
	private String operation;
	private Object value;

	public SearchCriteria(String key, String operation, Object value) {
		super();
		this.key = key;
		this.operation = operation;
		this.value = value;
	}

}
