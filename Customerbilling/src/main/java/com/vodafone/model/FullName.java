package com.vodafone.model;

import lombok.Data;
import lombok.ToString;

@ToString(includeFieldNames=true)
@Data
public class FullName {
	private String firstName, middleName, lastName;
}