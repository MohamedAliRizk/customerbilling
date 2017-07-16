package com.vodafone.model;

import lombok.Data;
import lombok.ToString;

@ToString(includeFieldNames=true)
public @Data class Address {
	private String street, city, country;
}