package com.vodafone.model;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@ToString(includeFieldNames=true)
public @Data class Address {
	private  @NotNull(message="Please Enter Full Address (Street, City, Country)") String street, city, country;
}